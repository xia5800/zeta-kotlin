package org.zetaframework.core.redis.aspect

import cn.hutool.extra.servlet.ServletUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.zetaframework.core.redis.annotation.Limit
import org.zetaframework.core.redis.enmu.LimitType
import org.zetaframework.core.redis.exception.LimitException
import org.zetaframework.core.redis.util.RedisUtil
import org.zetaframework.core.utils.ContextUtil

/**
 * 限流注解 切面
 * @author gcc
 */
@Aspect
@Component
class LimitAspect(private val redisUtil: RedisUtil) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    @Around("@annotation(org.zetaframework.core.redis.annotation.Limit)")
    fun doAround(joinPoint: ProceedingJoinPoint): Any {
        // 获取方法上的注解
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val limitAnnotation = method.getAnnotation(Limit::class.java)

        if (!limitAnnotation.enabled) {
            // 不启动限流
            return joinPoint.proceed()
        }

        // 获取注解里设置的key值
        var methodName = limitAnnotation.key
        if (methodName.isBlank()) {
            methodName = method.name
        }

        // 根据限流的类型返回处理后的redisKey 如果是IP限流，则redisKey = limit:{methodName}:{ip}。
        val suffix: String = getRedisKey(limitAnnotation, methodName)
        val redisKey = "${limitAnnotation.prefix}:$suffix"

        try {
            // 获取限流情况
            val limit = redisUtil.luaScriptLimit(redisKey, limitAnnotation.period, limitAnnotation.count)
            if (!limit) {
                // 触发限流
                logger.info("触发接口限流")
                throw LimitException(limitAnnotation.describe)
            }
        } catch (e: Exception) {
            logger.error("获取限流情况失败", e)
            // 获取限流情况失败
            throw LimitException(limitAnnotation.describe)
        }

        return joinPoint.proceed()
    }


    /**
     * 根据限流的类型返回处理后的redisKey
     *
     * @param limitAnnotation 限流注解
     * @param methodName      方法名
     * @return redisKey: String
     */
    private fun getRedisKey(limitAnnotation: Limit, methodName: String): String = when (limitAnnotation.limitType) {
        // IP限流
        LimitType.IP -> {
            // 获取请求ip
            var ip = "unknown"
            val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
            val request = attributes?.request
            if (request != null) {
                ip = ServletUtil.getClientIP(request)
            }
            "$methodName:$ip"
        }

        // 用户id限流
        LimitType.USERID -> {
            // 获取用户id
            val userId = ContextUtil.getUserIdStr().let {
                if (it.isBlank()) {
                    throw LimitException("获取用户id失败")
                }
                it
            }
            "$methodName:$userId"
        }
    }
}
