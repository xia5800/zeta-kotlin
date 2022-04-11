package com.zeta.common.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.extra.websocket.enums.WsUserTypeEnum
import org.zetaframework.extra.websocket.event.WsUserEventListener

/**
 * websocket事件配置
 * @author gcc
 */
@Configuration
class WebsocketEventConfiguration {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    @Bean
    fun wsUserEventListener(): WsUserEventListener = WsUserEventListener { user, userType ->
        when(userType) {
            WsUserTypeEnum.ONLINE -> logger.info("websocket用户上线：${user?.userId}")
            WsUserTypeEnum.OFFLINE -> logger.info("websocket用户离线：${user?.userId}")
        }
    }
}
