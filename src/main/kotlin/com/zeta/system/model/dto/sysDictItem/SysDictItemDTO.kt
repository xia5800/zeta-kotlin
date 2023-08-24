package com.zeta.system.model.dto.sysDictItem

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 字典项 详情
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@ApiModel(description = "字典项详情")
data class SysDictItemDTO(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 字典id */
    @ApiModelProperty(value = "字典id")
    var dictId: Long? = null,

    /** 字典项 */
    @ApiModelProperty(value = "字典项")
    var name: String? = null,

    /** 值 */
    @ApiModelProperty(value = "值")
    var value: String? = null,

    /** 描述 */
    @ApiModelProperty(value = "描述")
    var describe: String? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序")
    var sortValue: Int? = null,

    /** 字典code */
    @ApiModelProperty(value = "字典code")
    var dictCode: String? = null,
)
