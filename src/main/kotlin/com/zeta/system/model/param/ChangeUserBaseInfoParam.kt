package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * 修改用户基本信息参数
 * @author gcc
 */
@ApiModel(description = "修改用户基本信息参数")
data class ChangeUserBaseInfoParam(

    /** 用户名  */
    @ApiModelProperty(value = "用户名", required = true)
    @get:NotEmpty(message = "用户名不能为空")
    @get:Size(max = 32, message = "用户名长度不能超过32")
    var username: String? = null,

    /** 邮箱  */
    @ApiModelProperty(value = "邮箱", required = false)
     var email: String? = null,

    /** 手机号  */
    @ApiModelProperty(value = "手机号", required = false)
     var mobile: String? = null,

    /** 性别 0未知 1男 2女  */
    @ApiModelProperty(value = "性别 0未知 1男 2女", required = true)
    @get:NotNull(message = "性别不能为空")
    var sex: Int? = null,

    /** 生日  */
    @ApiModelProperty(value = "生日", required = false)
    var birthday: LocalDate? = null,
)
