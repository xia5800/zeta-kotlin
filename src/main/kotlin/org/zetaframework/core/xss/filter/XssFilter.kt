package org.zetaframework.core.xss.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.zetaframework.core.xss.cleaner.XssCleaner
import org.zetaframework.core.xss.properties.XssProperties
import org.zetaframework.core.xss.wrapper.XssRequestWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义用于XSS防护的 过滤器
 *
 * @author gcc
 */
class XssFilter(
    private val xssCleaner: XssCleaner,
    private val xssProperties: XssProperties,
): OncePerRequestFilter() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 执行过滤
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(XssRequestWrapper(request, xssCleaner), response)
        }catch (e: Exception) {
            log.error("执行XSS过滤失败", e)
        }
    }

    /**
     * 如果返回true，则这个请求不会被过滤
     */
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // XSS防护开关是否关闭
        if (!xssProperties.enabled) {
            return true
        }

        // 当前url是否是忽略xss防护的地址
        return xssProperties.isIgnoreUrl(request.requestURI)
    }
}
