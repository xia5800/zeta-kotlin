package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * 重置密码参数
 * @author gcc
 */
@Schema(description = "重置密码参数")
data class ResetPasswordParam(

    /** 用户id */
    @Schema(description = "用户id", required = true)
    @get:NotNull(message = "用户id不能为空")
    var id: Long? = null,

    /** 密码 */
    @Schema(description = "密码", required = true)
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,
)
