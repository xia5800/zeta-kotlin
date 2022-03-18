package com.zeta.config

import com.zeta.system.service.ISysOptLogService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.core.log.event.SysLogListener

/**
 * 系统用户操作日志配置
 *
 * 说明：
 * 考虑到不是所有项目都有记录用户操作日志的需求。故将日志记录功能剥离出来放到zetaframework核心包中。
 * 开发者可以在配置文件中将"zeta.log.enable"的值改成false。这样就关闭用户操作日志记录功能了
 * @author gcc
 */
@Configuration
class OperationLogConfiguration(private val sysOptLogService: ISysOptLogService) {

    /**
     * 配置系统日志事件监听 实现
     * @return [SysLogListener]
     */
    @Bean
    fun sysLogListener(): SysLogListener = SysLogListener(sysOptLogService::save)


}
