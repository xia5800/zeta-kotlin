package org.zetaframework.core.redis

import cn.hutool.core.text.StrPool
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.zetaframework.core.jackson.KtObjectMapper
import org.zetaframework.core.redis.helper.RedisHelper
import org.zetaframework.core.redis.serializer.CustomJackson2JsonRedisSerializer
import java.time.Duration

/**
 * redis 配置类
 *
 * @author gcc
 */
@EnableCaching
@Configuration
class RedisConfiguration {

    /**
     * 配置spring cache管理器
     *
     * @param connectionFactory
     * @return CacheManager
     */
    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        val jsonRedisSerializer = getRedisSerializer()
        val stringRedisSerializer = StringRedisSerializer()

        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            // 缓存双冒号变成单冒号  test::key::123  -> test:key:123
            .computePrefixWith { prefix: String -> prefix + StrPool.COLON }
            // 禁用空值
            .disableCachingNullValues()
            // 使用StringRedisSerializer来序列化和反序列化redis的key值
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
            // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
            // 缓存有效期 // TODO 应该从配置文件中读取
            .entryTtl(Duration.ofDays(1))
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(configuration).build()
    }


    /**
     * 配置 RedisTemplate
     *
     * @param connectionFactory
     * @return RedisTemplate<String, Any>
     */
    @Bean("redisTemplate")
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val jsonRedisSerializer = getRedisSerializer()
        val stringRedisSerializer = StringRedisSerializer()

        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(connectionFactory)
        redisTemplate.keySerializer = stringRedisSerializer
        redisTemplate.valueSerializer = jsonRedisSerializer
        redisTemplate.hashKeySerializer = stringRedisSerializer
        redisTemplate.hashValueSerializer = jsonRedisSerializer

        return redisTemplate
    }


    /**
     * 配置 StringRedisTemplate
     *
     * @param connectionFactory
     * @return StringRedisTemplate
     */
    @Bean("stringRedisTemplate")
    fun stringRedisTemplate(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val stringRedisTemplate = StringRedisTemplate()
        stringRedisTemplate.setConnectionFactory(connectionFactory)
        return stringRedisTemplate
    }

    /**
     * 配置Redis帮助类
     *
     * @param redisTemplate
     * @return RedisHelper
     */
    @Bean
    fun redisHelper(redisTemplate: RedisTemplate<String, Any>): RedisHelper {
        return RedisHelper(redisTemplate)
    }

    /**
     * 获取配置好的 Jackson2JsonRedisSerializer对象
     *
     * @return CustomJackson2JsonRedisSerializer<Any>
     */
    private fun getRedisSerializer(): CustomJackson2JsonRedisSerializer<Any> {
        return CustomJackson2JsonRedisSerializer(Any::class.java).also {
            it.setObjectMapper(KtObjectMapper.instance)
        }
    }
}
