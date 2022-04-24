package com.zeta.system.model.dto.sysMenu

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
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

    /** 路由key */
    @ApiModelProperty(value = "路由key")
    var name: String? = null,

    /** 菜单路由地址 */
    @ApiModelProperty(value = "菜单路由地址")
    var path: String? = null,

    /** 组件 */
    @ApiModelProperty(value = "组件")
    var component: String? = null,

    /** 重定向地址 */
    @ApiModelProperty(value = "重定向地址")
    var redirect: String? = null,

    /** 图标 */
    @ApiModelProperty(value = "图标")
    var icon: String? = null,

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识")
    var authority: String? = null,

    /** 菜单类型 */
    @ApiModelProperty(value = "菜单类型")
    @get:NotEmpty(message = "菜单类型不能为空")
    @get:Size(max = 32, message = "菜单类型长度不能超过32")
    var type: String? = null,


    /** 是否隐藏 0否 1是 */
    @ApiModelProperty(value = "是否隐藏 0否 1是")
    var hide: Boolean? = null,

    /** 外链地址 */
    @ApiModelProperty(value = "外链地址")
    var href: String? = null,
)
