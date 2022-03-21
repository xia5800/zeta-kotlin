package org.zetaframework.core.log.model

import cn.hutool.extra.servlet.ServletUtil
import cn.hutool.http.useragent.UserAgentUtil
import org.zetaframework.core.log.enums.LoginStateEnum
import org.zetaframework.core.utils.ContextUtil
import javax.servlet.http.HttpServletRequest

/**
 * 登录日志
 *
 * @author gcc
 */
data class SysLoginLogDTO (

    /** 状态 see: [LoginStateEnum] */
    var state: String? = null,

    /** 用户id */
    var userId: Long? = null,

    /** 账号 */
    var account: String? = null,

    /** 操作系统 */
    var os: String? = null,

    /** 设备名称 */
    var device: String? = null,

    /** 浏览器类型 */
    var browser: String? = null,

    /** ip地址 */
    var ip: String? = null,

    /** 备注 */
    var comments: String? = null,
) {

    companion object {
        fun build(account: String, state: String, comments: String? = "登录成功", request: HttpServletRequest): SysLoginLogDTO {
            val ua = UserAgentUtil.parse(ServletUtil.getHeaderIgnoreCase(request, "User-Agent"))
            return SysLoginLogDTO().apply {
                this.userId = ContextUtil.getUserId()
                this.account = account
                this.state = state
                this.comments = comments
                os = ua.platform.name
                device = ua.os.name
                browser = ua.browser.name
                ip = ServletUtil.getClientIP(request)
            }
        }
    }

}
