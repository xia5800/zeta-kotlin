package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 用户 查询参数
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "用户查询参数")
data class SysUserQueryParam (

    /** 用户id */
    @ApiModelProperty("用户id")
    var id: Long? = null,

    /** 用户名 */
    @ApiModelProperty("用户名")
    var username: String? = null,

    /** 账号 */
    @ApiModelProperty("账号")
    var account: String? = null,

    /** 邮箱 */
    @ApiModelProperty("邮箱")
    var email: String? = null,

    /** 手机号 */
    @ApiModelProperty("手机号")
    var mobile: String? = null,

    /** 性别 */
    @ApiModelProperty("性别")
    var sex: Int? = null,

    /** 状态 */
    @ApiModelProperty("状态")
    var state: Int? = null,
)
