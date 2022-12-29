package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

/**
 * <p>
 * 登录日志 查询参数
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@ApiModel(description = "登录日志查询参数")
data class SysLoginLogQueryParam(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    var createdBy: Long? = null,

    /** 状态 */
    @ApiModelProperty(value = "状态")
    var state: String? = null,

    /** 账号 */
    @ApiModelProperty(value = "账号")
    var account: String? = null,

    /** 备注 */
    @ApiModelProperty(value = "备注")
    var comments: String? = null,

    /** 操作系统 */
    @ApiModelProperty(value = "操作系统")
    var os: String? = null,

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称")
    var device: String? = null,

    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型")
    var browser: String? = null,

    /** ip地址 */
    @ApiModelProperty(value = "ip地址")
    var ip: String? = null,

    /** ip所在地区 */
    @ApiModelProperty(value = "ip所在地区")
    var ipRegion: String? = null,
)
