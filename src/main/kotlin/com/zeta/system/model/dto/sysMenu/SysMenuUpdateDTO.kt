package com.zeta.system.model.dto.sysMenu

import com.zeta.system.model.enums.MenuTypeEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * 修改 菜单
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@Schema(description = "修改菜单")
data class SysMenuUpdateDTO(

    /** id */
    @Schema(description = "id", required = true)
    @get:NotNull(message = "id不能为空")
    var id: Long? = null,

    /** 父级id */
    @Schema(description = "父级id", required = true)
    @get:NotNull(message = "父级id不能为空")
    var parentId: Long? = null,

    /** 菜单名称 */
    @Schema(description = "菜单名称", required = true)
    @get:NotBlank(message = "菜单名称不能为空")
    @get:Size(max = 32, message = "菜单名称长度不能超过32")
    var label: String? = null,

    /** 排序 */
    @Schema(description = "排序", required = false)
    var sortValue: Int? = null,

    /** 路由名称 */
    @Schema(description = "路由名称", required = true)
    var name: String? = null,

    /** 路由地址 */
    @Schema(description = "路由地址", required = false)
    var path: String? = null,

    /** 组件地址 */
    @Schema(description = "组件地址", required = false)
    var component: String? = null,

    /** 重定向地址 */
    @Schema(description = "重定向地址", required = false)
    var redirect: String? = null,

    /** 图标 */
    @Schema(description = "图标", required = false)
    var icon: String? = null,

    /** 权限标识 */
    @Schema(description = "权限标识", required = false)
    var authority: String? = null,

    /** 菜单类型 */
    @Schema(description = "菜单类型", required = true)
    @get:NotNull(message = "菜单类型不能为空")
    var type: MenuTypeEnum? = null,

    /** 是否隐藏 0否 1是 */
    @Schema(description = "是否隐藏 0否 1是", required = false)
    var hide: Boolean? = null,

    /** 是否缓存 */
    @Schema(description = "是否缓存 0否 1是", required = false)
    var keepAlive: Boolean? = null,

    /** 外链地址 */
    @Schema(description = "外链地址", required = false)
    var href: String? = null,

    /** 内链地址 */
    @Schema(description = "内链地址", required = false)
    var frameSrc: String? = null,
)
