package org.zetaframework.core.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.core.log.aspect.SysLogAspect
import org.zetaframework.core.log.properties.LogProperties

/**
 * 系统日志配置
 *
 * 说明：
 * 考虑到不是所有项目都有记录用户操作日志的需求。故将日志记录功能剥离出来放到zetaframework核心包中。
 * 开发者可以在配置文件中将"zeta.log.enable"的值改成false。这样就关闭用户操作日志记录功能了
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(LogProperties::class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = ["enabled"], havingValue = "true", matchIfMissing = true)
class SysLogConfiguration(private val context: ApplicationContext) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 系统日志切面
     * @return SysLogAspect
     */
    @Bean
    @ConditionalOnMissingBean
    fun sysLogAspect() : SysLogAspect = run {
        logger.info("记录用户操作日志功能：启动")
        SysLogAspect(context)
    }

}
