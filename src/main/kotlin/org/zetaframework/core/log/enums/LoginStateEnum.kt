package org.zetaframework.core.log.enums

/**
 * 登录状态枚举
 * @author gcc
 */
enum class LoginStateEnum(var desc: String) {
    /** 登录成功 */
    SUCCESS("登录成功"),
    /** 登录失败 */
    FAIL("登录失败"),
    /** 密码错误 */
    ERROR_PWD("密码不正确"),
    /** 退出登录 */
    LOGOUT("注销登录");
}
