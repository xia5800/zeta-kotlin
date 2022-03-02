package org.zetaframework.core.saToken.interceptor

import cn.dev33.satoken.interceptor.SaRouteInterceptor
import cn.dev33.satoken.router.SaRouteFunction
import org.slf4j.LoggerFactory
import org.zetaframework.core.utils.ContextUtil
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * sa-token基于路由的拦截式鉴权
 *
 * 说明：
 * 覆盖拦截器的afterCompletion方法，进行remove ThreadLocal，防止内存溢出
 * @author gcc
 */
class KtRouteInterceptor(function: SaRouteFunction): SaRouteInterceptor(function) {

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
