package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 菜单 查询参数
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "菜单查询参数")
data class SysMenuQueryParam (

    /** id */
    @ApiModelProperty(value = "菜单id")
    var id: Long? = null,

    /** 名称 */
    @ApiModelProperty(value = "名称")
    var label: String? = null,

    /** 父级Id */
    @ApiModelProperty(value = "父级Id")
    var parentId: Long? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序")
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

    /** 是否隐藏 */
    @ApiModelProperty("是否隐藏")
    var hide: Boolean? = null,

    /** 权限标识 */
    @ApiModelProperty("权限标识")
    var authority: String? = null,

    /** 菜单类型 */
    @ApiModelProperty("菜单类型")
    var type: String? = null,
)
