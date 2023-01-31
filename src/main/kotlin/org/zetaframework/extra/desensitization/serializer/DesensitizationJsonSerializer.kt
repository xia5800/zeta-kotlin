package org.zetaframework.extra.desensitization.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.zetaframework.extra.desensitization.annotation.Desensitization
import org.zetaframework.extra.desensitization.utils.DesensitizationUtil
import java.util.*

/**
 * 用于字段数据脱敏的Json序列化器
 *
 * @author gcc
 */
class DesensitizationJsonSerializer(): JsonSerializer<String>(), ContextualSerializer {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var rule: String? = ""
    private var symbol: String? = ""

    constructor(rule: String, symbol: String): this() {
        this.rule = rule
        this.symbol = symbol
    }

    /**
     * 序列化
     *
     * @param value 要序列化的值
     * @param jsonGenerator json内容生成器
     * @param serializers 可用于获取用于序列化对象的值包含的序列化器的提供程序 (如果有)。
     */
    override fun serialize(value: String?, jsonGenerator: JsonGenerator, serializers: SerializerProvider?) {
        if (value.isNullOrBlank()) return

        try {
            jsonGenerator.writeString(DesensitizationUtil.deserialization(value, rule, symbol))
        }catch (e: Exception) {
            logger.error("序列化失败：[${value}]", e)
        }
    }

    /**
     * 创建上下文
     *
     * @param prov 序列化器
     * @param property Bean字段
     */
    override fun createContextual(prov: SerializerProvider, property: BeanProperty?): JsonSerializer<*> {
        if (property != null) {
            // 如果字段类型是String
            if (Objects.equals(property.type.rawClass, String::class.java)) {
                // 获取字段上的@Desensitization注解
                var desensitization: Desensitization? = property.getAnnotation(Desensitization::class.java)
                if (desensitization == null) {
                    desensitization = property.getContextAnnotation(Desensitization::class.java)
                }

                // 如果获取到，构建DesensitizationJsonSerializer对象
                if (desensitization != null) {
                    return DesensitizationJsonSerializer(
                        desensitization.rule,
                        desensitization.symbol
                    )
                }
            }

            return prov.findValueSerializer(property.type, property)
        }
        return prov.findNullValueSerializer(null)
    }
}
