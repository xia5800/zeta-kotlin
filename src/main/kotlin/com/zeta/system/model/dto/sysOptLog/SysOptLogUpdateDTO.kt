package com.zeta.system.model.dto.sysOptLog

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * <p>
 * 修改 操作日志
 * </p>
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@ApiModel(description = "修改操作日志")
data class SysOptLogUpdateDTO(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型")
    var type: String? = null,

    /** 操作描述 */
    @ApiModelProperty(value = "操作描述")
    var description: String? = null,

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    var url: String? = null,

    /** 请求方式 */
    @ApiModelProperty(value = "请求方式")
    var httpMethod: String? = null,

    /** 类路径 */
    @ApiModelProperty(value = "类路径")
    var classPath: String? = null,

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数")
    var params: String? = null,

    /** 返回值 */
    @ApiModelProperty(value = "返回值")
    var result: String? = null,

    /** 异常描述 */
    @ApiModelProperty(value = "异常描述")
    var exception: String? = null,

    /** 消耗时间 单位毫秒 */
    @ApiModelProperty(value = "消耗时间 单位毫秒")
    var spendTime: Int? = null,

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
)
