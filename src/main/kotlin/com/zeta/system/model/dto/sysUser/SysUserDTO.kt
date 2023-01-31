package com.zeta.system.model.dto.sysUser

import com.zeta.system.model.dto.sysRole.SysRoleDTO
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.extra.desensitization.annotation.Desensitization
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 用户详情
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "用户详情")
data class SysUserDTO(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    var createdBy: Long? = null,

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间")
    var updateTime: LocalDateTime? = null,

    /** 修改人 */
    @ApiModelProperty(value = "修改人")
    var updatedBy: Long? = null,

    /** 状态 */
    @ApiModelProperty(value = "状态")
    var state: Int? = null,

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    var username: String? = null,

    /** 账号 */
    @Desensitization(rule = "3_4") // 账号脱敏
    @ApiModelProperty(value = "账号")
    var account: String? = null,

    /** 密码 */
    @Desensitization(rule = "3_4") // 密码脱敏
    @ApiModelProperty(value = "密码")
    var password: String? = null,

    /** 邮箱 */
    @Desensitization(rule = "3~@") // 邮箱脱敏
    @ApiModelProperty(value = "邮箱")
    var email: String? = null,

    /** 手机号 */
    @Desensitization(rule = "3_4") // 手机号脱敏
    @ApiModelProperty(value = "手机号")
    var mobile: String? = null,

    /** 性别 */
    @ApiModelProperty(value = "性别")
    var sex: Int? = null,

    /** 生日 */
    @ApiModelProperty(value = "生日")
    var birthday: LocalDate? = null,

    /** 用户角色 */
    @ApiModelProperty(value = "用户角色")
    var roleIds: List<SysRoleDTO>? = null,
)
