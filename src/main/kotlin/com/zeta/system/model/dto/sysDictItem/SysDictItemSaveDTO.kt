package com.zeta.system.model.dto.sysDictItem

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * 新增 字典项
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@ApiModel(description = "新增字典项")
data class SysDictItemSaveDTO(

    /** 字典id */
    @ApiModelProperty(value = "字典id", required = true)
    @get:NotNull(message = "字典id不能为空")
    var dictId: Long? = null,

    /** 字典项 */
    @ApiModelProperty(value = "字典项", required = true)
    @get:NotEmpty(message = "字典项不能为空")
    @get:Size(max = 32, message = "字典项长度不能超过32")
    var name: String? = null,

    /** 值 */
    @ApiModelProperty(value = "值", required = true)
    @get:NotEmpty(message = "值不能为空")
    @get:Size(max = 32, message = "值长度不能超过32")
    var value: String? = null,

    /** 描述 */
    @ApiModelProperty(value = "描述", required = false)
    var describe: String? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序", required = false)
    var sortValue: Int? = null,

)
