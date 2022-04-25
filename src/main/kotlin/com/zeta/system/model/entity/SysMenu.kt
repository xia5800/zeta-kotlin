package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.TreeEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@ApiModel(description = "菜单")
@TableName(value = "sys_menu")
class SysMenu: TreeEntity<SysMenu, Long>() {

    /** 路由key */
    @ApiModelProperty(value = "路由key")
    @TableField(value = "name")
    var name: String? = null

    /** 菜单路由地址 */
    @ApiModelProperty(value = "菜单路由地址")
    @TableField(value = "path")
    var path: String? = null

    /** 组件 */
    @ApiModelProperty(value = "组件")
    @TableField(value = "component")
    var component: String? = null

    /** 重定向地址 */
    @ApiModelProperty(value = "重定向地址")
    @TableField(value = "redirect")
    var redirect: String? = null

    /** 图标 */
    @ApiModelProperty(value = "图标")
    @TableField(value = "icon")
    var icon: String? = null

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识")
    @TableField(value = "authority")
    var authority: String? = null

    /** 菜单类型 */
    @ApiModelProperty(value = "菜单类型")
    @get:NotBlank(message = "菜单类型不能为空")
    @get:Size(max = 32, message = "菜单类型长度不能超过32")
    @TableField(value = "type")
    var type: String? = null

    /** 逻辑删除字段 */
    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic
    var deleted: Boolean? = null

    /** 是否隐藏 0否 1是 */
    @ApiModelProperty(value = "是否隐藏 0否 1是")
    @TableField(value = "hide")
    var hide: Boolean? = null

    /** 是否缓存 */
    @ApiModelProperty(value = "是否缓存 0否 1是")
    @TableField(value = "keep_alive")
    var keepAlive: Boolean? = null

    /** 外链地址 */
    @ApiModelProperty(value = "外链地址")
    @TableField(value = "href")
    var href: String? = null

    /** 角色权限树选中状态 */
    @ApiModelProperty("角色权限树选中状态")
    @TableField(exist = false)
    var checked: Boolean? = null

    override fun toString(): String {
        return "SysMenu(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, label=$label, parentId=$parentId, sortValue=$sortValue, name=$name, path=$path, component=$component, redirect=$redirect, icon=$icon, authority=$authority, type=$type, deleted=$deleted, hide=$hide, keepAlive=$keepAlive, href=$href)"
    }

}
