package org.zetaframework.core.saToken.annotation

/**
 * 注解鉴权的验证模式
 *
 * @author gcc
 */
enum class PreMode {
    /** 必须具有所有的元素 */
    AND,
    /** 只需具有其中一个元素 */
    OR;
}
