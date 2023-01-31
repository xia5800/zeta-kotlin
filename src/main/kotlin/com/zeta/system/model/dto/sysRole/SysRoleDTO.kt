package com.zeta.system.model.dto.sysRole

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

/**
 * 角色详情
 * @author gcc
 */
@ApiModel(description = "角色详情")
data class SysRoleDTO(

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    var id: Long? = null,

    /** 角色名 */
    @ApiModelProperty(value = "角色名")
    var name: String? = null,

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码")
    var code: String? = null,

    /** 描述 */
    @ApiModelProperty(value = "描述")
    var describe: String? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人ID */
    @ApiModelProperty(value = "创建人ID")
    var createdBy: Long? = null,

    /** 最后修改时间 */
    @ApiModelProperty(value = "最后修改时间")
    var updateTime: LocalDateTime? = null,

    /** 最后修改人ID */
    @ApiModelProperty(value = "最后修改人ID")
    var updatedBy: Long? = null,

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    var userId: Long? = null,
)
