package org.zetaframework.core.saToken.context

import cn.dev33.satoken.spring.SaPatternsRequestConditionHolder
import cn.dev33.satoken.spring.SaTokenContextForSpringInJakartaServlet
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

/**
 * 自定义Sa-Token 上下文处理器
 *
 * 说明：
 * 解决`No more pattern data allowed after {*...} or ** pattern element`问题
 *
 * @author gcc
 */
@Primary
@Component
class SaTokenContextByPatternsRequestCondition : SaTokenContextForSpringInJakartaServlet() {

    /**
     * 判断：指定路由匹配符是否可以匹配成功指定路径
     *
     * 说明：
     * 前置条件
     * ```
     * spring:
     *   mvc:
     *     pathmatch:
     *       matching-strategy: ant_path_matcher
     * ```
     */
    override fun matchPath(pattern: String, path: String): Boolean {
        return SaPatternsRequestConditionHolder.match(pattern, path)
    }

}