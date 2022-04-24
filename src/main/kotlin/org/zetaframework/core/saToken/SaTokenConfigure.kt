package org.zetaframework.core.saToken

import cn.dev33.satoken.`fun`.SaFunction
import cn.dev33.satoken.context.SaHolder
import cn.dev33.satoken.context.model.SaRequest
import cn.dev33.satoken.context.model.SaResponse
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.filter.SaFilterAuthStrategy
import cn.dev33.satoken.filter.SaServletFilter
import cn.dev33.satoken.jwt.StpLogicJwtForMix
import cn.dev33.satoken.jwt.StpLogicJwtForStateless
import cn.dev33.satoken.jwt.StpLogicJwtForStyle
import cn.dev33.satoken.router.SaHttpMethod
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.stp.StpLogic
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.convert.Convert
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.enums.ErrorCodeEnum
import org.zetaframework.core.saToken.enums.TokenTypeEnum
import org.zetaframework.core.saToken.filter.KtServletFilter
import org.zetaframework.core.saToken.interceptor.KtRouteInterceptor
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
    private val tokenProperties: TokenProperties
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
            .maxAge(3600L);
    }

    /**
     * saToken跨域配置
     *
     * 说明：
     * saToken拦截的接口的跨域配置
     */
    private val beforeAuth: SaFilterAuthStrategy = SaFilterAuthStrategy {
        // ---------- 设置跨域响应头 ----------
        SaHolder.getResponse()
            .setHeader("Access-Control-Allow-Origin", "*")
            .setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH")
            .setHeader("Access-Control-Max-Age", "3600")
            .setHeader("Access-Control-Allow-Headers", "*")

        // 如果是预检请求，则立即返回到前端
        SaRouter.match(SaHttpMethod.OPTIONS).back()
    }

    /**
     * 拦截器配置
     * @param registry InterceptorRegistry
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        // list -> array -> 可变参数
        val baseUrl: Array<String> = Convert.toStrArray(ignoreProperties.baseUrl)
        val ignoreUrl: Array<String> = Convert.toStrArray(ignoreProperties.ignoreUrl)

        // sa  路由权限配置
        registry.addInterceptor(KtRouteInterceptor { req: SaRequest, res: SaResponse, handler: Any? ->
            // 自定义路由拦截
            SaRouter.match("/**").notMatch(*baseUrl).notMatch(*ignoreUrl)
                .check(SaFunction { this.checkRequest(req) })
        }).addPathPatterns("/**")
    }

    /**
     * 注册 [Sa-Token全局过滤器]
     * @return SaServletFilter
     */
    @Bean
    fun saServletFilter(): SaServletFilter {
        // list -> array -> 可变参数
        val baseUrl: Array<String> = Convert.toStrArray(ignoreProperties.baseUrl)
        val ignoreUrl: Array<String> = Convert.toStrArray(ignoreProperties.ignoreUrl)

        return KtServletFilter()
            // 指定拦截路由
            .addInclude("/**")
            // 指定放行路由
            .addExclude(*baseUrl)
            .setAuth {
                // 需要登录认证的路由:所有, 排除登录认证的路由:/api/login、swagger等
                SaRouter.match("/**").notMatch(*ignoreUrl).check(SaFunction {
                    StpUtil.checkLogin()
                    // 获取用户id，并设置到ThreadLocal中。（mybatisplus自动填充用到）
                    ContextUtil.setUserId(StpUtil.getLoginIdAsLong())
                    ContextUtil.setToken(StpUtil.getTokenValue())
                    // 也可以用这种写法 ContextUtil["token"] = StpUtil.getTokenValue()
                })
            }
            .setError(this::returnFail)
            .setBeforeAuth(beforeAuth)
    }


    /**
     * Sa-Token token风格配置
     * @return StpLogic
     */
    @Bean
    fun stpLogic(): StpLogic {
        return when(tokenProperties.type) {
            TokenTypeEnum.STYLE -> {
                logger.info("检测到sa-token采用了[jwt-style模式]")
                StpLogicJwtForStyle()
            }
            TokenTypeEnum.MIX -> {
                logger.info("检测到sa-token采用了[jwt-mix模式]")
                StpLogicJwtForMix()
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

        when(e) {
            // 处理NotLoginException异常的错误信息
            is NotLoginException -> {
                message = when(e.type) {
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

        return JSONUtil.toJsonStr(ApiResult<Boolean>(code, message))
    }

    /**
     * 校验请求头
     *
     * 说明：自定义路由拦截demo
     */
    private fun checkRequest(request: SaRequest) {
        logger.info("本次请求的请求路径为: {}", request.requestPath);
        // 获取请求头中的xx参数，进行校验...
    }

}
