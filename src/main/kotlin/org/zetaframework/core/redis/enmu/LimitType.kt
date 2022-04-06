package org.zetaframework.core.redis.enmu

/**
 * 限流类型
 *
 * @author gcc
 */
enum class LimitType {
    /** ip限流 */
    IP,
    /** 用户id限流 */
    USERID;
}
