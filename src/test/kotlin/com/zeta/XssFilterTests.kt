package com.zeta

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.util.Assert
import org.zetaframework.core.xss.properties.XssProperties

/**
 * XSS过滤相关测试类
 *
 * @author gcc
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // https://docs.spring.io/spring-framework/reference/testing/annotations/integration-junit-jupiter.html#integration-testing-annotations-testconstructor
class XssFilterTests(val properties: XssProperties) {


    @Test
    fun `是否是忽略xss防护的地址 方法测试`() {
        properties.excludeUrl.addAll(mutableSetOf(
            "/api/sysUser",   // GET、POST、PUT、DELETE等请求方式都忽略
            "POST:/api/demo", // 忽略POST请求
            "PUT:/api/demo",  // 忽略PUT请求
        ))

        Assert.isTrue(properties.isIgnoreUrl("GET", "/api/sysUser"), "GET请求/api/sysUser接口，没有忽略xss防护")
        Assert.isTrue(properties.isIgnoreUrl("POST", "/api/sysUser"), "POST请求/api/sysUser接口，没有忽略xss防护")
        Assert.isTrue(properties.isIgnoreUrl("PUT", "/api/sysUser"), "PUT请求/api/sysUser接口，没有忽略xss防护")

        Assert.isTrue(!properties.isIgnoreUrl("GET", "/api/demo"), "GET请求/api/demo接口，没有忽略xss防护")

        Assert.isTrue(properties.isIgnoreUrl("POST", "/api/demo"), "POST请求/api/demo接口，没有忽略xss防护")
        Assert.isTrue(properties.isIgnoreUrl("PUT", "/api/demo"), "PUT请求/api/demo接口，没有忽略xss防护")
    }

}