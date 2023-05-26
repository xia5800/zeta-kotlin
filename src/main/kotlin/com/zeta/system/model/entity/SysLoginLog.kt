package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zetaframework.base.entity.SuperEntity

/**
 * 登录日志
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@Schema(description = "登录日志")
@TableName(value = "sys_login_log")
class SysLoginLog: SuperEntity<Long>() {

    /** 状态 */
    @Schema(description = "状态", required = true)
    @get:NotBlank(message = "状态不能为空")
    @get:Size(max = 10, message = "状态长度不能超过10")
    @TableField(value = "state")
    var state: String? = null

    /** 账号 */
    @Schema(description = "账号", required = true)
    @get:NotBlank(message = "账号不能为空")
    @get:Size(max = 64, message = "账号长度不能超过64")
    @TableField(value = "account")
    var account: String? = null

    /** 备注 */
    @Schema(description = "备注", required = false)
    @TableField(value = "comments")
    var comments: String? = null

    /** 操作系统 */
    @Schema(description = "操作系统", required = false)
    @TableField(value = "os")
    var os: String? = null

    /** 设备名称 */
    @Schema(description = "设备名称", required = false)
    @TableField(value = "device")
    var device: String? = null

    /** 浏览器类型 */
    @Schema(description = "浏览器类型", required = false)
    @TableField(value = "browser")
    var browser: String? = null

    /** ip地址 */
    @Schema(description = "ip地址", required = false)
    @TableField(value = "ip")
    var ip: String? = null

    /** ip所在地区 */
    @Schema(description = "ip所在地区", required = false)
    @TableField(value = "ip_region")
    var ipRegion: String? = null

    override fun toString(): String {
        return "SysLoginLog(id=$id, createTime=$createTime, createdBy=$createdBy, state=$state, account=$account, comments=$comments, os=$os, device=$device, browser=$browser, ip=$ip, ipRegion=$ipRegion)"
    }

}
