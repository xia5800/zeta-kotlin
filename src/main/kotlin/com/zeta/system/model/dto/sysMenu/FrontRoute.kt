package com.zeta.system.model.dto.sysMenu

import com.fasterxml.jackson.annotation.JsonInclude
import com.zeta.system.model.entity.SysMenu
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.TreeEntity

/**
 * 前端路由
 *
 * 说明：
 * 内含转换方法，将系统菜单转换成前端路由
 * @author gcc
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "前端路由")
class FrontRoute: TreeEntity<FrontRoute, Long>() {

    /** id */
    @ApiModelProperty(value = "路由id")
    override var id: Long? = null

    /** 子节点 */
    @ApiModelProperty(value = "子路由")
    override var children: MutableList<FrontRoute>? = null

    /** 路由名称 */
    @ApiModelProperty(value = "路由名称")
    var name: String? = null

    /** 路由路径 */
    @ApiModelProperty(value = "路由路径")
    var path: String? = null

    /** 路由组件 */
    @ApiModelProperty(value = "路由组件")
    var component: String? = null

    /** 重定向地址 */
    @ApiModelProperty(value = "重定向地址")
    var redirect: String? = null

    /** 路由描述 */
    @ApiModelProperty(value = "路由描述")
    var meta: RouteMeta? = null

    companion object {
        /**
         * 将菜单转换成前端路由
         * @param sysMenu
         */
        fun convert(sysMenu: SysMenu): FrontRoute {
            val meta = RouteMeta().apply {
                this.title = sysMenu.label
                this.icon = sysMenu.icon
                this.hide = sysMenu.hide
                this.keepAlive = sysMenu.keepAlive
                this.href = sysMenu.href
                this.order = sysMenu.sortValue
            }

            return FrontRoute().apply {
                this.id = sysMenu.id
                this.name = sysMenu.name
                this.path = sysMenu.path
                this.component = sysMenu.component
                this.redirect = sysMenu.redirect
                this.parentId = sysMenu.parentId
                this.meta = meta
            }
        }
    }
}

/**
 * 路由描述
 * @author gcc
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "路由描述")
data class RouteMeta(
    /** 路由标题 */
    @ApiModelProperty(value = "路由标题")
    var title: String? = null,

    /** 该路由需要登录才能访问 */
    @ApiModelProperty(value = "需要身份验证")
    var requiresAuth: Boolean = true,

    /**
     * 角色编码列表
     *
     * 说明：
     * Soybean Admin的前端菜单方案是获取系统所有的菜单，每个菜单都能让哪些角色能够访问就是通过这个字段来进行判断的。
     * 但是zeta-kotlin项目不同，本项目用的是查询用户可以访问的菜单，返回给前端。所以这个字段不需要设置值
     */
    @ApiModelProperty(value = "角色编码列表")
    var permissions: MutableList<String> = mutableListOf(),

    /** 菜单和面包屑对应的图标 */
    @ApiModelProperty(value = "菜单和面包屑对应的图标")
    var icon: String? = null,

    /** 是否在菜单中隐藏 */
    @ApiModelProperty(value = "是否在菜单中隐藏")
    var hide: Boolean? = null,

    /** 是否缓存 */
    @ApiModelProperty(value = "是否缓存")
    var keepAlive: Boolean? = null,

    /** 外链链接 */
    @ApiModelProperty(value = "外链链接")
    var href: String? = null,

    /** 路由顺序，可用于菜单的排序 */
    @ApiModelProperty(value = "路由顺序")
    var order: Int? = null,
)
