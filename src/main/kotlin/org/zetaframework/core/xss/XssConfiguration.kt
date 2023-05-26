package org.zetaframework.core.xss

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.zetaframework.core.xss.cleaner.HutoolXssCleaner
import org.zetaframework.core.xss.cleaner.XssCleaner
import org.zetaframework.core.xss.filter.XssFilter
import org.zetaframework.core.xss.properties.XssProperties
import org.zetaframework.core.xss.serializer.XssStringJsonDeserializer

/**
 * XSS跨站脚本攻击防护 配置
 *
 * 说明：
 * XSS攻击通常指的是通过利用网页开发时留下的漏洞，通过巧妙的方法注入恶意指令代码到网页，使用户加载并执行攻击者恶意制造的网页程序。
 * 这些恶意网页程序通常是JavaScript，但实际上也可以包括Java、 VBScript、ActiveX、 Flash 或者甚至是普通的HTML。
 * 攻击成功后，攻击者可能得到包括但不限于更高的权限（如执行一些操作）、私密网页内容、会话和cookie等各种内容。 -- 摘自：百度百科
 *
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(XssProperties::class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = XssProperties.PREFIX, name = ["enabled"], havingValue = "true", matchIfMissing = false)
class XssConfiguration(private val xssProperties: XssProperties) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        logger.info("XSS跨站脚本攻击防护功能：启动")
    }

    /**
     * 配置 XSS文本清理者
     *
     * @return XssCleaner
     */
    @Bean
    fun xssCleaner(): XssCleaner {
        // ps：这里图省事直接用的hutool的XSS过滤方法，如果需要自定义XSS过滤，仿造着实现一个XssCleaner接口就行
        return HutoolXssCleaner()
    }

    /**
     * 配置XSS过滤器
     *
     * @param xssCleaner
     * @return
     */
    @Bean
    fun xssFilter(xssCleaner: XssCleaner): FilterRegistrationBean<XssFilter> {
        val xssFilter = XssFilter(xssCleaner, xssProperties)
        return FilterRegistrationBean(xssFilter).apply {
            this.order = -1
        }
    }

    /**
     * 配置Json对象也能使用XSS清理
     *
     * @param xssCleaner
     * @return
     */
    @Bean
    fun jackson2ObjectMapperBuilderCustomizer2(xssCleaner: XssCleaner): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.deserializerByType(String::class.java, XssStringJsonDeserializer(xssCleaner, xssProperties))
        }
    }

}
