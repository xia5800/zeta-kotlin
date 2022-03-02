package org.zetaframework.core.saToken.filter

import cn.dev33.satoken.`fun`.SaParamFunction
import cn.dev33.satoken.exception.BackResultException
import cn.dev33.satoken.exception.StopMatchException
import cn.dev33.satoken.filter.SaServletFilter
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.router.SaRouterStaff
import cn.dev33.satoken.util.SaTokenConsts
import org.springframework.core.annotation.Order
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * Servlet全局过滤器
 *
 * 说明：重写[sa-token]Servlet全局过滤器的doFilter方法, 返回json类型的数据
 * @author gcc
 */
@Order(SaTokenConsts.ASSEMBLY_ORDER)
class KtServletFilter: SaServletFilter() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            // 执行全局过滤器
            SaRouter.match(includeList).notMatch(excludeList)
                .check(SaParamFunction<SaRouterStaff> { r: SaRouterStaff? ->
                    beforeAuth.run(null)
                    auth.run(null)
                })
        } catch (e: StopMatchException) {
        } catch (e: Throwable) {
            // 1. 获取异常处理策略结果
            val result = if (e is BackResultException) e.message else error.run(e).toString()

            // 2. 写入输出流 (改了这里)
            response.contentType = "application/json;charset=utf-8"
            response.writer.print(result)
            return
        }

        // 执行
        chain.doFilter(request, response)
    }
}
