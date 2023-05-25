package com.zeta.system.model.dto.sysDict

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * <p>
 * 字典 详情
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:38:20
 */
@Schema(description = "字典详情")
data class SysDictDTO(

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

    /** 名称 */
    @Schema(description = "名称")
    var name: String? = null,

    /** 编码 */
    @Schema(description = "编码")
    var code: String? = null,

    /** 描述 */
    @Schema(description = "描述")
    var describe: String? = null,

    /** 排序 */
    @Schema(description = "排序")
    var sortValue: Int? = null,
)
