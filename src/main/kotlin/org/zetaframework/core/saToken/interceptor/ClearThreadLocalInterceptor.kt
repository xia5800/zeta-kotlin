package org.zetaframework.core.saToken.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.zetaframework.core.utils.ContextUtil

/**
 * 清空ThreadLocal数据拦截器
 *
 * 说明：
 * 执行完Controller之后清空ContextUtil中ThreadLocal的值
 *
 * @author gcc
 */
class ClearThreadLocalInterceptor : HandlerInterceptor {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 每次请求之前触发的方法
     *
     * @param request [HttpServletRequest]
     * @param response [HttpServletResponse]
     * @param handler [Any]
     * @return 返回true进入控制器，返回false不进入控制器
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.debug("本次请求的请求路径为: {}", request.servletPath)
        return super.preHandle(request, response, handler)
    }

    /**
     * 执行完Controller之后，要做的事
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Any
     * @param ex Exception?
     */
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        // 清空ThreadLocal的值，防止下次请求时获取到的值是旧数据，同时也能防止内存溢出
        ContextUtil.remove()
    }
}
