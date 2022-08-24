package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

/**
 * 修改密码参数
 * @author gcc
 */
@ApiModel(description = "修改密码参数")
data class ChangePasswordParam(

    /** 旧密码 */
    @ApiModelProperty(value = "旧密码")
    @get:NotBlank(message = "旧密码不能为空")
    var oldPwd: String? = null,

    /** 新密码 */
    @ApiModelProperty(value = "新密码")
    @get:NotBlank(message = "新密码不能为空")
    var newPwd: String? = null,
)
