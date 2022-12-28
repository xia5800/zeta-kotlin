package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel
import cn.hutool.core.date.DatePattern
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 用户Excel导出数据
 *
 * @author gcc
 */
class SysUserExportPoi {

    /** id */
    @Excel(name = "id", width = 20.0)
    var id: Long? = null

    /** 用户名 */
    @Excel(name = "用户名", width = 15.0)
    var username: String? = null

    /** 账号 */
    @Excel(name = "账号", width = 15.0)
    var account: String? = null

    /** 密码 */
    @Excel(name = "密码", desensitizationRule="3_4", width = 50.0)
    var password: String? = null

    /** 邮箱 */
    @Excel(name = "邮箱", width = 15.0)
    var email: String? = null

    /** 手机号 */
    @Excel(name = "手机号", width = 15.0)
    var mobile: String? = null

    /** 性别 */
    @Excel(name = "性别", replace = ["男_1", "女_2", "_null"], addressList = true)
    var sex: Int? = null

    /** 生日 */
    @Excel(name = "生日", format = DatePattern.NORM_DATE_PATTERN, width = 20.0)
    var birthday: LocalDate? = null

    /** 状态 */
    @Excel(name = "状态", replace = ["正常_0", "封禁_1", "_null"])
    var state: Int? = null

    /** 用户角色 */
    @Excel(name = "用户角色", width = 20.0)
    var roles: List<String>? = null

    /** 注册时间 */
    @Excel(name = "注册时间", format = DatePattern.NORM_DATETIME_PATTERN, width = 20.0)
    var createTime: LocalDateTime? = null

}
