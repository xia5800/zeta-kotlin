package com.zeta.system.model.dto.sysUser

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * 新增 用户
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "新增用户")
data class SysUserSaveDTO(

    /** 用户名 */
    @ApiModelProperty("用户名")
    @get:Size(max = 32, message = "用户名长度不能大于32")
    @get:NotBlank(message = "用户名不能为空")
    var username: String? = null,

    /** 账号 */
    @ApiModelProperty("账号")
    @get:Size(max = 64, message = "账号长度不能大于64")
    @get:NotBlank(message = "账号不能为空")
    var account: String? = null,

    /** 密码 */
    @ApiModelProperty("密码")
    @get:Size(max = 10, message = "密码长度不能大于10")
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,

    /** 邮箱 */
    @ApiModelProperty("邮箱")
    var email: String? = null,

    /** 手机号 */
    @ApiModelProperty("手机号")
    var mobile: String? = null,

    /** 性别 */
    @ApiModelProperty("性别")
    var sex: Int? = null,

    /** 生日 */
    @ApiModelProperty("生日")
    var birthday: LocalDate? = null,

    /** 角色 */
    @ApiModelProperty("角色id列表")
    @get:NotEmpty(message = "角色不能为空")
    var roleIds: List<Long>? = null,
)
