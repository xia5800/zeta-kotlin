package com.zeta.system.model.result

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 登录返回结果
 * @author gcc
 */
@ApiModel(description = "登录返回结果")
data class LoginResult(

    @ApiModelProperty(value = "令牌")
    val token: String? = null,

    @ApiModelProperty(value = "用户基础信息")
    val user: LoginUserDTO? = null
)


/**
 * 登录返回的用户基础信息
 * @author gcc
 */
@ApiModel(description = "登录返回的用户基础信息")
data class LoginUserDTO(

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    var id: Long? = null,

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    var username: String? = null,

    /** 账号 */
    @ApiModelProperty(value = "账号")
    var account: String? = null,

    /** 性别 */
    @ApiModelProperty(value = "性别 0未知 1男 2女", example = "0", allowableValues = "0,1,2")
    var sex: Int? = null,

    /** 头像 */
    @ApiModelProperty(value = "头像")
    var avatar: String? = null,

    /** 状态 */
    @ApiModelProperty(value = "状态")
    var state: Int? = null,
)
