package com.zeta.common.cacheKey

import com.zeta.common.constants.SystemRedisKeyConstants
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.zetaframework.core.redis.model.StringCacheKey
import java.time.Duration

/**
 * 验证码 String类型的缓存key
 *
 * 使用方法：
 * ```
 *  // 添加缓存（使用默认的过期时间）
 *  captchaStringCacheKey.set("时间戳", "验证码的值")
 *  // 添加缓存（使用指定的过期时间）
 *  captchaStringCacheKey.set("时间戳", "验证码的值", Duration.ofMinutes(10))
 *
 *  // 获取缓存
 *  val captcha: String? = captchaStringCacheKey.get<String>("时间戳")
 *
 *  // 删除缓存
 *  captchaStringCacheKey.delete("时间戳")
 * ```
 *
 * 说明：
 * 更多的方法请查看[StringCacheKey]
 *
 * @author gcc
 */
@Component
class CaptchaStringCacheKey(private val redisTemplate: RedisTemplate<String, Any>): StringCacheKey {

    /**
     * key 前缀
     */
    override fun getPrefix(): String {
        return SystemRedisKeyConstants.CAPTCHA_KEY
    }

    /**
     * key 过期时间
     */
    override fun getExpire(): Duration? {
        return Duration.ofMinutes(5)
    }

    /**
     * 获取redisTemplate
     */
    override fun getRedisTemplate(): RedisTemplate<String, Any> {
        return redisTemplate
    }

}
