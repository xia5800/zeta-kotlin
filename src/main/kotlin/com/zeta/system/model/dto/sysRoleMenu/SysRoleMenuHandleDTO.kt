package com.zeta.system.model.dto.sysRoleMenu

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

/**
 * 批量新增、修改角色菜单关联关系
 *
 * @author gcc
 */
@Schema(description = "批量新增、修改角色菜单关联关系")
data class SysRoleMenuHandleDTO (

    /** 角色id */
    @Schema(description = "角色id", required = true)
    @get:NotNull(message = "角色id不能为空")
    var roleId: Long? = null,


    /** 菜单id列表 为空代表清空角色与菜单的关联 */
    @Schema(description = "菜单id列表 为空代表清空角色与菜单的关联", required = false)
    var menuIds: MutableList<Long>? = null,
)
