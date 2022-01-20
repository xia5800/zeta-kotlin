package org.zetaframework.core.redis.serializer

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.springframework.data.redis.serializer.RedisSerializer
import java.nio.charset.Charset

/**
 * FastJson实现redis序列化和反序列化
 *
 * @author gcc
 */
class FastJsonRedisSerializer<T>(private val clazz: Class<T>) : RedisSerializer<T> {
    private val charset = Charset.forName("utf-8")

    /**
     * 将给定的对象序列化为二进制数据。
     *
     * @param t 要序列化的对象。可以为空。
     * @return 等效的二进制数据。可以为空。
     */
    override fun serialize(t: T?): ByteArray? {
        return JSON.toJSONBytes(t, SerializerFeature.WriteClassName)
    }

    /**
     * 从给定的二进制数据反序列化一个对象。
     *
     * @param bytes 对象二进制表示。可以为空。
     * @return 等效的对象实例。可以为空。
     */
    override fun deserialize(bytes: ByteArray?): T? {
        if(bytes == null || bytes.isEmpty()) return null
        val str = String(bytes, charset)
        return JSON.parseObject<T>(str, clazz)
    }
}
