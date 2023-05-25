package org.zetaframework.base.result

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 返回结果
 *
 * @author gcc
 */
@Schema(description = "返回结果")
class ApiResult<T> {

    /** 状态码 */
    @Schema(description = "状态码")
    var code: Int? = null

    /** 是否成功 */
    val success: Boolean
        @Schema(description = "是否成功")
        get() = code == ErrorCodeEnum.SUCCESS.code

    /** 状态信息 */
    @Schema(description = "状态信息")
    var message: String? = null

    /** 返回数据 */
    @Schema(description = "返回数据")
    var data: T? = null

    /** 错误信息 */
    @Schema(description = "错误信息")
    var error: String? = null

    /** 是否执行默认操作 */
    @JsonIgnore
    @Schema(description = "是否执行默认操作", hidden = true)
    var defExec = true

    constructor() {
        this.defExec = false
    }

    constructor(code: Int?) : this(code, null)

    constructor(code: Int, msg: String?, data: T?, defExec: Boolean): this(code, msg, data) {
        this.defExec = defExec
    }

    constructor(code: Int? = ErrorCodeEnum.SUCCESS.code, message: String? = null, data: T? = null, error: String? = null) {
        this.code = code
        this.message = message
        this.data = data
        this.error = error
        this.defExec = false
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
            return ApiResult(code, message, data)
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
