package org.zetaframework.extra.websocket

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.zetaframework.extra.websocket.interceptor.WsUserInterceptor
import org.zetaframework.extra.websocket.properties.WebsocketProperties


/**
 * Websocket配置
 *
 * 说明：使用stomp协议
 * @author gcc
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(WebsocketProperties::class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = WebsocketProperties.PREFIX, name = ["enabled"], havingValue = "true", matchIfMissing = false)
class WebsocketStompConfiguration(
    private val userInterceptor: WsUserInterceptor
) : WebSocketMessageBrokerConfigurer {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        logger.info("websocket配置：启动")
    }

    /**
     * 注册stomp端点
     */
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // 添加端点。 前端 new SockJs("http://xxxx:8080/ws")
        registry.addEndpoint("/ws") // 记得配置saToken放开接口拦截
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }


    /**
     * 配置消息代理选项
     */
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 基于内存的STOMP消息代理
        registry.enableSimpleBroker("/queue", "/topic")
        // ↑↑↑↑↑ 配置详细说明 ↑↑↑↑↑
        // 上面的代码，开放了两个公共的【订阅】地址：即 "/queue/**"、"/topic/**"
        // 也就是说: 前端subscribe("/abc/xxx/xxx",...)、subscribe("/ddd/xxx",...)是无法接收到后端推送过来的消息的
        // 前端只有订阅了"/queue/**"、"/topic/**"地址，才能收到后端推送过来的消息
        //
        // 案例：
        // 后端给所有订阅了"/topic/welcome"地址的前端设备发送消息
        // 代码方式：simpMessagingTemplate.convertAndSend("/topic/welcome", "尊敬的用户，欢迎回来")
        // 注解方式：@SendTo("/topic/welcome")
        //
        // 更多详细案例参考：com.zeta.msg.controller.ChartController、 com.zeta.msg.controller.WebsocketController.kt

        registry.setUserDestinationPrefix("/user")
        // ↑↑↑↑↑ 配置详细说明 ↑↑↑↑↑
        // 上面的代码，配置了接收一对一私聊信息的【订阅】地址
        // 需要配合公共订阅地址使用，即 "/user/queue/**"、"/user/topic/**"
        // 也就是说: 前端subscribe("/user/abc/xxx/xxx",...)、subscribe("/user/ddd/xxx",...)是无法接收到后端推送过来的消息的
        // 前端只有订阅了"/user/queue/**"、"/user/topic/**"这两个地址，才能收到后端推送过来的消息
        //
        // 案例：
        // 后端给订阅了"/user/topic/message"并且用户id为"123"的用户发送消息
        // 代码方式：simpMessagingTemplate.convertAndSendToUser("123", "/topic/message", "嘿，大兄弟在吗？")
        // 注解方式：@SendToUser("/topic/message")
        //
        // 更多详细案例参考：com.zeta.msg.controller.ChartController、com.zeta.msg.controller.WebsocketController.kt

        // 基于MQ 的STOMP消息代理
        // registry.enableStompBrokerRelay("/queue", "/topic")
        //     .setRelayHost(host)
        //     .setRelayPort(port)
        //     .setClientLogin(userName)
        //     .setClientPasscode(password)
    }

    /**
     * 配置拦截器
     *
     * @param registration
     */
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(userInterceptor)
    }

}
