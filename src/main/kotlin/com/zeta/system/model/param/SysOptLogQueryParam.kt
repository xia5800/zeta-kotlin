package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 操作日志 查询参数
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@Schema(description = "操作日志查询参数")
data class SysOptLogQueryParam(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

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
