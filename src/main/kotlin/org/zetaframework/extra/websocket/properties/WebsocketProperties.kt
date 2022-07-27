package org.zetaframework.extra.websocket.properties

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

}
