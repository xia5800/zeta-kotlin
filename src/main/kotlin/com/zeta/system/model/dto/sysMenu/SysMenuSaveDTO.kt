package com.zeta.system.model.dto.sysMenu

import com.zeta.system.model.enums.MenuTypeEnum
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * <p>
 * 新增 菜单
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@ApiModel(description = "新增菜单")
data class SysMenuSaveDTO(

    /** 父级id */
    @ApiModelProperty(value = "父级id", required = true)
    @get:NotNull(message = "父级id不能为空")
    var parentId: Long? = null,

    /** 名称 */
    @ApiModelProperty(value = "名称", required = true)
    @get:NotBlank(message = "名称不能为空")
    @get:Size(max = 32, message = "名称长度不能超过32")
    var label: String? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序", required = false)
    var sortValue: Int? = null,

    /** 路由key */
    @ApiModelProperty(value = "路由key", required = false)
    var name: String? = null,

    /** 菜单路由地址 */
    @ApiModelProperty(value = "菜单路由地址", required = false)
    var path: String? = null,

    /** 组件 */
    @ApiModelProperty(value = "组件", required = false)
    var component: String? = null,

    /** 重定向地址 */
    @ApiModelProperty(value = "重定向地址", required = false)
    var redirect: String? = null,

    /** 图标 */
    @ApiModelProperty(value = "图标", required = false)
    var icon: String? = null,

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识", required = false)
    var authority: String? = null,

    /** 菜单类型 */
    @ApiModelProperty(value = "菜单类型", required = true)
    @get:NotNull(message = "菜单类型不能为空")
    var type: MenuTypeEnum? = null,

    /** 是否隐藏 0否 1是 */
    @ApiModelProperty(value = "是否隐藏 0否 1是", required = false)
    var hide: Boolean? = null,

    /** 是否缓存 */
    @ApiModelProperty(value = "是否缓存 0否 1是", required = false)
    var keepAlive: Boolean? = null,

    /** 外链地址 */
    @ApiModelProperty(value = "外链地址", required = false)
    var href: String? = null,
)
