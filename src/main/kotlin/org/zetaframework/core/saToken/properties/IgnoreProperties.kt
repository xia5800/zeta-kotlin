package org.zetaframework.core.saToken.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * security忽略鉴权配置类
 * @author gcc
 */
@ConfigurationProperties(prefix = "zeta.security.ignore")
class IgnoreProperties {

    /** 基础忽略鉴权地址 */
    var baseUrl: MutableList<String> = mutableListOf(
        "/**/*.html",
        "/**/*.css",
        "/**/*.js",
        "/**/*.ico",
        "/**/*.jpg",
        "/**/*.png",
        "/**/*.gif",
        "/**/api-docs/**",
        "/**/api-docs-ext/**",
        "/**/swagger-resources/**",
        "/**/webjars/**",
        "/druid/**",
        "/error",
        "/ws/**",
        "/api/login",
    )

    /** 忽略鉴权的地址 */
    var ignoreUrl: MutableList<String> = mutableListOf("/**/noToken/**")

}
