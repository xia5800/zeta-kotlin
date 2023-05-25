package com.zeta.system.model.dto.sysUser

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

/**
 * 新增 用户
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "新增用户")
data class SysUserSaveDTO(

    /** 用户名 */
    @Schema(description = "用户名", required = true)
    @get:Size(max = 32, message = "用户名长度不能大于32")
    @get:NotBlank(message = "用户名不能为空")
    var username: String? = null,

    /** 账号 */
    @Schema(description = "账号", required = true)
    @get:Size(max = 64, message = "账号长度不能大于64")
    @get:NotBlank(message = "账号不能为空")
    var account: String? = null,

    /** 密码 */
    @Schema(description = "密码", required = true)
    @get:Size(max = 10, message = "密码长度不能大于10")
    @get:NotBlank(message = "密码不能为空")
    var password: String? = null,

    /** 邮箱 */
    @Schema(description = "邮箱", required = false)
    var email: String? = null,

    /** 手机号 */
    @Schema(description = "手机号", required = false)
    var mobile: String? = null,

    /** 性别 */
    @Schema(description = "性别", required = true)
    @get:NotNull(message = "性别不能为空")
    var sex: Int? = null,

    /** 生日 */
    @Schema(description = "生日", required = false)
    var birthday: LocalDate? = null,

    /** 角色id列表 为空代表不关联用户角色 */
    @Schema(description = "角色id列表 为空代表不关联用户角色", required = false)
    var roleIds: List<Long>? = null,
)
