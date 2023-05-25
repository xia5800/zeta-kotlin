package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 用户 查询参数
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "用户查询参数")
data class SysUserQueryParam (

    /** 用户id */
    @Schema(description = "用户id")
    var id: Long? = null,

    /** 用户名 */
    @Schema(description = "用户名")
    var username: String? = null,

    /** 账号 */
    @Schema(description = "账号")
    var account: String? = null,

    /** 邮箱 */
    @Schema(description = "邮箱")
    var email: String? = null,

    /** 手机号 */
    @Schema(description = "手机号")
    var mobile: String? = null,

    /** 性别 */
    @Schema(description = "性别")
    var sex: Int? = null,

    /** 状态 */
    @Schema(description = "状态")
    var state: Int? = null,
)
