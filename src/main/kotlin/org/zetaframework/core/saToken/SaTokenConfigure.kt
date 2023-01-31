package org.zetaframework.core.saToken

import cn.dev33.satoken.`fun`.SaFunction
import cn.dev33.satoken.context.SaHolder
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.filter.SaFilterAuthStrategy
import cn.dev33.satoken.filter.SaServletFilter
import cn.dev33.satoken.jwt.StpLogicJwtForMixin
import cn.dev33.satoken.jwt.StpLogicJwtForSimple
import cn.dev33.satoken.jwt.StpLogicJwtForStateless
import cn.dev33.satoken.router.SaHttpMethod
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.spring.SpringMVCUtil
import cn.dev33.satoken.stp.StpLogic
import cn.dev33.satoken.stp.StpUtil
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.enums.ErrorCodeEnum
import org.zetaframework.core.mybatisplus.enums.UserIdType
import org.zetaframework.core.mybatisplus.properties.DatabaseProperties
import org.zetaframework.core.saToken.enums.TokenTypeEnum
import org.zetaframework.core.saToken.interceptor.ClearThreadLocalInterceptor
import org.zetaframework.core.saToken.properties.IgnoreProperties
import org.zetaframework.core.saToken.properties.TokenProperties
import org.zetaframework.core.utils.ContextUtil
import org.zetaframework.core.utils.JSONUtil


/**
 * [Sa-Token 权限认证] 配置类
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(IgnoreProperties::class, TokenProperties::class)
class SaTokenConfigure(
    private val ignoreProperties: IgnoreProperties,
    private val tokenProperties: TokenProperties,
    private val dataBaseProperties: DatabaseProperties
): WebMvcConfigurer {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 跨域配置
     *
     * 说明：
     * 非saToken拦截的接口的跨域配置
     * @param registry CorsRegistry
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowCredentials(true)
            .maxAge(3600L)
    }

    /**
     * SaToken过滤器[前置函数]：在每次[认证函数]之前执行
     *
     * 说明：
     * saToken拦截的接口的跨域配置
     */
    private val beforeAuth: SaFilterAuthStrategy = SaFilterAuthStrategy {
        // saToken跨域配置
        SaHolder.getResponse()
            .setHeader("Access-Control-Allow-Origin", "*")
            .setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH")
            .setHeader("Access-Control-Max-Age", "3600")
            .setHeader("Access-Control-Allow-Headers", "*")
            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
            .setHeader("X-XSS-Protection", "1; mode=block")

        // 如果是预检请求，则立即返回到前端
        SaRouter.match(SaHttpMethod.OPTIONS).back()
    }

    /**
     * SaToken过滤器[认证函数]: 每次请求都会执行
     *
     * 说明：
     * saToken接口拦截并处理
     */
    private val auth: SaFilterAuthStrategy = SaFilterAuthStrategy {
        // 需要登录认证的路由:所有, 排除登录认证的路由:/api/login、swagger等
        SaRouter.match("/**").check(SaFunction {
            StpUtil.checkLogin()
            // token续期
            if (tokenProperties.renew) {
                StpUtil.renewTimeout(tokenProperties.expireTime)
            }
            // 获取用户id，并设置到ThreadLocal中。（mybatisplus自动填充用到）
            when (dataBaseProperties.userIdType) {
                UserIdType.Long -> ContextUtil.setUserId(StpUtil.getLoginIdAsLong())
                UserIdType.Int -> ContextUtil.setUserId(StpUtil.getLoginIdAsInt())
                UserIdType.String -> ContextUtil.setUserId(StpUtil.getLoginIdAsString())
            }
            ContextUtil.setToken(StpUtil.getTokenValue())
        })
    }

    /**
     * 拦截器配置
     *
     * 说明：
     * 可以在这里使用[拦截器鉴权](https://sa-token.dev33.cn/doc/index.html#/use/route-check)
     * 针对某个接口，某些接口单独进行权限校验
     * @param registry InterceptorRegistry
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 清空ThreadLocal数据拦截器。
        registry.addInterceptor(ClearThreadLocalInterceptor()).addPathPatterns("/api/**")
    }

    /**
     * 注册 [Sa-Token全局过滤器]
     * @return SaServletFilter
     */
    @Bean
    fun saServletFilter(): SaServletFilter {
        return SaServletFilter()
            // 指定拦截路由
            .addInclude("/**")
            // 指定放行路由
            .setExcludeList(ignoreProperties.getNotMatchUrl())
            .setBeforeAuth(beforeAuth)
            .setAuth(auth)
            .setError(this::returnFail)
    }


    /**
     * Sa-Token token风格配置
     * @return StpLogic
     */
    @Bean
    fun stpLogic(): StpLogic {
        return when (tokenProperties.type) {
            TokenTypeEnum.SIMPLE -> {
                logger.info("检测到sa-token采用了[jwt-simple模式]")
                StpLogicJwtForSimple()
            }
            TokenTypeEnum.MIXIN -> {
                logger.info("检测到sa-token采用了[jwt-mixin模式]")
                StpLogicJwtForMixin()
            }
            TokenTypeEnum.STATELESS -> {
                logger.info("检测到sa-token采用了[jwt-stateless模式]")
                StpLogicJwtForStateless()
            }
            else -> {
                logger.info("检测到sa-token采用了default模式")
                StpLogic(StpUtil.TYPE)
            }
        }
    }


    /**
     * return 错误消息
     *
     * 注意：这里的异常不会被GlobalExceptionHandler(全局异常处理器)捕获处理
     * @param e
     * @return
     */
    private fun returnFail(e: Throwable): String? {
        // 初始化错误码和错误信息
        var code: Int = ErrorCodeEnum.FAIL.code
        var message: String? = ""

        when (e) {
            // 处理NotLoginException异常的错误信息
            is NotLoginException -> {
                message = when (e.type) {
                    NotLoginException.NOT_TOKEN -> NotLoginException.NOT_TOKEN_MESSAGE
                    NotLoginException.INVALID_TOKEN -> NotLoginException.INVALID_TOKEN_MESSAGE
                    NotLoginException.TOKEN_TIMEOUT -> NotLoginException.TOKEN_TIMEOUT_MESSAGE
                    NotLoginException.BE_REPLACED -> NotLoginException.BE_REPLACED_MESSAGE
                    NotLoginException.KICK_OUT -> NotLoginException.KICK_OUT_MESSAGE
                    else -> NotLoginException.DEFAULT_MESSAGE
                }
                code = ErrorCodeEnum.UNAUTHORIZED.code
            }
            // 处理NotRoleException和NotPermissionException异常的错误信息
            is NotRoleException, is NotPermissionException -> {
                message = ErrorCodeEnum.FORBIDDEN.msg
                code = ErrorCodeEnum.FORBIDDEN.code
            }
            // 处理其它异常的错误信息
            else -> message = e.message
        }

        // 手动设置Content-Type为json格式，替换之前重写SaServletFilter.doFilter方法的写法
        SpringMVCUtil.getResponse().setHeader("Content-Type", "application/json;charset=utf-8")
        return JSONUtil.toJsonStr(ApiResult<Boolean>(code, message))
    }

}
