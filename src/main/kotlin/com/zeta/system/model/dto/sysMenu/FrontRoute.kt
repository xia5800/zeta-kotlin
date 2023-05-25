package com.zeta.system.model.dto.sysMenu

import com.fasterxml.jackson.annotation.JsonInclude
import com.zeta.system.model.entity.SysMenu
import io.swagger.v3.oas.annotations.media.Schema
import org.zetaframework.base.entity.TreeEntity

/**
 * 前端路由
 *
 * 说明：
 * 内含转换方法，将系统菜单转换成前端路由
 * @author gcc
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "前端路由")
class FrontRoute: TreeEntity<FrontRoute, Long>() {

    /** id */
    @Schema(description = "路由id")
    override var id: Long? = null

    /** 子节点 */
    @Schema(description = "子路由")
    override var children: MutableList<FrontRoute>? = null

    /** 路由名称 */
    @Schema(description = "路由名称")
    var name: String? = null

    /** 路由地址 */
    @Schema(description = "路由地址")
    var path: String? = null

    /** 组件地址 */
    @Schema(description = "组件地址")
    var component: String? = null

    /** 重定向地址 */
    @Schema(description = "重定向地址")
    var redirect: String? = null

    /** 路由元数据 */
    @Schema(description = "路由元数据")
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
                this.frameSrc = sysMenu.frameSrc
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
@Schema(description = "路由描述")
data class RouteMeta(
    /** 路由标题 */
    @Schema(description = "路由标题")
    var title: String? = null,

    /** 菜单和面包屑对应的图标 */
    @Schema(description = "菜单和面包屑对应的图标")
    var icon: String? = null,

    /** 是否在菜单中隐藏 */
    @Schema(description = "是否在菜单中隐藏")
    var hide: Boolean? = null,

    /** 是否缓存 */
    @Schema(description = "是否缓存")
    var keepAlive: Boolean? = null,

    /** 外链地址 */
    @Schema(description = "外链链接")
    var href: String? = null,

    /** 内链地址 */
    @Schema(description = "内链地址")
    var frameSrc: String? = null,
)
