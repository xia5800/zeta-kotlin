package org.zetaframework.core.redis.model

import org.springframework.data.redis.core.HashOperations
import java.time.Duration

/**
 * Hash(Map)类型的缓存key
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
 * hashKey:
 *      你可以理解为HashMap的key。不能为空
 * value:
 *      你可以理解为HashMap的value。可以为空
 *
 * @author gcc
 */
interface HashCacheKey: CacheKey {

    fun getHashOps(): HashOperations<String, Any, Any?> {
        return getRedisTemplate().opsForHash<Any, Any?>()
    }

    /**
     * 将哈希表key中的字段hashKey的值设为value
     *
     * @param suffix  key后缀 可空
     *        示例："", "123", "abc:123"
     *        得到的完整key："前缀", "前缀:123", "前缀:abc:123"
     * @param hashKey hash表中的key 不可空
     * @param value  缓存值  可空  为空缓存NullVal对象
     */
    fun set(suffix: Any? = "", hashKey: Any, value: Any?) {
        set(suffix, hashKey, value, getExpire())
    }

    /**
     * 将哈希表key中的字段hashKey的值设为value
     *
     * @param suffix  key后缀 可空
     *        示例："", "123", "abc:123"
     *        得到的完整key："前缀", "前缀:123", "前缀:abc:123"
     * @param hashKey hash表中的key 不可空
     * @param value  缓存值  可空  为空缓存NullVal对象
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun set(suffix: Any? = "", hashKey: Any, value: Any?, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key
        getHashOps().put(key, hashKey, value ?: createNullVal())

        // 设置key过期
        expire(key, expire)
    }

    /**
     * 获取指定key对应的hash表
     *
     * @param suffix   key后缀 可空
     */
    fun get(suffix: Any? = ""): MutableMap<Any, Any?> {
        return getHashOps().entries(buildKey(getPrefix(), suffix))
    }

    /**
     * 获取存储在哈希表中指定hashKey对应的值
     *
     * @param suffix   key后缀 可空
     * @param hashKey  hash表中的key 不可空
     */
    fun <T> getValue(suffix: Any? = "", hashKey: Any): T? {
        val key = buildKey(getPrefix(), suffix)
        // opsForHash方法两个泛型分别代表: hashKey类型、hashKey对应value的类型
        return returnVal(getHashOps()[key, hashKey] as T?)
    }

    /**
     * 删除指定hashKey
     *
     * @param suffix   key后缀 可空
     * @param hashKey  hash表中的key 不可空
     */
    fun delete(suffix: Any? = "", vararg hashKey: Any): Long {
        val key = buildKey(getPrefix(), suffix)
        return getHashOps().delete(key, *hashKey)
    }

    /**
     * 获取哈希表中的所有hashkey
     *
     * @param suffix   key后缀 可空
     * @return hashKeySet
     */
    fun getKeys(suffix: Any? = ""): MutableSet<Any> {
        return getHashOps().keys(buildKey(getPrefix(), suffix))
    }

    /**
     * 获取哈希表中的所有value
     *
     * @param suffix   key后缀 可空
     * @return valueList
     */
    fun getValues(suffix: Any? = ""): MutableList<Any?> {
        return getHashOps().values(buildKey(getPrefix(), suffix))
    }

    /**
     * 获取存储在哈希表中指定hashKey对应的值
     *
     * @param suffix    key后缀 可空
     * @param hashKeys  hash表中的key 不可空
     */
    fun getValues(suffix: Any? = "", hashKeys: Collection<Any>): MutableList<Any?> {
        val key = buildKey(getPrefix(), suffix)
        return getHashOps().multiGet(key, hashKeys)
    }

    /**
     * 将多个键值对设置到hash表中
     *
     * @param suffix    key后缀 可空
     * @param keyValues 键值对 键不可空 值可空
     */
    fun setAll(suffix: Any? = "", keyValues: Map<Any, Any?>) {
        setAll(suffix, keyValues, getExpire())
    }

    /**
     * 将多个键值对设置到hash表中
     *
     * @param suffix    key后缀 可空
     * @param keyValues 键值对 键不可空 值可空
     * @param expire    过期时间 可空 为空不设置过期时间
     */
    fun setAll(suffix: Any? = "", keyValues: Map<Any, Any?>, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key
        getHashOps().putAll(key, keyValues)

        // 设置key过期
        expire(key, expire)
    }

    // 如果需要用到这里没有的方法，可以在这里加，或者使用getRedisTemplate()自己实现

}
