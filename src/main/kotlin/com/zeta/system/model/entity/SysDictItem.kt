package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.Entity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * <p>
 * 字典项
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@ApiModel(description = "字典项")
@TableName(value = "sys_dict_item")
class SysDictItem: Entity<Long>() {

    /** 字典id */
    @ApiModelProperty(value = "字典id")
    @get:NotNull(message = "字典id不能为空")
    @TableField(value = "dict_id")
    var dictId: Long? = null

    /** 字典项 */
    @ApiModelProperty(value = "字典项")
    @get:NotBlank(message = "字典项不能为空")
    @get:Size(max = 32, message = "字典项长度不能超过32")
    @TableField(value = "name")
    var name: String? = null

    /** 值 */
    @ApiModelProperty(value = "值")
    @get:NotBlank(message = "值不能为空")
    @get:Size(max = 32, message = "值长度不能超过32")
    @TableField(value = "value")
    var value: String? = null

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
        return "SysDictItem(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, dictId=$dictId, name=$name, value=$value, describe=$describe, sortValue=$sortValue, deleted=$deleted)"
    }

}
