package com.zeta.system.model.dto.sysDictItem

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * <p>
 * 新增 字典项
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "新增字典项")
data class SysDictItemSaveDTO(

    /** 字典id */
    @Schema(description = "字典id", required = true)
    @get:NotNull(message = "字典id不能为空")
    var dictId: Long? = null,

    /** 字典项 */
    @Schema(description = "字典项", required = true)
    @get:NotEmpty(message = "字典项不能为空")
    @get:Size(max = 32, message = "字典项长度不能超过32")
    var name: String? = null,

    /** 值 */
    @Schema(description = "值", required = true)
    @get:NotEmpty(message = "值不能为空")
    @get:Size(max = 32, message = "值长度不能超过32")
    var value: String? = null,

    /** 描述 */
    @Schema(description = "描述", required = false)
    var describe: String? = null,

    /** 排序 */
    @Schema(description = "排序", required = false)
    var sortValue: Int? = null,

)
