package org.zetaframework.core.log.model

import org.zetaframework.core.log.enums.LogTypeEnum

/**
 * 系统日志
 *
 * @author gcc
 */
data class SysLogDTO(

    /**  日志类型 see: [LogTypeEnum] */
    var type: String? = null,

    /** 操作描述 */
    var description: String? = null,

    /** 请求地址 */
    var url: String? = null,

    /** 请求方式 */
    var httpMethod: String? = null,

    /** 类路径 */
    var classPath: String? = null,

    /** 请求参数 */
    var params: String? = null,

    /** 返回值 */
    var result: String? = null,

    /** 异常描述 */
    var exception: String? = null,

    /** 操作耗时 */
    var spendTime: Long? = null,

    /** 操作系统 */
    var os: String? = null,

    /** 设备名称 */
    var device: String? = null,

    /** 浏览器类型 */
    var browser: String? = null,

    /** ip地址 */
    var ip: String? = null,
)
