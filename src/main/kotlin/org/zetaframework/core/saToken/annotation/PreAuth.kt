package org.zetaframework.core.saToken.annotation

/**
 * 用户权限{}占位符替换
 *
 * 说明：
 * 配合@PreCheckPermission注解使用。只能用于类
 *
 * 使用方式：
 * <pre>
 *      @PreAuth(replace = "sys:user")
 *      // 校验是否有sys:user:add权限
 *      @PreCheckPermission(value = ["{}:add"])
 *      // 关闭类鉴权
 *      @PreAuth(enabled = false)
 * </pre>
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PreAuth(

    /**
     * 替换@PreCheckPermission注解中value的占位符{}
     */
    val replace: String = "",

    /**
     * 该类是否启用权限、角色校验
     */
    val enabled: Boolean = true
)
