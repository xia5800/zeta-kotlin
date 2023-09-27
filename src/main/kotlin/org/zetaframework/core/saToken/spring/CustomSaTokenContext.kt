package org.zetaframework.core.saToken.spring

import cn.dev33.satoken.spring.SaTokenContextForSpringInJakartaServlet
import org.springframework.util.AntPathMatcher

/**
 * 自定义Sa-Token 上下文处理器
 *
 * 说明：
 * 解决`No more pattern data allowed after {*...} or ** pattern element`问题
 *
 * @author gcc
 */
class CustomSaTokenContext : SaTokenContextForSpringInJakartaServlet() {

    companion object {
        private val ANT_PATH_MATCHER = AntPathMatcher()
    }

    /**
     * 判断：指定路由匹配符是否可以匹配成功指定路径
     *
     * 说明：
     * sa-token使用的PathPattern匹配器，不支持`/ **`这种写法，
     * 导致会报错`No more pattern data allowed after {*...} or ** pattern element`
     * 故改用AntPathMatcher匹配器
     */
    override fun matchPath(pattern: String, path: String): Boolean {
        return ANT_PATH_MATCHER.match(pattern, path)
    }

}