package com.zeta.system.model.dto.sysDictItem

import io.swagger.v3.oas.annotations.media.Schema

/**
 * <p>
 * 字典项 详情
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "字典项详情")
data class SysDictItemDTO(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

    /** 字典id */
    @Schema(description = "字典id")
    var dictId: Long? = null,

    /** 字典项 */
    @Schema(description = "字典项")
    var name: String? = null,

    /** 值 */
    @Schema(description = "值")
    var value: String? = null,

    /** 描述 */
    @Schema(description = "描述")
    var describe: String? = null,

    /** 排序 */
    @Schema(description = "排序")
    var sortValue: Int? = null,

    /** 字典code */
    @Schema(description = "字典code")
    var dictCode: String? = null,
)
