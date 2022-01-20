package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.TreeEntity

/**
 * 菜单
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "菜单")
@TableName(value = "sys_menu")
class SysMenu(

    /** 菜单路由地址 */
    @ApiModelProperty("菜单路由地址")
    @TableField(value = "path")
    var path: String? = null,

    /** 组件路由地址 */
    @ApiModelProperty("组件路由地址")
    @TableField(value = "component")
    var component: String? = null,

    /** 图标 */
    @ApiModelProperty("图标")
    @TableField(value = "icon")
    var icon: String? = null,

    /** 图标颜色 */
    @ApiModelProperty("图标颜色")
    @TableField(value = "color")
    var color: String? = null,

    /** 权限标识 */
    @ApiModelProperty("权限标识")
    @TableField(value = "authority")
    var authority: String? = null,

    /** 菜单类型 */
    @ApiModelProperty("菜单类型")
    @TableField(value = "type")
    var type: String? = null,

    /** 是否删除 true or false  */
    @ApiModelProperty("是否删除 true or false")
    @TableLogic
    var deleted: Boolean? = null,

    /** 角色权限树选中状态 */
    @ApiModelProperty("角色权限树选中状态")
    @TableField(exist = false)
    var checked: Boolean? = null,
): TreeEntity<SysMenu, Long>() {

    override fun toString(): String {
        return "SysMenu(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, parentId=$parentId, sortValue=$sortValue, path=$path, component=$component, icon=$icon, color=$color, authority=$authority, type=$type, deleted=$deleted)"
    }
}
