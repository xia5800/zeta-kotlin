package org.zetaframework.core.exception

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.exception.SaTokenException
import cn.hutool.core.util.StrUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.enums.ErrorCodeEnum
import org.zetaframework.core.redis.annotation.Limit
import org.zetaframework.core.redis.exception.LimitException
import java.util.*

/**
 * 全局异常处理器
 *
 * @author gcc
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 业务异常处理
     *
     * @param ex BusinessException
     * @return ApiResult<*>
     */
    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun businessExceptionHandler(ex: BusinessException): ApiResult<*> {
        logger.warn("抛出业务异常：${ex}")
        return ApiResult.result(ex.code, ex.message, null)
    }

    /**
     * 参数异常处理
     *
     * @param ex ArgumentException
     * @return ApiResult<*>
     */
    @ExceptionHandler(ArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun argumentExceptionHandler(ex: ArgumentException): ApiResult<*> {
        logger.warn("抛出参数异常：", ex)
        return ApiResult.result(ex.code, ex.message, null)
    }

    /**
     * 非法参数异常处理
     *
     * 说明：
     * 主要用于Hutool的Assert断言异常处理
     * @param ex IllegalArgumentException
     * @return ApiResult<*>
     */
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException): ApiResult<*> {
        logger.warn("抛出非法参数异常：", ex)
        return ApiResult.result(ErrorCodeEnum.ERR_ARGUMENT.code, ex.message, null)
    }

    /**
     * 限流异常处理
     *
     * 说明：
     * 主要用于接口限流注解[Limit]的异常处理
     * @param ex LimitException
     * @return ApiResult<*>
     */
    @ExceptionHandler(LimitException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun limitExceptionHandler(ex: LimitException): ApiResult<*> {
        logger.warn("抛出接口限流异常：", ex)
        return ApiResult.result(ex.code, ex.message, null)
    }

    /**
     * 绑定异常
     *
     * @param ex BindException
     * @return ApiResult<*>
     */
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun bindExceptionHandler(ex: BindException): ApiResult<*> {
        logger.warn("抛出绑定异常：", ex)
        try {
            val msg = Objects.requireNonNull(ex.bindingResult.fieldError)!!.defaultMessage
            if (StrUtil.isNotEmpty(msg)) {
                return ApiResult.result(ErrorCodeEnum.ERR_BIND_EXCEPTION.code, msg, ex.message)
            }
        } catch (e: Exception) {
            logger.warn("获取异常描述失败", e)
        }

        val msg = StringBuilder()
        ex.fieldErrors.forEach {
            msg.append("参数:[${it.objectName}.${it.field}]的传入值:[${it.rejectedValue}]与预期的字段类型不匹配.")
        }
        return ApiResult.result(ErrorCodeEnum.ERR_BIND_EXCEPTION.code, msg.toString(), ex.message)
    }

    /**
     * 方法参数类型不匹配异常
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return R<*>?
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ApiResult<*>? {
        logger.warn("抛出方法参数类型不匹配异常：", ex)
        val msg = "参数：[${ex.name}]的传入值：[${ex.value}]与预期的字段类型：[${Objects.requireNonNull(ex.requiredType)!!.name}]不匹配"
        return ApiResult.result(ErrorCodeEnum.ERR_ARGUMENT_TYPE_MISMATCH_EXCEPTION.code, msg, ex.message)
    }

    /**
     * 从请求中读取数据失败异常
     *
     * @param ex HttpMessageNotReadableException
     * @return R<*>?
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableException(ex: HttpMessageNotReadableException): ApiResult<*>? {
        logger.warn("抛出从请求中读取数据失败异常：", ex)
        var msg = ex.message
        if (StrUtil.containsAny(msg, "Could not read document:")) {
            msg = "无法正确的解析json类型的参数：${StrUtil.subBetween(msg, "Could not read document:", " at ")}"
        }

        // Controller接口参数为空
        if (StrUtil.containsAny(msg, "Required request body is missing")) {
            msg = "请求参数为空"
        }

        // 接口参数枚举值不正确
        if (StrUtil.containsAny(msg, "not one of the values accepted for Enum class")) {
            msg = "枚举参数值不正确"
        }
        return ApiResult.result(ErrorCodeEnum.ERR_REQUEST_PARAM_EXCEPTION.code, msg, ex.message)
    }

    /**
     * 登录认证异常
     *
     * @param ex NotLoginException
     * @return ApiResult<*>
     */
    @ExceptionHandler(NotLoginException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun notLoginExceptionHandler(ex: NotLoginException): ApiResult<*> {
        logger.warn("抛出登录认证异常：", ex)
        val message = when(ex.type) {
            NotLoginException.NOT_TOKEN -> NotLoginException.NOT_TOKEN_MESSAGE
            NotLoginException.INVALID_TOKEN -> NotLoginException.INVALID_TOKEN_MESSAGE
            NotLoginException.TOKEN_TIMEOUT -> NotLoginException.TOKEN_TIMEOUT_MESSAGE
            NotLoginException.BE_REPLACED -> NotLoginException.BE_REPLACED_MESSAGE
            NotLoginException.KICK_OUT -> NotLoginException.KICK_OUT_MESSAGE
            else -> NotLoginException.DEFAULT_MESSAGE
        }
        return ApiResult.result(ErrorCodeEnum.UNAUTHORIZED.code, message, null).setError(ex.message)
    }


    /**
     * sa-token相关异常处理
     *
     * @param ex SaTokenException
     * @return ApiResult<*>
     */
    @ExceptionHandler(SaTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun saTokenExceptionHandler(ex: SaTokenException): ApiResult<*> {
        logger.warn("抛出sa-token相关异常：", ex)
        var code: Int = ErrorCodeEnum.FAIL.code
        var message: String? = ""
        if(ex is NotLoginException) {
            return notLoginExceptionHandler(ex)
        } else if(ex is NotRoleException || ex is NotPermissionException) {
            message = ErrorCodeEnum.FORBIDDEN.msg
            code = ErrorCodeEnum.FORBIDDEN.code
        } else {
            message = ex.message
        }
        return ApiResult.result(code, message, null)
    }


    /**
     * 其它异常
     * @param ex Exception
     * @return ApiResult<*>
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun otherExceptionHandler(ex: Exception): ApiResult<*> {
        logger.warn("抛出其它异常：", ex)
        return ApiResult.result(ErrorCodeEnum.SYSTEM_BUSY.code, ex.message, null)
    }

}
