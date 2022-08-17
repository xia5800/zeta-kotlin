package org.zetaframework.core.redis.model

import org.springframework.data.redis.core.ValueOperations
import java.time.Duration

/**
 * String类型的缓存key
 *
 * 说明：
 * 只封装了几个特别常用的方法
 *
 * 使用前先搞懂几个概念：
 * prefix:
 *      顾名思义，缓存key的前缀。 例如：system:captcha、user、msg:push:send  注意：前缀不要有结尾的冒号
 * suffix:
 *      顾名思义，缓存key的后缀。 是一些动态的值(也可以是固定的值)。 例如：用户的id、当前的时间戳、abc:def、md5值等
 * key:
 *      缓存key，即redis的key。 key = prefix + ":" + suffix。 例如： user:9527、system:captcha:1348646464等
 * @author gcc
 */
interface StringCacheKey: CacheKey {

    fun getValueOps(): ValueOperations<String, Any> {
        return getRedisTemplate().opsForValue()
    }

    /**
     * 设置指定key的值
     *
     * @param suffix key后缀 可空
     *        示例："", "123", "abc:123"
     *        得到的完整key："前缀", "前缀:123", "前缀:abc:123"
     * @param value 缓存值  可空  为空缓存NullVal对象
     */
    fun set(suffix: Any? = "", value: Any?) {
        set(suffix, value, getExpire())
    }

    /**
     * 设置指定key的值, 并手动设置超时时间
     *
     * @param suffix  key后缀 可空
     *        示例："", "123", "abc:123"
     *        得到的完整key："前缀", "前缀:123", "前缀:abc:123"
     * @param value  缓存值  可空  为空缓存NullVal对象
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun set(suffix: Any? = "", value: Any? = null, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key。 如果value值为null,则缓存NullVal对象
        getValueOps()[key] = value ?: createNullVal()

        // 设置key过期
        expire(key, expire)
    }

    /**
     * 获取指定key的值
     *
     * @param suffix   key后缀 可空
     * @return T?      缓存值 查不到则返回null，查得到则返回对应的对象
     */
    fun <T> get(suffix: Any? = ""): T? {
        val key = buildKey(getPrefix(), suffix)
        return returnVal(getValueOps()[key] as T?)
    }

    /**
     * 删除key
     *
     * @param suffix   key后缀 可空
     */
    fun delete(suffix: Any? = null): Boolean {
        return getRedisTemplate().delete(buildKey(getPrefix(), suffix))
    }

    // 如果需要用到这里没有的方法，可以在这里加，或者使用getValueOps()自己实现

}
