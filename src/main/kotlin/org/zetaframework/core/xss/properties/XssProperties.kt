package org.zetaframework.core.xss.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.AntPathMatcher
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * XSS防护配置参数
 *
 * @author gcc
 */
@ConfigurationProperties(prefix = XssProperties.PREFIX)
class XssProperties {
    companion object {
        const val PREFIX = "zeta.xss"
        /** AntPath规则匹配器 */
        private val ANT_PATH_MATCHER = AntPathMatcher()
    }

    /** XSS防护开关 默认为：false */
    var enabled: Boolean = false

    /** 忽略xss防护的地址 */
    var excludeUrl: MutableList<String> = mutableListOf(
        "/**/noxss/**"
    )



    /**
     * 是否是忽略xss防护的地址
     *
     * @param path 请求地址
     * @return boolean
     */
    fun isIgnoreUrl(path: String): Boolean {
        return this.excludeUrl.any { url ->
            ANT_PATH_MATCHER.match(url, path)
        }
    }

    /**
     * 是否是忽略xss防护的地址
     *
     * @return boolean
     */
    fun isIgnoreUrl(): Boolean {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        // 获取当前访问的路由。获取不到直接return false
        return attributes?.request?.let {
            isIgnoreUrl(it.requestURI)
        } ?: false
    }

}
