package com.zeta.msg.controller

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import com.zeta.msg.model.PrivateMessageParam
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.MessagingException
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zetaframework.base.result.ApiResult

/**
 * websocket测试
 *
 * @author gcc
 */
@Tag(name = "websocket测试", description = "websocket测试")
@RestController
@RequestMapping("/api/msg")
class WebsocketController(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val userRegistry: SimpUserRegistry,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    /**
     * 群发消息
     *
     * @param message 群发消息内容
     */
    @ApiOperationSupport(order = 1)
    @Operation(summary = "群发消息")
    @GetMapping("/group")
    fun group(@RequestParam message: String): ApiResult<Boolean> {
        return try {
            simpMessagingTemplate.convertAndSend("/topic/group", message)
            ApiResult.success()
        } catch (e: MessagingException) {
            logger.error("群发消息发送失败", e)
            ApiResult.fail()
        }
    }


    /**
     * 私聊消息
     *
     * @param message 私聊消息参数
     */
    @ApiOperationSupport(order = 2)
    @Operation(summary = "私聊消息")
    @PostMapping("/group")
    fun privateChat(@RequestBody @Validated message: PrivateMessageParam): ApiResult<Boolean> {
        return try {
            simpMessagingTemplate.convertAndSendToUser(message.toUserId!!, "/queue/private", message.message!!)
            ApiResult.success()
        }catch (e: MessagingException) {
            logger.error("私聊消息发送失败", e)
            return ApiResult.fail()
        }
    }


    /**
     * 获取当前在线人数
     *
     * @return ApiResult<Int>
     */
    @ApiOperationSupport(order = 3)
    @Operation(summary = "获取当前在线人数")
    @GetMapping("/onlineUser")
    fun onlineUser(): ApiResult<Int> {
        return ApiResult.success(data = userRegistry.userCount)
    }
}
