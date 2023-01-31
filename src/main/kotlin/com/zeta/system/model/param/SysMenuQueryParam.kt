package com.zeta.system.model.param

import com.zeta.system.model.enums.MenuTypeEnum
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

/**
 * <p>
 * 菜单 查询参数
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-24 17:45:03
 */
@ApiModel(description = "菜单查询参数")
data class SysMenuQueryParam(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    var createdBy: Long? = null,

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间")
    var updateTime: LocalDateTime? = null,

    /** 修改人 */
    @ApiModelProperty(value = "修改人")
    var updatedBy: Long? = null,

    /** 名称 */
    @ApiModelProperty(value = "名称")
    var label: String? = null,

    /** 父级id */
    @ApiModelProperty(value = "父级id")
    var parentId: Long? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序")
    var sortValue: Int? = null,

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
    var type: MenuTypeEnum? = null,

    /** 是否隐藏 0否 1是 */
    @ApiModelProperty(value = "是否隐藏 0否 1是")
    var hide: Boolean? = null,

    /** 是否缓存 */
    @ApiModelProperty(value = "是否缓存 0否 1是")
    var keepAlive: Boolean? = null,

    /** 外链地址 */
    @ApiModelProperty(value = "外链地址")
    var href: String? = null,
)
