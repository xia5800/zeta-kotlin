package com.zeta.system.model.dto.sysRole

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * 角色详情
 * @author gcc
 */
@Schema(description = "角色详情")
data class SysRoleDTO(

    /** 角色id */
    @Schema(description = "角色id")
    var id: Long? = null,

    /** 角色名 */
    @Schema(description = "角色名")
    var name: String? = null,

    /** 角色编码 */
    @Schema(description = "角色编码")
    var code: String? = null,

    /** 描述 */
    @Schema(description = "描述")
    var describe: String? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人ID */
    @Schema(description = "创建人ID")
    var createdBy: Long? = null,

    /** 最后修改时间 */
    @Schema(description = "最后修改时间")
    var updateTime: LocalDateTime? = null,

    /** 最后修改人ID */
    @Schema(description = "最后修改人ID")
    var updatedBy: Long? = null,

    /** 用户id */
    @Schema(description = "用户id")
    var userId: Long? = null,
)
