package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * 登录参数
 * @author gcc
 */
@ApiModel(description = "登录参数")
data class LoginParam(

    /** 账号 */
    @ApiModelProperty(value = "账号", required = true)
    @get:NotBlank(message = "账号不能为空")
    var account: String? = null,

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true)
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,

    /** 验证码key */
    @ApiModelProperty(value = "验证码key", required = true)
    @get:NotNull(message = "验证码key不能为空")
    var key: Long? = null,

    /** 验证码 */
    @ApiModelProperty(value = "验证码", required = true)
    @get:NotBlank(message = "验证码不能为空")
    val code: String? = null,
)
