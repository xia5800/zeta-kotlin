package org.zetaframework.core.redis.exception

import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 自定义限流异常 - 接口请求频繁
 *
 * @author gcc
 */
class LimitException: RuntimeException {
    /** 错误码 */
    var code: Int? = null

    /** 无参构造方法 */
    constructor(): this(ErrorCodeEnum.TOO_MANY_REQUESTS.msg)

    /**
     * 构造方法
     *
     * @param message 异常信息
     */
    constructor(message: String): this(ErrorCodeEnum.TOO_MANY_REQUESTS.code, message)

    /**
     * 构造方法
     *
     * @param code 错误码
     * @param message 异常信息
     */
    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    /**
     * 构造方法
     *
     * @param message 异常信息
     * @param cause 异常
     */
    constructor(message: String, cause: Throwable) : this(ErrorCodeEnum.TOO_MANY_REQUESTS.code, message, cause)

    /**
     * 构造方法
     *
     * @param code 错误码
     * @param message 异常信息
     * @param cause 异常
     */
    constructor(code: Int, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }

}
