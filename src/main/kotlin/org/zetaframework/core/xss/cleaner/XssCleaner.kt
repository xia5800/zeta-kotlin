package org.zetaframework.core.xss.cleaner

/**
 * XSS文本清理 接口
 *
 * @author gcc
 */
interface XssCleaner {


    /**
     * 清理有XSS风险的文本
     *
     * @param value 有风险的xss文本
     * @return 清理后的xss文本
     */
    fun clear(value: String): String

}
