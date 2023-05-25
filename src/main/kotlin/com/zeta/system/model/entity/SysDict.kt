package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zetaframework.base.entity.Entity

/**
 * <p>
 * 字典
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "字典")
@TableName(value = "sys_dict")
class SysDict: Entity<Long>() {

    /** 名称 */
    @Schema(description = "名称", required = true)
    @get:NotBlank(message = "名称不能为空")
    @get:Size(max = 32, message = "名称长度不能超过32")
    @TableField(value = "name")
    var name: String? = null

    /** 编码 */
    @Schema(description = "编码", required = true)
    @get:NotBlank(message = "编码不能为空")
    @get:Size(max = 32, message = "编码长度不能超过32")
    @TableField(value = "code")
    var code: String? = null

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
        return "SysDict(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, name=$name, code=$code, describe=$describe, sortValue=$sortValue, deleted=$deleted)"
    }

}
