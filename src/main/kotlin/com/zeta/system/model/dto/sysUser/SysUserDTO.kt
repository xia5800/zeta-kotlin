package com.zeta.system.model.dto.sysUser

import com.zeta.system.model.dto.sysRole.SysRoleDTO
import io.swagger.v3.oas.annotations.media.Schema
import org.zetaframework.extra.desensitization.annotation.Desensitization
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 用户详情
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "用户详情")
data class SysUserDTO(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @Schema(description = "创建人")
    var createdBy: Long? = null,

    /** 修改时间 */
    @Schema(description = "修改时间")
    var updateTime: LocalDateTime? = null,

    /** 修改人 */
    @Schema(description = "修改人")
    var updatedBy: Long? = null,

    /** 状态 */
    @Schema(description = "状态")
    var state: Int? = null,

    /** 用户名 */
    @Schema(description = "用户名")
    var username: String? = null,

    /** 账号 */
    @Desensitization(rule = "3_4") // 账号脱敏
    @Schema(description = "账号")
    var account: String? = null,

    /** 密码 */
    @Desensitization(rule = "3_4") // 密码脱敏
    @Schema(description = "密码")
    var password: String? = null,

    /** 邮箱 */
    @Desensitization(rule = "3~@") // 邮箱脱敏
    @Schema(description = "邮箱")
    var email: String? = null,

    /** 手机号 */
    @Desensitization(rule = "3_4") // 手机号脱敏
    @Schema(description = "手机号")
    var mobile: String? = null,

    /** 性别 */
    @Schema(description = "性别")
    var sex: Int? = null,

    /** 头像 */
    @Schema(description = "头像")
    var avatar: String? = null,

    /** 生日 */
    @Schema(description = "生日")
    var birthday: LocalDate? = null,

    /** 用户角色 */
    @Schema(description = "用户角色")
    var roles: List<SysRoleDTO>? = null,
)
