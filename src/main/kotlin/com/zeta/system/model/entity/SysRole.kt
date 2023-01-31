package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.Entity
import javax.validation.constraints.NotBlank

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
    @ApiModelProperty(value = "角色名", required = true)
    @get:NotBlank(message = "角色名不能为空")
    @TableField(value = "name")
    var name: String? = null

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码", required = true)
    @get:NotBlank(message = "角色编码不能为空")
    @TableField(value = "code")
    var code: String? = null

    /** 描述 */
    @ApiModelProperty(value = "描述", required = false)
    @TableField(value = "describe_")
    var describe: String? = null

    /** 是否内置 0否 1是 */
    @ApiModelProperty(value = "是否内置 0否 1是", required = false)
    @TableField(value = "readonly_")
    var readonly: Boolean? = null

    /** 逻辑删除字段 */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除字段", hidden = true)
    @TableLogic
    var deleted: Boolean? = null

    override fun toString(): String {
        return "SysRole(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, name=$name, code=$code, describe=$describe, readonly=$readonly, deleted=$deleted)"
    }

}
