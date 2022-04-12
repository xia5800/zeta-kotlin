package com.zeta.system.model.dto.sysFile

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.*

/**
 * <p>
 * 系统文件 详情
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-11 11:18:44
 */
@ApiModel(description = "系统文件详情")
data class SysFileDTO(

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

    /** 业务类型 */
    @ApiModelProperty(value = "业务类型")
    var bizType: String? = null,

    /** 桶 */
    @ApiModelProperty(value = "桶")
    var bucket: String? = null,

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型")
    var storageType: String? = null,

    /** 文件相对地址 */
    @ApiModelProperty(value = "文件相对地址")
    var path: String? = null,

    /** 文件访问地址 */
    @ApiModelProperty(value = "文件访问地址")
    var url: String? = null,

    /** 唯一文件名 */
    @ApiModelProperty(value = "唯一文件名")
    var uniqueFileName: String? = null,

    /** 原始文件名 */
    @ApiModelProperty(value = "原始文件名")
    var originalFileName: String? = null,

    /** 文件类型 */
    @ApiModelProperty(value = "文件类型")
    var fileType: String? = null,

    /** 内容类型 */
    @ApiModelProperty(value = "内容类型")
    var contentType: String? = null,

    /** 后缀 */
    @ApiModelProperty(value = "后缀")
    var suffix: String? = null,

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小")
    var size: Long? = null,
)
