package org.zetaframework.core.xss.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.zetaframework.core.xss.cleaner.XssCleaner
import org.zetaframework.core.xss.properties.XssProperties

/**
 * 自定义用于XSS防护的Json反序列化器
 *
 * @author gcc
 */
class XssStringJsonDeserializer(
    private val xssCleaner: XssCleaner,
    private val xssProperties: XssProperties
): JsonDeserializer<String>() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 反序列化
     *
     * @param p 用于读取JSON内容的JsonParser对象
     * @param ctxt 可用于访问有关此反序列化活动的信息的上下文。
     * @return
     */
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): String? {
        if (!p.hasToken(JsonToken.VALUE_STRING)) {
            return null
        }

        // 获取要清理的文本内容
        val value = p.valueAsString
        if (value.isNullOrBlank()) return value

        // XSS防护开关是否关闭
        if (!xssProperties.enabled) return value
        // 当前url是否是忽略xss防护的地址
        if (xssProperties.isIgnoreUrl()) return value

        // 清理有XSS风险的文本
        logger.debug("正在执行xss反序列化。。。。。需要清理的文本内容为：$value") // ps:生产环境打印日志可能会有风险
        return xssCleaner.clear(value)
    }
}
