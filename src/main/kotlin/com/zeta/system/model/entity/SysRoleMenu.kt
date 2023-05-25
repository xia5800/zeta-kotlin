package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.zetaframework.base.entity.SuperEntity

/**
 * 角色菜单
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "角色菜单")
@TableName(value = "sys_role_menu")
class SysRoleMenu(): SuperEntity<Long>() {

    /** 角色id */
    @Schema(description = "角色id")
    @get:NotNull(message = "角色id不能为空")
    @TableField(value = "role_id")
    var roleId: Long? = null

    /** 菜单id */
    @Schema(description = "菜单id")
    @get:NotNull(message = "菜单id不能为空")
    @TableField(value = "menu_id")
    var menuId: Long? = null

    constructor(roleId: Long?, menuId: Long?): this() {
        this.roleId = roleId
        this.menuId = menuId
    }

    override fun toString(): String {
        return "SysRoleMenu(id=$id, createTime=$createTime, createdBy=$createdBy, roleId=$roleId, menuId=$menuId)"
    }

}
