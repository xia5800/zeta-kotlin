package org.zetaframework.core.redis.serializer

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException
import org.zetaframework.core.redis.helper.NullVal

/**
 * 自定义Jackson2JsonRedisSerializer类
 *
 * 说明：
 * 1.该类的实现参考了Jackson2JsonRedisSerializer类
 * 2.为什么要自定义这个类？是为了反序列化时把[NullVal]对象转换成null对象
 *
 * 为什么要自定义这个类：
 * 为了防止缓存穿透，redisUtil中将null值替换成了[NullVal]对象。
 * 然后就导致同时使用Spring注解@Cacheable和自定义的CacheKey（使用RedisUtil也一样）缓存了null值之后，
 * “@Cacheable”在读取缓存值时会抛出异常，提示“[NullVal]对象不能转换为XXX对象”问题。
 *
 * 解决方法也很简单：
 * 在反序列化对象时，判断对象是否是[NullVal]对象，如果是则返回null。否则返回对象值
 *
 * @author gcc
 */
class CustomJackson2JsonRedisSerializer<T>(clazz: Class<T>): RedisSerializer<T> {
    /** java类型 */
    private val javaType: JavaType

    init {
        // 获取java类型
        javaType = getJavaType(clazz)
    }

    /**
     * 默认初始化一个ObjectMapper对象。（啥都没配置的那种）
     */
    private var objectMapper: ObjectMapper = ObjectMapper()

    /**
     * 设置一个自定义的ObjectMapper对象
     *
     * @param objectMapper 自定义的[ObjectMapper]
     */
    fun setObjectMapper(objectMapper: ObjectMapper) {
        this.objectMapper = objectMapper
    }

    /**
     * 获取java类型
     *
     * @param clazz Class<T>
     */
    private fun getJavaType(clazz: Class<T>): JavaType {
        return TypeFactory.defaultInstance().constructType(clazz)
    }

    /**
     * 利用Jackson的ObjectMapper将对象序列化成二进制数据
     *
     * @param t 要序列化的对象。可以为空。
     * @return 等效的二进制数据。可以为空。
     */
    override fun serialize(t: T?): ByteArray? {
        if (t == null) { return byteArrayOf() }

        try {
            return objectMapper.writeValueAsBytes(t)
        } catch (ex: Exception) {
            throw SerializationException("Could not write JSON: " + ex.message, ex)
        }
    }

    /**
     * 从给定的二进制数据反序列化对象
     *
     * @param bytes 对象的二进制数据。可以为空。
     * @return 等价的对象实例。可以为空。
     */
    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) { return null }

        try {
            // 得到序列化对象的值
            val value = objectMapper.readValue<T>(bytes, 0, bytes.size, javaType)
            // 判断对象是否为null或NullVal对象。
            return if (isNullVal(value)) null else value
        } catch (ex: Exception) {
            throw SerializationException("Could not read JSON: " + ex.message, ex)
        }
    }


    /**
     * 判断对象是否为空对象(null or [NullVal])
     *
     * @param value T
     * @return Boolean
     */
    private fun <T> isNullVal(value: T?): Boolean {
        return value == null || NullVal::class.java == value.javaClass
    }

}
