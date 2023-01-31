package org.zetaframework.core.jackson

import cn.hutool.core.date.DatePattern
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * 自定义Jackson配置
 * @author gcc
 */
@Configuration
class JacksonConfiguration {

    /**
     * 全局Jackson序列化配置
     */
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            // 序列化
            builder.serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER))
            builder.serializerByType(LocalTime::class.java, LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER))
            builder.serializerByType(LocalDate::class.java, LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER))
            builder.serializerByType(Long::class.java, ToStringSerializer.instance)
            builder.serializerByType(Long::class.javaObjectType, ToStringSerializer.instance)
            builder.serializerByType(BigDecimal::class.java, ToStringSerializer.instance)
            builder.serializerByType(BigInteger::class.java, ToStringSerializer.instance)
            // 反序列化
            builder.deserializerByType(LocalDateTime::class.java, LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER))
            builder.deserializerByType(LocalTime::class.java, LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER))
            builder.deserializerByType(LocalDate::class.java, LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER))

            // 配置枚举使用toString方式
            builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        }
    }
}
