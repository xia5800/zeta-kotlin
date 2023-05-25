package com.zeta.system.model.dto.sysRole

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

/**
 * 新增 角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "新增角色")
data class SysRoleSaveDTO(

    /** 角色名 */
    @Schema(description = "角色名", required = true)
    @get:NotBlank(message = "角色名不能为空")
    var name: String? = null,

    /** 角色编码 */
    @Schema(description = "角色编码", required = true)
    @get:NotBlank(message = "角色编码不能为空")
    var code: String? = null,

    /** 描述 */
    @Schema(description = "描述", required = false)
    var describe: String? = null,
)
