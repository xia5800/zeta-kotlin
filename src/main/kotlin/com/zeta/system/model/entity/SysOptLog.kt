package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.SuperEntity

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@ApiModel(description = "操作日志")
@TableName(value = "sys_opt_log")
class SysOptLog : SuperEntity<Long>() {

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型")
    @TableField(value = "type")
    var type: String? = null

    /** 操作描述 */
    @ApiModelProperty(value = "操作描述")
    @TableField(value = "description")
    var description: String? = null

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    @TableField(value = "url")
    var url: String? = null

    /** 请求方式 */
    @ApiModelProperty(value = "请求方式")
    @TableField(value = "http_method")
    var httpMethod: String? = null

    /** 类路径 */
    @ApiModelProperty(value = "类路径")
    @TableField(value = "class_path")
    var classPath: String? = null

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数")
    @TableField(value = "params")
    var params: String? = null

    /** 返回值 */
    @ApiModelProperty(value = "返回值")
    @TableField(value = "result")
    var result: String? = null

    /** 异常描述 */
    @ApiModelProperty(value = "异常描述")
    @TableField(value = "exception")
    var exception: String? = null

    /** 消耗时间 单位毫秒 */
    @ApiModelProperty(value = "消耗时间 单位毫秒")
    @TableField(value = "spend_time")
    var spendTime: Int? = null

    /** 操作系统 */
    @ApiModelProperty(value = "操作系统")
    @TableField(value = "os")
    var os: String? = null

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称")
    @TableField(value = "device")
    var device: String? = null

    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型")
    @TableField(value = "browser")
    var browser: String? = null

    /** ip地址 */
    @ApiModelProperty(value = "ip地址")
    @TableField(value = "ip")
    var ip: String? = null

    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField(exist = false)
    var userName: String? = null

}
