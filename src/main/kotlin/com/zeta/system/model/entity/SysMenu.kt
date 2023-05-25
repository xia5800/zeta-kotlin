package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.zeta.system.model.enums.MenuTypeEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.zetaframework.base.entity.TreeEntity

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@Schema(description = "菜单")
@TableName(value = "sys_menu")
class SysMenu: TreeEntity<SysMenu, Long>() {

    /** 路由名称 */
    @Schema(description = "路由名称", required = true)
    @TableField(value = "name")
    var name: String? = null

    /** 路由地址 */
    @Schema(description = "路由地址", required = false)
    @TableField(value = "path")
    var path: String? = null

    /** 组件地址 */
    @Schema(description = "组件地址", required = false)
    @TableField(value = "component")
    var component: String? = null

    /** 重定向地址 */
    @Schema(description = "重定向地址", required = false)
    @TableField(value = "redirect")
    var redirect: String? = null

    /** 图标 */
    @Schema(description = "图标", required = false)
    @TableField(value = "icon")
    var icon: String? = null

    /** 权限标识 */
    @Schema(description = "权限标识", required = false)
    @TableField(value = "authority")
    var authority: String? = null

    /** 菜单类型 */
    @Schema(description = "菜单类型", required = true)
    @get:NotNull(message = "菜单类型不能为空")
    @TableField(value = "type")
    var type: MenuTypeEnum? = null

    /** 逻辑删除字段 */
    @Schema(description = "逻辑删除字段", hidden = true, required = true)
    @TableLogic
    var deleted: Boolean? = null

    /** 是否隐藏 0否 1是 */
    @Schema(description = "是否隐藏 0否 1是", required = false)
    @TableField(value = "hide")
    var hide: Boolean? = null

    /** 是否缓存 */
    @Schema(description = "是否缓存 0否 1是", required = false)
    @TableField(value = "keep_alive")
    var keepAlive: Boolean? = null

    /** 外链地址 */
    @Schema(description = "外链地址", required = false)
    @TableField(value = "href")
    var href: String? = null

    /** 内链地址 */
    @Schema(description = "内链地址", required = false)
    @TableField(value = "frame_src")
    var frameSrc: String? = null

    /** 角色权限树选中状态 */
    @Schema(description = "角色权限树选中状态", required = false)
    @TableField(exist = false)
    var checked: Boolean? = null

    override fun toString(): String {
        return "SysMenu(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, label=$label, parentId=$parentId, sortValue=$sortValue, name=$name, path=$path, component=$component, redirect=$redirect, icon=$icon, authority=$authority, type=$type, deleted=$deleted, hide=$hide, keepAlive=$keepAlive, href=$href, frameSrc=$frameSrc)"
    }

}
