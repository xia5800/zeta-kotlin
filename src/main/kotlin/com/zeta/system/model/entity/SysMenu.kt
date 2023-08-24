package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.zeta.system.model.enums.MenuTypeEnum
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.TreeEntity
import javax.validation.constraints.NotNull

/**
 * 菜单
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@ApiModel(description = "菜单")
@TableName(value = "sys_menu")
class SysMenu: TreeEntity<SysMenu, Long>() {

    /** 路由名称 */
    @ApiModelProperty(value = "路由名称", required = true)
    @TableField(value = "name")
    var name: String? = null

    /** 路由地址 */
    @ApiModelProperty(value = "路由地址", required = false)
    @TableField(value = "path")
    var path: String? = null

    /** 组件地址 */
    @ApiModelProperty(value = "组件地址", required = false)
    @TableField(value = "component")
    var component: String? = null

    /** 重定向地址 */
    @ApiModelProperty(value = "重定向地址", required = false)
    @TableField(value = "redirect")
    var redirect: String? = null

    /** 图标 */
    @ApiModelProperty(value = "图标", required = false)
    @TableField(value = "icon")
    var icon: String? = null

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识", required = false)
    @TableField(value = "authority")
    var authority: String? = null

    /** 菜单类型 */
    @ApiModelProperty(value = "菜单类型", required = true)
    @get:NotNull(message = "菜单类型不能为空")
    @TableField(value = "type")
    var type: MenuTypeEnum? = null

    /** 逻辑删除字段 */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除字段", hidden = true, required = true)
    @TableLogic
    var deleted: Boolean? = null

    /** 是否隐藏 0否 1是 */
    @ApiModelProperty(value = "是否隐藏 0否 1是", required = false)
    @TableField(value = "hide")
    var hide: Boolean? = null

    /** 是否缓存 */
    @ApiModelProperty(value = "是否缓存 0否 1是", required = false)
    @TableField(value = "keep_alive")
    var keepAlive: Boolean? = null

    /** 外链地址 */
    @ApiModelProperty(value = "外链地址", required = false)
    @TableField(value = "href")
    var href: String? = null

    /** 内链地址 */
    @ApiModelProperty(value = "内链地址", required = false)
    @TableField(value = "frame_src")
    var frameSrc: String? = null

    /** 角色权限树选中状态 */
    @ApiModelProperty(value = "角色权限树选中状态", required = false)
    @TableField(exist = false)
    var checked: Boolean? = null

    override fun toString(): String {
        return "SysMenu(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, label=$label, parentId=$parentId, sortValue=$sortValue, name=$name, path=$path, component=$component, redirect=$redirect, icon=$icon, authority=$authority, type=$type, deleted=$deleted, hide=$hide, keepAlive=$keepAlive, href=$href, frameSrc=$frameSrc)"
    }

}
