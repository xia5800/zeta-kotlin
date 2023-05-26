package com.zeta.system.model.param

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * 登录日志 查询参数
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@Schema(description = "登录日志查询参数")
data class SysLoginLogQueryParam(

    /** id */
    @Schema(description = "id")
    var id: Long? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @Schema(description = "创建人")
    var createdBy: Long? = null,

    /** 状态 */
    @Schema(description = "状态")
    var state: String? = null,

    /** 账号 */
    @Schema(description = "账号")
    var account: String? = null,

    /** 备注 */
    @Schema(description = "备注")
    var comments: String? = null,

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
