package org.zetaframework.extra.websocket.interceptor

import org.springframework.context.ApplicationContext
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessagingException
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.zetaframework.extra.websocket.enums.WsUserTypeEnum
import org.zetaframework.extra.websocket.event.WsUserEvent
import org.zetaframework.extra.websocket.model.WsUser

/**
 * Websocket用户信息 拦截器
 *
 * @author gcc
 */
@Component
class WsUserInterceptor(private val applicationContext: ApplicationContext) : ChannelInterceptor {

    /**
     * 在消息实际发送到通道之前调用。
     * 这允许在必要时修改消息。
     * 如果此方法返回null ，则不会发生实际的发送调用。
     */
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        // 解码消息
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)

        if (accessor != null) {
            when (accessor.command) {
                StompCommand.CONNECT -> {
                    // 获取用户信息
                    val userId = accessor.getFirstNativeHeader("userId")
                    if (userId.isNullOrBlank()) {
                        throw MessagingException("用户信息获取失败")
                    }

                    // 构造一个websocket用户
                    val wsUser = WsUser().apply { this.userId = userId }
                    accessor.user = wsUser

                    // 发布一个用户上线事件，用户上线之后要做的事交给具体的业务去实现
                    applicationContext.publishEvent(WsUserEvent(wsUser, WsUserTypeEnum.ONLINE))
                }
                StompCommand.DISCONNECT -> {
                    val wsUser = accessor.user
                    // 说明：临时解决客户端断开连接，触发两次DISCONNECT问题
                    if (wsUser != null && accessor.messageHeaders.size == 5) {
                        // 发布一个用户离线事件，用户离线之后要做的事交给具体的业务去实现
                        applicationContext.publishEvent(WsUserEvent(wsUser as WsUser, WsUserTypeEnum.OFFLINE))
                    }
                }
                else -> {
                }
            }
        }
        return message
    }
}
