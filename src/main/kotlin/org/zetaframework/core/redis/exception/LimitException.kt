package org.zetaframework.core.redis.exception

import org.zetaframework.core.enums.ErrorCodeEnum

/**
 * 自定义限流异常 - 接口请求频繁
 *
 * @author gcc
 */
class LimitException: RuntimeException {

    var code: Int? = null

    constructor(): this(ErrorCodeEnum.TOO_MANY_REQUESTS.msg)

    constructor(message: String): this(ErrorCodeEnum.TOO_MANY_REQUESTS.code, message)

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(message: String, cause: Throwable) : this(ErrorCodeEnum.TOO_MANY_REQUESTS.code, message, cause)

    constructor(code: Int, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }

}
