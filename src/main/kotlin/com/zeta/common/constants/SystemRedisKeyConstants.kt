package com.zeta.common.constants

/**
 * System模块redis缓存key常量
 *
 * @author gcc
 */
object SystemRedisKeyConstants {

    /**
     * 验证码 前缀
     *
     * 完整key: system:captcha:{key} -> str
     */
    const val CAPTCHA_KEY = "system:captcha"

}
