package org.zetaframework.core.xss.annotation

/**
 * 排除xss过滤
 *
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class NoXss()
