package org.zetaframework.core.utils

import cn.hutool.core.net.NetUtil
import cn.hutool.core.util.ArrayUtil
import cn.hutool.core.util.StrUtil
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import java.util.*

/**
 * Servlet相关工具类封装
 *
 * 说明：
 * 因为hutool基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
object ServletUtil {

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象[ServletRequest]
     * @return Map
     */
    fun getParams(request: ServletRequest): Map<String, Array<String>> {
        val map = request.parameterMap
        return Collections.unmodifiableMap(map)
    }


    /**
     * 获得所有请求参数
     *
     * @param request 请求对象[ServletRequest]
     * @return Map
     */
    fun getParamMap(request: ServletRequest): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        for ((key, value) in getParams(request)) {
            params[key] = ArrayUtil.join(value, StrUtil.COMMA)
        }
        return params
    }

    /**
     * 忽略大小写获得请求header中的信息
     *
     * @param request        请求对象[HttpServletRequest]
     * @param nameIgnoreCase 忽略大小写头信息的KEY
     * @return header值
     */
    fun getHeaderIgnoreCase(request: HttpServletRequest, nameIgnoreCase: String?): String? {
        val names = request.headerNames
        var name: String?
        while (names.hasMoreElements()) {
            name = names.nextElement()
            if (name != null && name.equals(nameIgnoreCase, ignoreCase = true)) {
                return request.getHeader(name)
            }
        }
        return null
    }


    /**
     * 获取客户端IP
     *
     * 默认检测的Header:
     * ```
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * ```
     * otherHeaderNames参数用于自定义检测的Header
     *
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     *
     * @param request          请求对象[HttpServletRequest]
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    fun getClientIP(request: HttpServletRequest, vararg otherHeaderNames: String): String {
        var headers = arrayOf(
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        )
        if (ArrayUtil.isNotEmpty<String>(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames)
        }
        return getClientIPByHeader(request, *headers)
    }

    /**
     * 获取客户端IP
     *
     * headerNames参数用于自定义检测的Header
     *
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     *
     * @param request     请求对象[HttpServletRequest]
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     * @since 4.4.1
     */
    fun getClientIPByHeader(request: HttpServletRequest, vararg headerNames: String?): String {
        var ip: String
        for (header in headerNames) {
            ip = request.getHeader(header) ?: ""
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip)
            }
        }
        ip = request.remoteAddr
        return NetUtil.getMultistageReverseProxyIp(ip)
    }

}
