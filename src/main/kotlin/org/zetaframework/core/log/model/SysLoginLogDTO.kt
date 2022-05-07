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

        /**
         * 构造登录日志
         *
         * @param account 账号
         * @param state 状态
         * @param comments 备注
         * @param request HttpServletRequest
         * @return SysLoginLogDTO
         */
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

        /**
         * 构造登录成功日志
         *
         * @param account 账号
         * @param comments 备注
         * @param request HttpServletRequest
         * @return SysLoginLogDTO
         */
        fun loginSuccess(account: String, comments: String? = "登录成功", request: HttpServletRequest): SysLoginLogDTO =
            build(account, LoginStateEnum.SUCCESS.name, request = request)


        /**
         * 构造登录失败日志
         *
         * @param account 账号
         * @param state [LoginStateEnum]
         * @param request HttpServletRequest
         * @return SysLoginLogDTO
         */
        fun loginFail(account: String, state: LoginStateEnum, request: HttpServletRequest): SysLoginLogDTO =
            build(account, state.name, state.desc, request)

        /**
         * 构造登录失败日志
         *
         * @param account 账号
         * @param state 状态
         * @param comments 备注
         * @param request HttpServletRequest
         * @return SysLoginLogDTO
         */
        fun loginFail(account: String, state: LoginStateEnum, comments: String, request: HttpServletRequest): SysLoginLogDTO =
            build(account, state.name, comments, request)
    }

}
