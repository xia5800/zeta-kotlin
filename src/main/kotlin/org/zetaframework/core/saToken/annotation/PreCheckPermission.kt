package org.zetaframework.core.saToken.annotation

/**
 * 权限认证
 *
 * 说明：
 * 参考sa-token框架的@SaCheckPermission注解实现
 *
 * 使用方式:
 * ```
 *      @PreAuth(replace = "sys:user")
 *      // 校验是否有sys:user:add权限
 *      @PreCheckPermission(value = ["{}:add"])
 *      // 校验是否有sys:user:add 和 sys:user:save权限
 *      @PreCheckPermission(value = ["{}:add", "{}:save"])
 *      // 校验是否有sys:user:add 或 sys:user:save权限
 *      @PreCheckPermission(value = ["{}:add", "{}:save"], mode = PreMode.OR)
 *      // 校验是否有sys:user:add权限 或 是否有admin角色
 *      @PreCheckPermission(value = ["{}:add"], orRole = ["admin"])
 *      // 关闭接口鉴权
 *      @PreCheckPermission(enabled = false)
 *      @PreCheckPermission(value = ["{}:add"], enabled = false)
 * ```
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PreCheckPermission(

    /**
     * 需要校验的权限码
     */
    val value: Array<String> = [],

    /**
     * 验证模式：AND | OR，默认AND
     */
    val mode: PreMode = PreMode.AND,

    /**
     * 在权限认证不通过时的次要选择，两者只要其一认证成功即可通过校验
     */
    val orRole: Array<String> = [],

    /**
     * 该类、方法是否启用权限校验
     */
    val enabled: Boolean = true,
)
