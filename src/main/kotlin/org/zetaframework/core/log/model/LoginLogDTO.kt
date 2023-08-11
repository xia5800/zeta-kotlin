package org.zetaframework.core.log.model

import cn.hutool.extra.servlet.ServletUtil
import cn.hutool.http.useragent.UserAgentUtil
import org.zetaframework.core.log.enums.LoginStateEnum
import org.zetaframework.core.utils.ContextUtil
import org.zetaframework.core.utils.IpAddressUtil
import javax.servlet.http.HttpServletRequest

/**
 * 登录日志
 *
 * @author gcc
 */
data class LoginLogDTO (

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

    /** ip所在地区 */
    var ipRegion: String? = null,

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
         * @return LoginLogDTO
         */
        fun build(account: String, state: String, comments: String? = "登录成功", request: HttpServletRequest): LoginLogDTO {
            val ua = UserAgentUtil.parse(ServletUtil.getHeaderIgnoreCase(request, "User-Agent"))
            return LoginLogDTO().apply {
                this.userId = ContextUtil.getUserId()
                this.account = account
                this.state = state
                this.comments = comments
                this.os = ua.platform.name
                this.device = ua.os.name
                this.browser = ua.browser.name
                this.ip = ServletUtil.getClientIP(request)
                this.ip?.let { ip ->
                    this.ipRegion = IpAddressUtil.search(ip)
                }
            }
        }

        /**
         * 构造登录成功日志
         *
         * @param account 账号
         * @param comments 备注
         * @param request HttpServletRequest
         * @return LoginLogDTO
         */
        fun loginSuccess(account: String, comments: String? = "登录成功", request: HttpServletRequest): LoginLogDTO =
            build(account, LoginStateEnum.SUCCESS.name, request = request)


        /**
         * 构造登录失败日志
         *
         * @param account 账号
         * @param state [LoginStateEnum]
         * @param request HttpServletRequest
         * @return LoginLogDTO
         */
        fun loginFail(account: String, state: LoginStateEnum, request: HttpServletRequest): LoginLogDTO =
            build(account, state.name, state.desc, request)

        /**
         * 构造登录失败日志
         *
         * @param account 账号
         * @param state 状态
         * @param comments 备注
         * @param request HttpServletRequest
         * @return LoginLogDTO
         */
        fun loginFail(account: String, state: LoginStateEnum, comments: String, request: HttpServletRequest): LoginLogDTO =
            build(account, state.name, comments, request)
    }

}
