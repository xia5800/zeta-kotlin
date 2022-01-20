package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 重置密码参数
 * @author gcc
 */
@ApiModel(description = "重置密码参数")
data class ResetPasswordParam(

    /** 用户id */
    @ApiModelProperty("用户id")
    @get:NotNull(message = "用户id不能为空")
    var id: Long? = null,

    /** 密码 */
    @ApiModelProperty("密码")
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,
)
