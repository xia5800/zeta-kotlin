package com.zeta.system.model.dto.sysUser

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

/**
 * 修改 用户
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "修改用户")
data class SysUserUpdateDTO(

    /** 用户id */
    @Schema(description = "用户id", required = true)
    @get:NotNull(message = "id不能为空")
    var id: Long? = null,

    /** 用户名 */
    @Schema(description = "用户名", required = true)
    @get:Size(max = 32, message = "用户名长度不能大于32")
    @get:NotBlank(message = "用户名不能为空")
    var username: String? = null,

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
