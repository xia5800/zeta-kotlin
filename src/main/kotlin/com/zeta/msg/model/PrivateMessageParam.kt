package com.zeta.msg.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

/**
 * 私聊消息参数
 *
 * @author gcc
 */
@Schema(description = "私聊消息")
data class PrivateMessageParam(
    /** 接收人 */
    @Schema(description = "接收人")
    @get:NotBlank(message = "消息接收人不能为空")
    var toUserId: String? = null,

    /** 发送的消息 */
    @Schema(description = "发送的消息")
    @get:NotBlank(message= "消息不能为空")
    var message: String? = null
)
