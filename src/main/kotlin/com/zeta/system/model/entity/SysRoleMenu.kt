package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.SuperEntity

/**
 * 角色菜单
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "角色菜单")
@TableName(value = "sys_role_menu")
class SysRoleMenu(): SuperEntity<Long>() {

    /** 角色id */
    @ApiModelProperty("角色id")
    @TableField(value = "role_id")
    var roleId: Long? = null

    /** 菜单id */
    @ApiModelProperty("菜单id")
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
