package com.zeta.system.model.dto.sysOptLog

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
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
@ApiModel(description = "操作日志详情,数据表格用")
data class SysOptLogTableDTO(

    /** id */
    @ApiModelProperty(value = "id")
    var id: Long? = null,

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    var createTime: LocalDateTime? = null,

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    var createdBy: Long? = null,

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型")
    var type: String? = null,

    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    var userName: String? = null,

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

    /** ip所在地区 */
    @ApiModelProperty(value = "ip所在地区")
    var ipRegion: String? = null,
)
