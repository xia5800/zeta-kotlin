package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * <p>
 * 字典项 查询参数
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "字典项查询参数")
data class SysDictItemQueryParam(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @Schema(description = "创建人")
    var createdBy: Long? = null,

    /** 修改时间 */
    @Schema(description = "修改时间")
    var updateTime: LocalDateTime? = null,

    /** 修改人 */
    @Schema(description = "修改人")
    var updatedBy: Long? = null,

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

)
