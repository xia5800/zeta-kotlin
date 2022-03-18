package org.zetaframework.core.saToken.annotation

/**
 * 权限认证
 *
 * 说明：
 * 参考sa-token框架的@SaCheckRole注解实现
 *
 * 使用方式:
 * ```
 *      // 校验是否有admin角色
 *      @PreCheckRole(value = ["admin"])
 *      // 校验是否有admin 和 super_admin角色
 *      @PreCheckRole(value = ["admin", "super_admin"])
 *      // 校验是否有admin 或 super_admin角色
 *      @PreCheckRole(value = ["admin", "super_admin"], mode = PreMode.OR)
 *      // 关闭角色鉴权
 *      @PreCheckRole(enable = false)
 *      @PreCheckRole(value = ["admin"], enable = false)
 * ```
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PreCheckRole(
    /**
     * 需要校验的权限码
     */
    val value: Array<String> = [],

    /**
     * 验证模式：AND | OR，默认AND
     */
    val mode: PreMode = PreMode.AND,

    /**
     * 该类、方法是否启用角色校验
     */
    val enabled: Boolean = true,
)
