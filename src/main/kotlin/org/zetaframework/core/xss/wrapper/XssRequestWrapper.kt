package org.zetaframework.core.xss.wrapper

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.zetaframework.core.xss.cleaner.XssCleaner

/**
 * 自定义用于XSS防护的 请求包装器
 *
 * @author gcc
 */
class XssRequestWrapper(
    request: HttpServletRequest,
    private val xssCleaner: XssCleaner
): HttpServletRequestWrapper(request) {

    /**
     * 重写getParameterMap方法，实现XSS防御
     */
    override fun getParameterMap(): MutableMap<String, Array<String>> {
        val requestMap: MutableMap<String, Array<String>> = super.getParameterMap()
        val result: MutableMap<String, Array<String>> = mutableMapOf()

        // 处理参数值
        requestMap.forEach { (key, values) ->
            if (values.isNotEmpty()) {
                // 等价于 result.put(key, 清理过的value)
                result[key] = values.map {
                    xssCleaner.clear(it)
                }.toTypedArray()
            } else {
                result[key] = values
            }
        }

        return result
    }

    /**
     * 重写getQueryString方法，实现XSS防御
     */
    override fun getQueryString(): String? {
        val value = super.getQueryString()
        return if (value.isNullOrBlank()) value else xssCleaner.clear(value)
    }

    /**
     * 重写getParameterValues方法，实现XSS防御
     */
    override fun getParameterValues(name: String): Array<String>? {
        val values = super.getParameterValues(name)
        if (values.isNullOrEmpty()) return null
        return values.map { xssCleaner.clear(it) }.toTypedArray()
    }

    /**
     * 重写getParameter方法，实现XSS防御
     */
    override fun getParameter(name: String): String? {
        val value = super.getParameter(name)
        return if (value.isNullOrBlank()) value else xssCleaner.clear(value)
    }

    /**
     * 重写getHeader方法，实现XSS防御
     */
    override fun getHeader(name: String): String? {
        val value = super.getHeader(name)
        return if (value.isNullOrBlank()) value else xssCleaner.clear(value)
    }
}
