package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

/**
 * <p>
 * 字典项 查询参数
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@ApiModel(description = "字典项查询参数")
data class SysDictItemQueryParam(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    var createdBy: Long? = null,

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间")
    var updateTime: LocalDateTime? = null,

    /** 修改人 */
    @ApiModelProperty(value = "修改人")
    var updatedBy: Long? = null,

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

)
