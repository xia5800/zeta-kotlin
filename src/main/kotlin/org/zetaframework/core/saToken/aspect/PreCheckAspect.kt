package org.zetaframework.core.saToken.aspect

import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.stp.StpUtil
import cn.dev33.satoken.util.SaFoxUtil
import cn.hutool.core.util.StrUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreCheckRole
import org.zetaframework.core.saToken.annotation.PreMode
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

/**
 * 权限认证 切面
 *
 * @author gcc
 */
@Aspect
@Component
class PreCheckAspect {
    companion object {
        const val POINTCUT_SIGN: String =
            "@within(org.zetaframework.core.saToken.annotation.PreCheckPermission) || " +
            "@annotation(org.zetaframework.core.saToken.annotation.PreCheckPermission) || " +
            "@within(org.zetaframework.core.saToken.annotation.PreCheckRole) || " +
            "@annotation(org.zetaframework.core.saToken.annotation.PreCheckRole)"
    }

    @Around(POINTCUT_SIGN)
    fun doAround(joinPoint: ProceedingJoinPoint): Any {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method

        var replace = ""
        var enabled = true

        // 获取父类上的@PreAuth注解
        val declaringClass = method.declaringClass
        if(declaringClass.isAnnotationPresent(PreAuth::class.java)) {
            val declaringAnnotation: PreAuth = declaringClass.getAnnotation(PreAuth::class.java)
            enabled = declaringAnnotation.enabled
            replace = declaringAnnotation.replace
        }

        // 获取类上面的@PreAuth注解
        val clazz = joinPoint.target.javaClass
        if (clazz.isAnnotationPresent(PreAuth::class.java)) {
            val clazzAnnotation: PreAuth = clazz.getAnnotation(PreAuth::class.java)
            enabled = clazzAnnotation.enabled
            replace = clazzAnnotation.replace
        }

        // 鉴权
        if(enabled) {
            checkMethodAnnotation(replace, method)
        }

        return joinPoint.proceed()
    }


    /**
     * 检查方法上的注解
     * @param method
     */
    private fun checkMethodAnnotation(replace: String, method: Method) {
        // 先校验 Method 所属 Class 上的注解
        validateAnnotation(replace, method.declaringClass)
        // 再校验 Method 上的注解
        validateAnnotation(replace, method)
    }

    /**
     * 校验注解
     * @param replace String
     * @param element AnnotatedElement
     */
    private fun validateAnnotation(replace: String, element: AnnotatedElement) {
        val checkPermission: PreCheckPermission? = element.getAnnotation(PreCheckPermission::class.java)
        if (checkPermission != null && checkPermission.enabled) {
            checkByAnnotation(replace, checkPermission)
        }
        val checkRole: PreCheckRole? = element.getAnnotation(PreCheckRole::class.java)
        if (checkRole != null && checkRole.enabled) {
            checkByAnnotation(checkRole)
        }
    }


    /**
     * 校验PreCheckPermission注解
     *
     * @param replace String
     * @param annotation PreCheckPermission
     */
    private fun checkByAnnotation(replace: String, annotation: PreCheckPermission) {
        var value: Array<String> = annotation.value
        value = handleValue(replace, value)
        try {
            if (annotation.mode === PreMode.AND) {
                StpUtil.checkPermissionAnd(*value)
            } else {
                StpUtil.checkPermissionOr(*value)
            }
        } catch (e: NotPermissionException) {
            // 权限认证未通过，再开始角色认证
            if (annotation.orRole.isNotEmpty()) {
                for (role in annotation.orRole) {
                    val roleArray = SaFoxUtil.convertStringToArray(role)
                    // 某一项role认证通过，则可以提前退出了，代表通过
                    if (StpUtil.hasRoleAnd(*roleArray)) {
                        return
                    }
                }
            }
            throw e
        }
    }

    /**
     * 校验PreCheckRole注解
     *
     * @param annotation PreCheckRole
     */
    private fun checkByAnnotation(annotation: PreCheckRole) {
        val value: Array<String> = annotation.value
        if (annotation.mode === PreMode.AND) {
            StpUtil.checkRoleAnd(*value)
        } else {
            StpUtil.checkRoleOr(*value)
        }
    }

    /**
     * 处理权限码
     *
     * 例如：["{}:add", "{}:save"] => ["sys:user:add", "sys:user:save"]
     * @param replace String?
     * @param value Array<String>
     * @return Array<String>
     */
    private fun handleValue(replace: String?, value: Array<String>): Array<String> {
        var result = value
        if (StrUtil.isNotBlank(replace)) {
            result = result.map {
                StrUtil.format(it, replace)
            }.toTypedArray()
        }
        return result
    }
}
