package org.zetaframework.core.xss.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.zetaframework.core.xss.cleaner.XssCleaner
import org.zetaframework.core.xss.properties.XssProperties

/**
 * 自定义用于XSS防护的Json序列化器
 *
 * @author gcc
 */
class XssStringJsonSerializer(
    private val xssCleaner: XssCleaner,
    private val xssProperties: XssProperties
): JsonSerializer<String>() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 处理类型
     */
    override fun handledType(): Class<String> {
        return String::class.java
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
            if (xssProperties.enabled) {
                // 清理有XSS风险的文本
                val filterValue = xssCleaner.clear(value)
                jsonGenerator.writeString(filterValue)
            } else {
                jsonGenerator.writeString(value)
            }
        }catch (e: Exception) {
            logger.error("序列化失败：[${value}]", e)
        }
    }
}
