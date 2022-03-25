package org.zetaframework.core.websoket.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * websocket配置
 * @author gcc
 */
@ConfigurationProperties(prefix = WebsocketProperties.PREFIX)
class WebsocketProperties {
    companion object {
        const val PREFIX = "zeta.websocket"
    }

    /** websocket开关 默认为：false */
    var enabled: Boolean = false

    /** 心跳间隔 单位：毫秒  为0则不发送心跳 */
    var heartbeat: Long = 10 * 1000L

}
