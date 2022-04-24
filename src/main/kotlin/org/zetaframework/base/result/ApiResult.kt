package org.zetaframework.base.result

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 返回结果
 *
 * @author gcc
 */
@ApiModel(description = "返回结果")
class ApiResult<T> {

    /** 状态码 */
    @ApiModelProperty(value = "状态码")
    var code: Int? = null

    /** 状态信息 */
    @ApiModelProperty(value = "状态信息")
    var message: String? = null

    /** 返回数据 */
    @ApiModelProperty(value = "返回数据")
    var data: T? = null

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息")
    var error: String? = null

    /** 是否执行默认操作 */
    @JsonIgnore
    @ApiModelProperty(value = "是否执行默认操作", hidden = true)
    var defExec = true

    constructor() {
        this.defExec = false
    }

    constructor(code: Int?) : this(code, null)

    constructor(code: Int, msg: String?, data: T?, defExec: Boolean): this(code, msg, data) {
        this.defExec = defExec
    }

    constructor(code: Int? = ErrorCodeEnum.SUCCESS.code, message: String? = null, data: T? = null, error: String? = null) {
        this.code = code;
        this.message = message;
        this.data = data
        this.error = error;
        this.defExec = false;
    }

    fun setMessage(message: String?): ApiResult<T> {
        this.message = message
        return this
    }

    fun setError(error: String?): ApiResult<T> {
        this.error = error
        return this
    }

    fun setData(data: T?): ApiResult<T> {
        this.data = data
        return this
    }

    companion object {
        /**
         * 返回结果
         *
         * @param code Int
         * @param message String
         * @return ApiResult<E>
         */
        fun <E> result(code: Int?, message: String?, data: E? = null): ApiResult<E> {
            return ApiResult(code, message)
        }

        /**
         * 请求成功
         * @param code Int?
         * @param message String?
         * @param data E?
         * @return ApiResult<E>
         */
        fun <E> success(code: Int? = ErrorCodeEnum.SUCCESS.code, message: String? = ErrorCodeEnum.SUCCESS.msg, data: E? = null): ApiResult<E> {
            return ApiResult(code, message, data)
        }

        /**
         * 请求成功，需要执行默认操作
         * @return ApiResult<Entity>
         */
        fun <E> successDef(): ApiResult<E> {
            return ApiResult(ErrorCodeEnum.SUCCESS.code, ErrorCodeEnum.SUCCESS.msg, null, true)
        }

        /**
         * 请求失败
         * @param code Int?
         * @param message String?
         * @param data E?
         * @return ApiResult<E>
         */
        fun <E> fail(code: Int? = ErrorCodeEnum.FAIL.code, message: String? = ErrorCodeEnum.FAIL.msg, data: E? = null): ApiResult<E> {
            return ApiResult(code, message, data)
        }
    }
}
