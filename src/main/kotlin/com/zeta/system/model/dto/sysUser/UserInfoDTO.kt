package com.zeta.system.model.dto.sysUser

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 用户信息
 *
 * @author gcc
 */
@Schema(description = "用户信息")
data class UserInfoDTO(
    /** 用户id */
    @Schema(description = "用户id")
    var id: Long? = null,

    /** 用户名 */
    @Schema(description = "用户名")
    var username: String? = null,

    /** 账号 */
    @Schema(description = "账号")
    var account: String? = null,

    /** 性别 */
    @Schema(description = "性别 0未知 1男 2女", example = "0", allowableValues = ["0","1","2"])
    var sex: Int? = null,

    /** 头像 */
    @Schema(description = "头像")
    var avatar: String? = null,

    /** 状态 */
    @Schema(description = "状态")
    var state: Int? = null,

    /** 角色列表 */
    @Schema(description = "角色列表")
    var roles: List<String>? = null,

    /** 权限列表 */
    @Schema(description = "权限列表")
    var permissions: List<String>? = null,
)
