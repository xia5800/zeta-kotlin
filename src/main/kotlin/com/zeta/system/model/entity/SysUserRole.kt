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
class SysUserRole(

    /** 用户id */
    @ApiModelProperty("用户id")
    @TableField(value = "user_id")
    var userId: Long? = null,

    /** 角色id */
    @ApiModelProperty("角色id")
    @TableField(value = "role_id")
    var roleId: Long? = null,
): SuperEntity<Long>()
