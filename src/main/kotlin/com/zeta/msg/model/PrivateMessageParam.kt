package com.zeta.msg.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank

/**
 * 私聊消息参数
 *
 * @author gcc
 */
@ApiModel(description = "私聊消息")
data class PrivateMessageParam(
    /** 接收人 */
    @ApiModelProperty(value = "接收人")
    @get:NotBlank(message = "消息接收人不能为空")
    var toUserId: String? = null,

    /** 发送的消息 */
    @ApiModelProperty(value = "发送的消息")
    @get:NotBlank(message= "消息不能为空")
    var message: String? = null
)
