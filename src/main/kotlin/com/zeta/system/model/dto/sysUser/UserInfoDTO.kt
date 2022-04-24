package com.zeta.system.model.dto.sysUser

import io.swagger.annotations.ApiModelProperty

/**
 * 用户信息
 * @author gcc
 */
data class UserInfoDTO(
    /** 用户id */
    @ApiModelProperty("用户id")
    var id: Long? = null,

    /** 用户名 */
    @ApiModelProperty("用户名")
    var username: String? = null,

    /** 账号 */
    @ApiModelProperty("账号")
    var account: String? = null,

    /** 性别 */
    @ApiModelProperty("性别 0未知 1男 2女", example = "0", allowableValues = "0,1,2")
    var sex: Int? = null,

    /** 头像 */
    @ApiModelProperty("头像")
    var avatar: String? = null,

    /** 状态 */
    @ApiModelProperty("状态")
    var state: Int? = null,

    /** 角色列表 */
    @ApiModelProperty("角色列表")
    var roles: List<String>? = null,

    /** 权限列表 */
    @ApiModelProperty("权限列表")
    var permissions: List<String>? = null,
)
