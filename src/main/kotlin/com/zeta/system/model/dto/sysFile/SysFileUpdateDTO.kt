package com.zeta.system.model.dto.sysFile

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * <p>
 * 修改 系统文件
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-12 16:47:45
 */
@ApiModel(description = "修改系统文件")
data class SysFileUpdateDTO(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 业务类型 */
    @ApiModelProperty(value = "业务类型")
    var bizType: String? = null,

    /** 桶 */
    @ApiModelProperty(value = "桶")
    @get:NotEmpty(message = "桶不能为空")
    @get:Size(max = 255, message = "桶长度不能超过255")
    var bucket: String? = null,

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型")
    @get:NotEmpty(message = "存储类型不能为空")
    @get:Size(max = 255, message = "存储类型长度不能超过255")
    var storageType: String? = null,

    /** 文件相对地址 */
    @ApiModelProperty(value = "文件相对地址")
    @get:NotEmpty(message = "文件相对地址不能为空")
    @get:Size(max = 255, message = "文件相对地址长度不能超过255")
    var path: String? = null,

    /** 文件访问地址 */
    @ApiModelProperty(value = "文件访问地址")
    var url: String? = null,

    /** 唯一文件名 */
    @ApiModelProperty(value = "唯一文件名")
    @get:NotEmpty(message = "唯一文件名不能为空")
    @get:Size(max = 255, message = "唯一文件名长度不能超过255")
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
