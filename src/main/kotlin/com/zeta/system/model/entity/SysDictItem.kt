package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.zetaframework.base.entity.Entity

/**
 * <p>
 * 字典项
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "字典项")
@TableName(value = "sys_dict_item")
class SysDictItem: Entity<Long>() {

    /** 字典id */
    @Schema(description = "字典id", required = true)
    @get:NotNull(message = "字典id不能为空")
    @TableField(value = "dict_id")
    var dictId: Long? = null

    /** 字典项 */
    @Schema(description = "字典项", required = true)
    @get:NotBlank(message = "字典项不能为空")
    @get:Size(max = 32, message = "字典项长度不能超过32")
    @TableField(value = "name")
    var name: String? = null

    /** 值 */
    @Schema(description = "值", required = true)
    @get:NotBlank(message = "值不能为空")
    @get:Size(max = 32, message = "值长度不能超过32")
    @TableField(value = "value")
    var value: String? = null

    /** 描述 */
    @Schema(description = "描述", required = false)
    @TableField(value = "describe_")
    var describe: String? = null

    /** 排序 */
    @Schema(description = "排序", required = false)
    @TableField(value = "sort_value")
    var sortValue: Int? = null

    /** 逻辑删除字段 */
    @JsonIgnore
    @Schema(description = "逻辑删除字段", hidden = true, required = true)
    @TableLogic
    var deleted: Boolean? = null


    override fun toString(): String {
        return "SysDictItem(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, dictId=$dictId, name=$name, value=$value, describe=$describe, sortValue=$sortValue, deleted=$deleted)"
    }

}
