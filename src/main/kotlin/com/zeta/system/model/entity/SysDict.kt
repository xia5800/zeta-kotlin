package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.Entity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * <p>
 * 字典
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@ApiModel(description = "字典")
@TableName(value = "sys_dict")
class SysDict: Entity<Long>() {

    /** 名称 */
    @ApiModelProperty(value = "名称")
    @get:NotBlank(message = "名称不能为空")
    @get:Size(max = 32, message = "名称长度不能超过32")
    @TableField(value = "name")
    var name: String? = null

    /** 编码 */
    @ApiModelProperty(value = "编码")
    @get:NotBlank(message = "编码不能为空")
    @get:Size(max = 32, message = "编码长度不能超过32")
    @TableField(value = "code")
    var code: String? = null

    /** 描述 */
    @ApiModelProperty(value = "描述")
    @TableField(value = "describe_")
    var describe: String? = null

    /** 排序 */
    @ApiModelProperty(value = "排序")
    @TableField(value = "sort_value")
    var sortValue: Int? = null

    /** 逻辑删除字段 */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic
    var deleted: Boolean? = null

    override fun toString(): String {
        return "SysDict(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, name=$name, code=$code, describe=$describe, sortValue=$sortValue, deleted=$deleted)"
    }

}
