package org.zetaframework.core.redis.model

import org.springframework.data.redis.core.ListOperations
import java.time.Duration

/**
 * List类型的缓存key
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
interface ListCacheKey: CacheKey {

    fun getListOps(): ListOperations<String, Any> {
        return getRedisTemplate().opsForList()
    }


    /**
     * 通过索引【设置】列表元素的值
     *
     * @param suffix  key后缀 可空
     * @param index   索引值 不可空
     * @param value   缓存值 不可空
     */
    fun set(suffix: Any? = "", index: Long, value: Any) {
        val key = buildKey(getPrefix(), suffix)
        getListOps().set(key, index, value)
    }

    /**
     * 通过索引【获取】列表元素的值
     *
     * @param suffix  key后缀 可空
     * @param index   索引值 不可空
     * @return T     缓存值 查不到则返回null，查得到则返回对应的对象
     */
    fun <T> get(suffix: Any? = "", index: Long): T? {
        val key = buildKey(getPrefix(), suffix)
        return returnVal(getListOps().index(key, index) as T?)
    }

    /**
     * 删除集合中值等于value的元素
     *
     * @param suffix  key后缀 可空
     * @param index   索引值 不可空
     *        index=0, 删除所有值等于value的元素
     *        index>0, 从头部开始删除第index个值等于value的元素
     *        index<0, 从尾部开始删除第index个值等于value的元素
     * @param value   缓存值 不可空
     */
    fun delete(suffix: Any? = "", index: Long, value: Any): Long? {
        val key = buildKey(getPrefix(), suffix)
        return getListOps().remove(key, index, value)
    }

    /**
     * 添加到list最末尾 （等价于rPush）
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     */
    fun push(suffix: Any? = "", value: Any) {
        push(suffix, value, getExpire())
    }

    /**
     * 添加到list最末尾 （等价于rPush）
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun push(suffix: Any? = "", value: Any, expire: Duration? = null) {
        rPush(suffix, value, expire)
    }

    /**
     * 添加到list最前面
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     */
    fun lPush(suffix: Any? = "", value: Any) {
        lPush(suffix, value, getExpire())
    }

    /**
     * 添加到list最前面
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun lPush(suffix: Any? = "", value: Any, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key。
        getListOps().leftPush(key, value)

        // 设置key过期
        expire(key, expire)
    }

    /**
     * 添加到list最末尾
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     */
    fun rPush(suffix: Any? = "", value: Any) {
        rPush(suffix, value, getExpire())
    }

    /**
     * 添加到list最末尾
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun rPush(suffix: Any? = "", value: Any, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key。
        getListOps().rightPush(key, value)

        // 设置key过期
        expire(key, expire)
    }

    /**
     * 添加到list最末尾 （等价于rPush）
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 可空  为空缓存NullVal对象
     */
    fun pushAll(suffix: Any? = "", value: Collection<Any>) {
        pushAll(suffix, value, getExpire())
    }

    /**
     * 添加到list最末尾 （等价于rPush）
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 可空  为空缓存NullVal对象
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun pushAll(suffix: Any? = "", value: Collection<Any>, expire: Duration? = null) {
        rPushAll(suffix, value, expire)
    }

    /**
     * 添加到list最前面
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     */
    fun lPushAll(suffix: Any? = "", value: Collection<Any>) {
        lPushAll(suffix, value, getExpire())
    }

    /**
     * 添加到list最前面
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun lPushAll(suffix: Any? = "", value: Collection<Any>, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key。
        getListOps().leftPushAll(key, *value.toTypedArray())

        // 设置key过期
        expire(key, expire)
    }

    /**
     * 添加到list最末尾
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     */
    fun rPushAll(suffix: Any? = "", value: Collection<Any>) {
        rPushAll(suffix, value, getExpire())
    }

    /**
     * 添加到list最末尾
     *
     * @param suffix key后缀 可空
     * @param value  缓存值 不可空
     * @param expire key过期时间 可空 为空不设置过期时间
     */
    fun rPushAll(suffix: Any? = "", value: Collection<Any>, expire: Duration? = null) {
        val key = buildKey(getPrefix(), suffix)

        // 缓存key。
        getListOps().rightPushAll(key, *value.toTypedArray())

        // 设置key过期
        expire(key, expire)
    }

    // 如果需要用到这里没有的方法，可以在这里加，或者使用getListOps()自己实现
}
