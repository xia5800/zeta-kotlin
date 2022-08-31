package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.Entity

/**
 * 角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "角色")
@TableName(value = "sys_role")
class SysRole: Entity<Long>() {

    /** 角色名 */
    @ApiModelProperty("角色名")
    @TableField(value = "name")
    var name: String? = null

    /** 角色编码 */
    @ApiModelProperty("角色编码")
    @TableField(value = "code")
    var code: String? = null

    /** 描述 */
    @ApiModelProperty("描述")
    @TableField(value = "describe_")
    var describe: String? = null

    /** 是否删除 true or false  */
    @ApiModelProperty("是否删除 true or false")
    @TableLogic
    var deleted: Boolean? = null

    override fun toString(): String {
        return "SysRole(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, name=$name, code=$code, describe=$describe, deleted=$deleted)"
    }

}
