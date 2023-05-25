package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 角色 查询参数
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "角色查询参数")
data class SysRoleQueryParam (

    /** 角色名 */
    @Schema(description = "角色名")
    var name: String? = null,

    /** 角色编码 */
    @Schema(description = "角色编码")
    var code: String? = null,

    /** 描述 */
    @Schema(description = "描述")
    var describe: String? = null,
)
