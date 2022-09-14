package org.zetaframework.core.saToken.interceptor

import cn.dev33.satoken.exception.BackResultException
import cn.dev33.satoken.exception.StopMatchException
import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import org.springframework.web.servlet.HandlerInterceptor
import org.zetaframework.core.log.event.SysLogListener
import org.zetaframework.core.log.event.SysLoginLogListener
import org.zetaframework.core.utils.ContextUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义路由拦截器
 *
 * 说明：
 * 1.SaRouteInterceptor拦截器过时之后的替代, 嗯...就是把SaRouteInterceptor用kotlin重写了
 * 2.覆盖拦截器的afterCompletion方法，进行remove ThreadLocal，防止内存溢出
 *
 * QA:
 * 为什么不用Sa-token最新提供的[SaInterceptor]拦截器呢？
 * 1.本项目使用的是自定义鉴权注解，没有使用Sa-token提供的注解。不需要用到SaInterceptor提供的注解鉴权功能，虽然这个注解鉴权功能可以关闭
 * 2.SaInterceptor拦截器构造方法的auth参数（一个Function接口），没有传入HttpServletRequest、HttpServletResponse
 *
 * @author gcc
 */
class KtRouteInterceptor: HandlerInterceptor {

    /**
     * 执行验证的方法
     *
     * 说明：
     * 1.这是kotlin的高阶函数。它接收3个参数，这3个参数和preHandle方法的参数是一致的。
     * 2.它有一个默认的实现，对每个拦截到的方法进行登录检查
     *
     * 如何使用：
     * ```
     * override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
     *     function.invoke(request, response, handler)
     *     // ...省略其它操作
     * }
     * ```
     *
     * 扩展知识：
     * [高阶函数](https://kotlinlang.org/docs/lambdas.html#higher-order-functions)
     * 还可以看看[SysLoginLogListener]、[SysLogListener]
     */
    private var function: (HttpServletRequest, HttpServletResponse, Any) -> Unit = { _: HttpServletRequest, _: HttpServletResponse, _: Any ->
        StpUtil.checkLogin()
    }

    /**
     * 无参构造方法
     */
    constructor()

    /**
     * 有参构造方法
     *
     * @param function 一个高阶函数，用于修改function的默认实现
     */
    constructor(function: (HttpServletRequest, HttpServletResponse, Any) -> Unit): this() {
        this.function = function
    }

    /** 自定义路由拦截器 */
    companion object {
        /**
         * 静态方法快速构建一个
         * @param function 自定义模式下的执行函数
         * @return 自定义路由拦截器
         */
        fun newInstance(function: (HttpServletRequest, HttpServletResponse, Any) -> Unit): KtRouteInterceptor {
            return KtRouteInterceptor(function)
        }
    }


    /**
     * 每次请求之前触发的方法
     *
     * @param request [HttpServletRequest]
     * @param response [HttpServletResponse]
     * @param handler [Any]
     * @return 返回true进入控制器，返回false不进入控制器
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return try {
            function.invoke(request, response, handler)
            true
        } catch (e: StopMatchException) {
            // 停止匹配，进入Controller
            true
        } catch (e: BackResultException) {
            // 停止匹配，向前端输出结果
            if (response.contentType == null) {
                response.contentType = "application/json;charset=utf-8"
            }
            response.writer.print(e.message)
            false
        }
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
