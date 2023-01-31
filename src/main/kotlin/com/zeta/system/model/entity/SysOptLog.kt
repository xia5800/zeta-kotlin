package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.SuperEntity
import javax.validation.constraints.NotBlank

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
    @ApiModelProperty(value = "操作类型", required = true)
    @TableField(value = "type")
    var type: String? = null

    /** 操作描述 */
    @ApiModelProperty(value = "操作描述", required = true)
    @TableField(value = "description")
    var description: String? = null

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址", required = true)
    @get:NotBlank(message = "请求地址不能为空")
    @TableField(value = "url")
    var url: String? = null

    /** 请求方式 */
    @ApiModelProperty(value = "请求方式", required = true)
    @get:NotBlank(message = "请求方式不能为空")
    @TableField(value = "http_method")
    var httpMethod: String? = null

    /** 类路径 */
    @ApiModelProperty(value = "类路径", required = true)
    @get:NotBlank(message = "类路径不能为空")
    @TableField(value = "class_path")
    var classPath: String? = null

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数", required = false)
    @TableField(value = "params")
    var params: String? = null

    /** 返回值 */
    @ApiModelProperty(value = "返回值", required = false)
    @TableField(value = "result")
    var result: String? = null

    /** 异常描述 */
    @ApiModelProperty(value = "异常描述", required = false)
    @TableField(value = "exception")
    var exception: String? = null

    /** 消耗时间 单位毫秒 */
    @ApiModelProperty(value = "消耗时间 单位毫秒", required = true)
    @get:NotBlank(message = "消耗时间不能为空")
    @TableField(value = "spend_time")
    var spendTime: Int? = null

    /** 操作系统 */
    @ApiModelProperty(value = "操作系统", required = false)
    @TableField(value = "os")
    var os: String? = null

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称", required = false)
    @TableField(value = "device")
    var device: String? = null

    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型", required = false)
    @TableField(value = "browser")
    var browser: String? = null

    /** ip地址 */
    @ApiModelProperty(value = "ip地址", required = false)
    @TableField(value = "ip")
    var ip: String? = null

    /** ip所在地区 */
    @ApiModelProperty(value = "ip所在地区", required = false)
    @TableField(value = "ip_region")
    var ipRegion: String? = null

    /** 操作人 */
    @ApiModelProperty(value = "操作人", required = false)
    @TableField(exist = false)
    var userName: String? = null

    override fun toString(): String {
        return "SysOptLog(id=$id, createTime=$createTime, createdBy=$createdBy, type=$type, description=$description, url=$url, httpMethod=$httpMethod, classPath=$classPath, params=$params, result=$result, exception=$exception, spendTime=$spendTime, os=$os, device=$device, browser=$browser, ip=$ip, ipRegion=$ipRegion)"
    }

}
