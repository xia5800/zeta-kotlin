package com.zeta.system.model.dto.sysMenu

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 修改 菜单
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "修改菜单")
data class SysMenuUpdateDTO(

    /** id */
    @ApiModelProperty(value = "菜单id")
    @get:NotNull(message = "菜单id不能为空")
    var id: Long? = null,

    /** 名称 */
    @ApiModelProperty(value = "名称")
    @get:NotBlank(message = "名称不能为空")
    var label: String? = null,

    /** 父级Id */
    @ApiModelProperty(value = "父级Id")
    @get:NotNull(message = "上级菜单不能为空")
    var parentId: Long? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序")
    @get:NotNull(message = "排序不能为空")
    var sortValue: Int? = null,

    /** 菜单路由地址 */
    @ApiModelProperty("菜单路由地址")
    var path: String? = null,

    /** 组件路由地址 */
    @ApiModelProperty("组件路由地址")
    var component: String? = null,

    /** 图标 */
    @ApiModelProperty("图标")
    var icon: String? = null,

    /** 图标颜色 */
    @ApiModelProperty("图标颜色")
    var color: String? = null,

    /** 权限标识 */
    @ApiModelProperty("权限标识")
    var authority: String? = null,

    /** 菜单类型 */
    @ApiModelProperty(value = "菜单类型", required = true)
    @get:NotBlank(message = "菜单类型不能为空")
    var type: String? = null,
)
