package org.zetaframework.core.log.enums

/**
 * 登录状态枚举
 * @author gcc
 */
enum class LoginStateEnum {
    /** 登录成功 */
    SUCCESS,
    /** 登录失败 */
    FAIL,
    /** 密码错误 */
    ERROR_PWD,
    /** 退出登录 */
    LOGOUT;
}
