package com.zeta.system.model.dto.sysLoginLog

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * <p>
 * 新增 登录日志
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@ApiModel(description = "新增登录日志")
data class SysLoginLogSaveDTO(

    /** 状态 */
    @ApiModelProperty(value = "状态")
    @get:NotEmpty(message = "状态不能为空")
    @get:Size(max = 10, message = "状态长度不能超过10")
    var state: String? = null,

    /** 账号 */
    @ApiModelProperty(value = "账号")
    @get:NotEmpty(message = "账号不能为空")
    @get:Size(max = 64, message = "账号长度不能超过64")
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
