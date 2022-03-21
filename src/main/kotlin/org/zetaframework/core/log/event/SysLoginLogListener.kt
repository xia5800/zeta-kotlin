package org.zetaframework.core.log.event

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.zetaframework.core.log.model.SysLoginLogDTO

/**
 * 登录日志事件监听
 *
 * 使用说明：
 * 1. 在业务包中，@Bean配置一个SysLoginLogListener
 * 2. 保存登录日志的方式交给具体的业务去实现
 * @author gcc
 */
open class SysLoginLogListener constructor(consumer: (loginLogDTO: SysLoginLogDTO) -> Unit){
    private var block = consumer

    /**
     * 保存登录日志
     *
     * 说明：
     * 该方法不实现，交给具体业务去实现
     */
    @Async
    @EventListener(SysLoginEvent::class)
    open fun saveSysLog(event: SysLoginEvent) {
        val loginLogDTO = event.source as SysLoginLogDTO
        block.invoke(loginLogDTO)
    }
}
