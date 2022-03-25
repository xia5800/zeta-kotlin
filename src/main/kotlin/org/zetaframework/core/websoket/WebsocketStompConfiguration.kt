package org.zetaframework.core.websoket

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Primary
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.zetaframework.core.websoket.interceptor.WsUserInterceptor
import org.zetaframework.core.websoket.properties.WebsocketProperties
import java.util.concurrent.ThreadPoolExecutor


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
@ConditionalOnProperty(prefix = WebsocketProperties.PREFIX, name = ["enabled"], havingValue = "true", matchIfMissing = true)
class WebsocketStompConfiguration(
    private val websocketProperties: WebsocketProperties,
    @Lazy private val taskExecutor: TaskScheduler,
    private val userInterceptor: WsUserInterceptor
): WebSocketMessageBrokerConfigurer {
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
            .setHeartbeatValue(longArrayOf(websocketProperties.heartbeat, websocketProperties.heartbeat))
            .setTaskScheduler(taskExecutor)

        // 基于MQ 的STOMP消息代理
        // registry.enableStompBrokerRelay("/queue", "/topic")
        //     .setRelayHost(host)
        //     .setRelayPort(port)
        //     .setClientLogin(userName)
        //     .setClientPasscode(password)
    }

    /**
     * 配置拦截器
     */
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(userInterceptor).taskExecutor(threadPoolTaskExecutor())
    }


    @Primary
    @Bean
    fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor = ThreadPoolTaskExecutor().apply {
        // 核心线程数
        this.corePoolSize = 2
        // 最大线程数
        this.maxPoolSize = 50
        // 线程存活时间
        this.keepAliveSeconds = 300
        // 队列容量
        this.setQueueCapacity(1000)
        // 线程前缀名称
        this.setThreadNamePrefix("zeta-async-executor-")
        // 拒绝策略
        this.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
    }

}
