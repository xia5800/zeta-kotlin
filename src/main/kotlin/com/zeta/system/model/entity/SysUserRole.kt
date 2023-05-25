package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.zetaframework.base.entity.SuperEntity

/**
 * 用户角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Schema(description = "用户角色")
@TableName(value = "sys_user_role")
class SysUserRole(): SuperEntity<Long>() {

    /** 用户id */
    @Schema(description = "用户id")
    @get:NotNull(message = "用户id不能为空")
    @TableField(value = "user_id")
    var userId: Long? = null

    /** 角色id */
    @Schema(description = "角色id")
    @get:NotNull(message = "角色id不能为空")
    @TableField(value = "role_id")
    var roleId: Long? = null


    constructor(userId: Long?, roleId: Long?): this() {
        this.userId = userId
        this.roleId = roleId
    }

    override fun toString(): String {
        return "SysUserRole(id=$id, createTime=$createTime, createdBy=$createdBy, userId=$userId, roleId=$roleId)"
    }

}
