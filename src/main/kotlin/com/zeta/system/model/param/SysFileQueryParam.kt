package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * <p>
 * 系统文件 查询参数
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-11 11:18:44
 */
@Schema(description = "系统文件查询参数")
data class SysFileQueryParam(

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

    /** 业务类型 */
    @Schema(description = "业务类型")
    var bizType: String? = null,

    /** 桶 */
    @Schema(description = "桶")
    var bucket: String? = null,

    /** 存储类型 */
    @Schema(description = "存储类型")
    var storageType: String? = null,

    /** 文件相对地址 */
    @Schema(description = "文件相对地址")
    var path: String? = null,

    /** 文件访问地址 */
    @Schema(description = "文件访问地址")
    var url: String? = null,

    /** 唯一文件名 */
    @Schema(description = "唯一文件名")
    var uniqueFileName: String? = null,

    /** 原始文件名 */
    @Schema(description = "原始文件名")
    var originalFileName: String? = null,

    /** 文件类型 */
    @Schema(description = "文件类型")
    var fileType: String? = null,

    /** 内容类型 */
    @Schema(description = "内容类型")
    var contentType: String? = null,

    /** 后缀 */
    @Schema(description = "后缀")
    var suffix: String? = null,

    /** 文件大小 */
    @Schema(description = "文件大小")
    var size: Long? = null,
)
