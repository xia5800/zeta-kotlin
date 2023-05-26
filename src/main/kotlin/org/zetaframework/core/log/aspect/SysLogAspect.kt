package org.zetaframework.core.log.aspect

import cn.hutool.core.exceptions.ExceptionUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.http.useragent.UserAgentUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.log.enums.LogTypeEnum
import org.zetaframework.core.log.event.SysLogEvent
import org.zetaframework.core.log.model.SysLogDTO
import org.zetaframework.core.utils.IpAddressUtil
import org.zetaframework.core.utils.JSONUtil
import org.zetaframework.core.utils.ServletUtil

/**
 * 系统日志 切面
 *
 * @author gcc
 */
// @Component  // 为了可以控制开启、关闭全局日志记录。改为Bean配置的方式
@Aspect
class SysLogAspect(private val context: ApplicationContext) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
        private val START_TIME: ThreadLocal<Long> = ThreadLocal()
        private const val MAX_LENGTH = 65535
    }

    @Pointcut("@annotation(org.zetaframework.core.log.annotation.SysLog)")
    fun SysLogAspect() {}


    /**
     * 执行方法之前
     *
     * @param joinPoint JoinPoint
     */
    @Before(value = "SysLogAspect()")
    fun doBefore(joinPoint: JoinPoint) {
        // 记录操作开始时间
        START_TIME.set(System.currentTimeMillis())
    }

    /**
     * 执行方法之后
     *
     * @param joinPoint JoinPoint
     * @param result Any
     */
    @AfterReturning(pointcut = "SysLogAspect()", returning = "result")
    fun doAfterReturning(joinPoint: JoinPoint, result: Any?) {
        publishEvent(joinPoint, result)
    }

    /**
     * 发生异常之后
     *
     * @param joinPoint JoinPoint
     * @param e Throwable
     */
    @AfterThrowing(pointcut = "SysLogAspect()", throwing = "e")
    fun doAfterThrowing(joinPoint: JoinPoint, e: Throwable?) {
        publishEvent(joinPoint, exception = e)
    }

    /**
     * 发布日志存储事件
     *
     * @param joinPoint JoinPoint
     * @param result Any?
     * @param exception Throwable?
     */
    fun publishEvent(joinPoint: JoinPoint, result: Any? = null, exception: Throwable? = null) {
        // 方法耗时
        val spendTime = getSpendTime()
        // 获取注解
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        var sysLog: SysLog? = null
        // 方法上的@SysLog注解
        if (method.isAnnotationPresent(SysLog::class.java)) {
            sysLog = method.getAnnotation(SysLog::class.java)
        }
        if (sysLog == null || !sysLog.enabled) {
            return
        }

        // 构造系统日志
        val sysLogDTO = buildSysLogDTO(joinPoint, sysLog)
        sysLogDTO.type = LogTypeEnum.OPERATION.name
        sysLogDTO.spendTime = spendTime
        sysLogDTO.result = getResponse(result, sysLog)
        sysLogDTO.exception = getException(exception) {
            sysLogDTO.type = LogTypeEnum.EXCEPTION.name
        }
        // 发布保存系统日志事件
        context.publishEvent(SysLogEvent(sysLogDTO))
    }

    /**
     * 获取方法耗时
     */
    private fun getSpendTime(): Long {
        // 记录结束时间
        var spendTime = 0L
        if (START_TIME.get() != null) {
            spendTime = System.currentTimeMillis() - START_TIME.get()
        }
        START_TIME.remove()
        logger.debug("操作耗时：${spendTime}")
        return spendTime
    }

    /**
     * 构造系统日志
     *
     * @param joinPoint JoinPoint
     * @param sysLog SysLog
     * @return SysLogDTO
     */
    private fun buildSysLogDTO(joinPoint: JoinPoint, sysLog: SysLog): SysLogDTO {
        val sysLogDTO = SysLogDTO()

        // 类路径
        sysLogDTO.classPath = "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"

        // 操作描述
        sysLogDTO.description = getDescription(joinPoint, sysLog)

        // 记录请求地址、请求方式、ip
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = attributes?.request
        if (request != null) {
            val ua = UserAgentUtil.parse(ServletUtil.getHeaderIgnoreCase(request, "User-Agent"))
            sysLogDTO.url = request.requestURI
            sysLogDTO.httpMethod = request.method
            sysLogDTO.os = ua.platform.name
            sysLogDTO.device = ua.os.name
            sysLogDTO.browser = ua.browser.name
            sysLogDTO.ip = ServletUtil.getClientIP(request)
            sysLogDTO.ip?.let { ip ->
                sysLogDTO.ipRegion = IpAddressUtil.search(ip)
            }
            // 获取请求参数
            if(sysLog.request) {
                sysLogDTO.params = getRequestParam(joinPoint, request)
            }
        }

        return sysLogDTO
    }


    /**
     * 获取请求参数
     *
     * @param joinPoint JoinPoint
     * @param request HttpServletRequest
     * @return String?
     */
    private fun getRequestParam(joinPoint: JoinPoint, request: HttpServletRequest): String? {
        var params: String? = null

        val paramMap = ServletUtil.getParamMap(request)
        if (paramMap.isNotEmpty()) {
            params = JSONUtil.toJsonStr(paramMap)
        } else {
            if (joinPoint.args.isNotEmpty()) {
                val paramList = mutableListOf<Any>()
                joinPoint.args.forEach {
                    if (it !is HttpServletRequest
                        && it !is HttpServletResponse
                        && it !is MultipartFile
                    ) {
                        paramList.add(it)
                    }
                }
                if (paramList.isNotEmpty()) {
                    params = JSONUtil.toJsonStr(paramList)
                }
            }
        }
        return params
    }

    /**
     * 获取操作描述
     *
     * 格式：xxx-xxxx
     * "@Tag注解的name值"-"SysLog注解的value值 或 @Operation注解的summary值"
     * @return String?
     */
    private fun getDescription(joinPoint: JoinPoint, sysLog: SysLog): String {
        val sb = StringBuilder()

        // 获取@Tag的value值
        val api = joinPoint.target.javaClass.getAnnotation(Tag::class.java)
        if (api != null) {
            if(api.name.isNotBlank()) {
                sb.append(api.name).append("-")
            }
        }

        // 获取@SysLog的value值
        if (StrUtil.isNotBlank(sysLog.value)) {
            sb.append(sysLog.value)
        } else {
            // 获取@Operation的summary值
            val signature = joinPoint.signature as MethodSignature
            val method = signature.method
            if (method.isAnnotationPresent(Operation::class.java)) {
                val apiOperation = method.getAnnotation(Operation::class.java)
                if (StrUtil.isNotBlank(apiOperation.summary)) {
                    sb.append(apiOperation.summary)
                } else {
                    // @SysLog没有value值、@Operation没有summary值的情况下。显示方法名
                    sb.append(method.name)
                }
            } else {
                // @SysLog没有value值、没有@Operation注解的情况下。显示方法名
                sb.append(method.name)
            }
        }
        return sb.toString()
    }

    /**
     * 获取返回值
     *
     * @param result Any?
     * @param sysLog OperationLog
     * @return String
     */
    private fun getResponse(result: Any?, sysLog: SysLog): String? = if(sysLog.response) {
        JSONUtil.toJsonStr(result)
    } else {
        ""
    }

    /**
     * 获取异常
     *
     * @param exception Throwable?
     * @param block Function0<Unit>
     * @return String?
     */
    private fun getException(exception: Throwable?, block: () -> Unit): String? = if(exception != null) {
        block.invoke()
        ExceptionUtil.stacktraceToString(exception, MAX_LENGTH)
    } else {
        ""
    }
}
