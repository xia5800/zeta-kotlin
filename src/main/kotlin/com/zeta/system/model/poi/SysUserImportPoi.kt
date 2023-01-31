package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel
import cn.hutool.core.date.DatePattern
import org.zetaframework.base.entity.ImportPoi
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * 用户Excel导入数据
 *
 * @author gcc
 */
class SysUserImportPoi: ImportPoi() {

    /** 用户名 */
    @Excel(name = "用户名", width = 15.0)
    @NotBlank(message = "不能为空")
    var username: String? = null

    /** 账号 */
    @Excel(name = "账号", width = 15.0)
    @NotBlank(message = "不能为空")
    var account: String? = null

    /** 密码 */
    @Excel(name = "密码", width = 50.0)
    @NotBlank(message = "不能为空")
    var password: String? = null

    /** 邮箱 */
    @Excel(name = "邮箱", width = 15.0)
    var email: String? = null

    /** 手机号 */
    @Excel(name = "手机号", width = 15.0)
    @Size(max = 11, message = "长度不能超过11")
    var mobile: String? = null

    /** 性别 */
    @Excel(name = "性别", replace = ["男_1", "女_2", "_0"], addressList = true)
    var sex: Int? = null

    /** 生日 */
    @Excel(name = "生日", format = DatePattern.NORM_DATE_PATTERN, width = 20.0)
    var birthday: LocalDate? = null

    /** 用户角色 说明：多个角色之间逗号隔开 */
    @Excel(name = "用户角色", replace = ["普通用户_普通用户", "管理员_管理员", "_null"], addressList = true, width = 20.0)
    var roleNames: String? = null
}
