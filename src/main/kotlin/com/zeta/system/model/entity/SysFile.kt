package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zetaframework.base.entity.Entity

/**
 * 系统文件
 *
 * @author AutoGenerator
 * @date 2022-04-12 16:47:45
 */
@Schema(description = "系统文件")
@TableName(value = "sys_file")
class SysFile: Entity<Long>() {

    /** 业务类型 */
    @Schema(description = "业务类型", required = false)
    @TableField(value = "biz_type")
    var bizType: String? = null

    /** 桶 */
    @Schema(description = "桶", required = true)
    @get:NotBlank(message = "桶不能为空")
    @get:Size(max = 255, message = "桶长度不能超过255")
    @TableField(value = "bucket")
    var bucket: String? = null

    /** 存储类型 */
    @Schema(description = "存储类型", required = true)
    @get:NotBlank(message = "存储类型不能为空")
    @get:Size(max = 255, message = "存储类型长度不能超过255")
    @TableField(value = "storage_type")
    var storageType: String? = null

    /** 文件相对地址 */
    @Schema(description = "文件相对地址", required = true)
    @get:NotBlank(message = "文件相对地址不能为空")
    @get:Size(max = 255, message = "文件相对地址长度不能超过255")
    @TableField(value = "path")
    var path: String? = null

    /** 文件访问地址 */
    @Schema(description = "文件访问地址", required = false)
    @TableField(value = "url")
    var url: String? = null

    /** 唯一文件名 */
    @Schema(description = "唯一文件名", required = true)
    @get:NotBlank(message = "唯一文件名不能为空")
    @get:Size(max = 255, message = "唯一文件名长度不能超过255")
    @TableField(value = "unique_file_name")
    var uniqueFileName: String? = null

    /** 原始文件名 */
    @Schema(description = "原始文件名", required = false)
    @TableField(value = "original_file_name")
    var originalFileName: String? = null

    /** 文件类型 */
    @Schema(description = "文件类型", required = false)
    @TableField(value = "file_type")
    var fileType: String? = null

    /** 内容类型 */
    @Schema(description = "内容类型", required = false)
    @TableField(value = "content_type")
    var contentType: String? = null

    /** 后缀 */
    @Schema(description = "后缀", required = false)
    @TableField(value = "suffix")
    var suffix: String? = null

    /** 文件大小 */
    @Schema(description = "文件大小", required = false)
    @TableField(value = "size")
    var size: Long? = null


    override fun toString(): String {
        return "SysFile(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, bizType=$bizType, bucket=$bucket, storageType=$storageType, path=$path, url=$url, uniqueFileName=$uniqueFileName, originalFileName=$originalFileName, fileType=$fileType, contentType=$contentType, suffix=$suffix, size=$size)"
    }

}
