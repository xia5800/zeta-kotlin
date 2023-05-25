package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * 登录参数
 * @author gcc
 */
@Schema(description = "登录参数")
data class LoginParam(

    /** 账号 */
    @Schema(description = "账号", required = true)
    @get:NotBlank(message = "账号不能为空")
    var account: String? = null,

    /** 密码 */
    @Schema(description = "密码", required = true)
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,

    /** 验证码key */
    @Schema(description = "验证码key", required = true)
    @get:NotNull(message = "验证码key不能为空")
    var key: Long? = null,

    /** 验证码 */
    @Schema(description = "验证码", required = true)
    @get:NotBlank(message = "验证码不能为空")
    val code: String? = null,
)
