package com.zeta.system.model.dto.sysOptLog

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * 操作日志详情,数据表格用
 *
 * 说明：
 * 少了请求参数、返回值、异常信息字段。
 * 这几个字段没必要在分页查询的时候传输给前端
 *
 * @author gcc
 */
@Schema(description = "操作日志详情,数据表格用")
data class SysOptLogTableDTO(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @Schema(description = "创建人")
    var createdBy: Long? = null,

    /** 操作类型 */
    @Schema(description = "操作类型")
    var type: String? = null,

    /** 操作人 */
    @Schema(description = "操作人")
    var userName: String? = null,

    /** 操作描述 */
    @Schema(description = "操作描述")
    var description: String? = null,

    /** 请求地址 */
    @Schema(description = "请求地址")
    var url: String? = null,

    /** 请求方式 */
    @Schema(description = "请求方式")
    var httpMethod: String? = null,

    /** 类路径 */
    @Schema(description = "类路径")
    var classPath: String? = null,

    /** 消耗时间 单位毫秒 */
    @Schema(description = "消耗时间 单位毫秒")
    var spendTime: Int? = null,

    /** 操作系统 */
    @Schema(description = "操作系统")
    var os: String? = null,

    /** 设备名称 */
    @Schema(description = "设备名称")
    var device: String? = null,

    /** 浏览器类型 */
    @Schema(description = "浏览器类型")
    var browser: String? = null,

    /** ip地址 */
    @Schema(description = "ip地址")
    var ip: String? = null,

    /** ip所在地区 */
    @Schema(description = "ip所在地区")
    var ipRegion: String? = null,
)
