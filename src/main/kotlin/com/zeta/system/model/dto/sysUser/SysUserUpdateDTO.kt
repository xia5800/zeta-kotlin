package com.zeta.system.model.dto.sysUser

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * 修改 用户
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "修改用户")
data class SysUserUpdateDTO(

    /** 用户id */
    @ApiModelProperty("用户id")
    @get:NotNull(message = "id不能为空")
    var id: Long? = null,

    /** 用户名 */
    @ApiModelProperty("用户名")
    @get:NotBlank(message = "用户名不能为空")
    var username: String? = null,

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
    var roleIds: List<Long>? = null,
)
