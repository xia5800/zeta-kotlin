package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

/**
 * 修改密码参数
 * @author gcc
 */
@Schema(description = "修改密码参数")
data class ChangePasswordParam(

    /** 旧密码 */
    @Schema(description = "旧密码", required = true)
    @get:NotBlank(message = "旧密码不能为空")
    var oldPwd: String? = null,

    /** 新密码 */
    @Schema(description = "新密码", required = true)
    @get:NotBlank(message = "新密码不能为空")
    var newPwd: String? = null,
)
