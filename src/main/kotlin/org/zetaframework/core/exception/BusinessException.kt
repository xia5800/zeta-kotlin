package org.zetaframework.core.exception

import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 自定义异常 - 业务异常
 *
 * @author gcc
 */
class BusinessException: RuntimeException {
    /** 错误码 */
    var code: Int? = null

    /** 无参构造方法 */
    constructor(): this(ErrorCodeEnum.ERR_BUSINESS.msg)

    /**
     * 构造方法
     *
     * @param message 异常信息
     */
    constructor(message: String): this(ErrorCodeEnum.ERR_BUSINESS.code, message)

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
    constructor(message: String, cause: Throwable) : this(ErrorCodeEnum.ERR_BUSINESS.code, message, cause)

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
