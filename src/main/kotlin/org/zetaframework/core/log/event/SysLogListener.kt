package org.zetaframework.core.log.event

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.zetaframework.core.log.model.SysLogDTO

/**
 * 系统日志事件监听
 *
 * 使用说明：
 * 1. 在业务包中，@Bean配置一个SysLogListener
 * 2. 保存系统日志的方式交给具体的业务去实现
 * @author gcc
 */
open class SysLogListener(private val consumer: (sysLogDTO: SysLogDTO) -> Unit) {

    /**
     * 保存系统日志
     *
     * 说明：
     * 该方法不实现，交给具体业务去实现
     *
     * @param event 操作日志事件
     */
    @Async
    @EventListener(SysLogEvent::class)
    open fun saveSysLog(event: SysLogEvent) {
        val sysLogDTO = event.source as SysLogDTO
        consumer.invoke(sysLogDTO)
    }
}
