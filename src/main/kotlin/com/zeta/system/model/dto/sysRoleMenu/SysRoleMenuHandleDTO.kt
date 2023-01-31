package com.zeta.system.model.dto.sysRoleMenu

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

/**
 * 批量新增、修改角色菜单关联关系
 * @author gcc
 */
@ApiModel(description = "批量新增、修改角色菜单关联关系")
data class SysRoleMenuHandleDTO (

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    @get:NotNull(message = "角色id不能为空")
    var roleId: Long? = null,


    /** 菜单id列表 */
    @ApiModelProperty(value = "菜单id列表，为空代表清空角色与菜单的关联")
    var menuIds: MutableList<Long>? = null,
)
