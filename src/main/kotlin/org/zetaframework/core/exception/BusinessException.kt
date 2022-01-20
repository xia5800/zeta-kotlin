package org.zetaframework.core.exception

import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 自定义异常 - 业务异常
 *
 * @author gcc
 */
class BusinessException: RuntimeException {

    var code: Int? = null

    constructor(): this(ErrorCodeEnum.ERR_BUSINESS.msg)

    constructor(message: String): this(ErrorCodeEnum.ERR_BUSINESS.code, message)

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(message: String, cause: Throwable) : this(ErrorCodeEnum.ERROR.code, message, cause)

    constructor(code: Int, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }
}
