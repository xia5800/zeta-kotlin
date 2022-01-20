package org.zetaframework.core.jackson

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.ZoneId
import java.util.*

/**
 * jackson ObjectMapper配置
 *
 * @author gcc
 */
object KtObjectMapper {
    val instance: ObjectMapper = jacksonObjectMapper()

    /**
     * 自定义ObjectMapper配置
     */
    init {
        // 设置语言环境
        instance.setLocale(Locale.CHINA)
        // 设置时区
        instance.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        instance.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        // 指定序列化输入的类型, 这里不用`ObjectMapper.DefaultTyping.NON_FINAL`是因为kotlin的类都是final的
        instance.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_ARRAY)

        // 禁止:将日期写为时间戳，解决日期格式化问题
        instance.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        // 禁止:FAIL_ON_EMPTY_BEANS
        instance.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        // 禁止:忽略未知字段
        instance.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

        // 允许:对单引号处理
        instance.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
        // 允许:忽略不能转义的字符
        instance.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
        // 允许:JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
        instance.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature())

        // 扩展
        instance.registerModule(JavaTimeModule())
        instance.registerModule(Jdk8Module())
        instance.registerModule(KotlinModule.Builder().build())
        instance.findAndRegisterModules()
    }
}
