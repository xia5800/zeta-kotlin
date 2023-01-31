package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.SuperEntity

/**
 * 用户角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "用户角色")
@TableName(value = "sys_user_role")
class SysUserRole(): SuperEntity<Long>() {

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    @TableField(value = "user_id")
    var userId: Long? = null

    /** 角色id */
    @ApiModelProperty(value = "角色id")
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
