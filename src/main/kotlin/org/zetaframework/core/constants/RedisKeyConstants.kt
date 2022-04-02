package org.zetaframework.core.constants

/**
 * redis缓存key常量
 *
 * @author gcc
 */
object RedisKeyConstants {

    /**
     * 存放用户角色的缓存key
     *
     * 完整key: user:role:{userId} -> { roles }
     */
    const val USER_ROLE_KEY = "user:role"

    /**
     * 存放用户权限的缓存key
     *
     * 完整key: user:permission:{userId} -> { authorities }
     */
    const val USER_PERMISSION_KEY = "user:permission"
}
