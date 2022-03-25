package org.zetaframework.core.websoket.event

import org.springframework.context.ApplicationEvent
import org.zetaframework.core.websoket.enums.WsUserTypeEnum
import org.zetaframework.core.websoket.model.WsUser

/**
 * Websocket用户 事件
 *
 * 说明：
 * 主要用来发送用户上线、下线事件通知
 * @author gcc
 */
class WsUserEvent(val user: WsUser?, source: WsUserTypeEnum): ApplicationEvent(source) {

}
