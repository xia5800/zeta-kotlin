package org.zetaframework.core.redis.annotation

import org.zetaframework.core.redis.enmus.LimitType

/**
 * # 接口限流
 *
 * 使用方式：
 * ```
 * // 登录限流，1分钟只允许调用3次。 => 限流的redisKey为：`limit:login123:{ip}`
 * @Limit(name = "登录限流", key = "login123", period = "60", count = "3")
 * fun login(loginParam: LoginParam): ApiResult<LoginResult>
 *
 * // 获取当前登录用户信息，1分钟只允许调用3次 => 限流的redisKey为：`limit:userInfo:{userId}`
 * @Limit(period = "60", count = "3", limitType = LimitType.USERID)
 * fun userInfo(): ApiResult<SysUser>
 *
 * // 最简单的写法。1分钟只允许调用10次 => 限流的redisKey为：`limit:custom:{ip}`
 * @Limit
 * fun custom(): APiResult<Boolean>
 *
 * // 修改限流后返回描述
 * @Limit(describe = "哎呀呀，你的请求太频繁了，请稍后再试哦")
 * fun custom(): APiResult<Boolean>
 * ```
 *
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Limit(
    /**
     * 名字, 当做备注用吧
     */
    val name: String = "",

    /**
     * key
     *
     * 说明：为空自动设置方法名, 详情见prefix注释
     */
    val key: String = "",

    /**
     * # Key的前缀
     *
     * 说明：
     *
     * 最终的redis限流key =  key前缀 + ":" + 处理后的key
     *
     * 例如：
     * ```
     * limit:login:127.0.0.1   (IP限流，key为""的情况下自动获取方法名)
     * limit:getUserInfo:1     (USERID限流，key为"getUserInfo")
     * ```
     */
    val prefix: String = "limit",

    /**
     * 给定的时间范围 单位(秒)
     */
    val period: Int = 60,

    /**
     * 一定时间内最多访问次数
     */
    val count: Int = 10,

    /**
     * 限流的类型(用户id 或者 请求ip)
     */
    val limitType: LimitType = LimitType.IP,

    /**
     * 限流后返回描述
     */
    val describe: String = "你的访问过于频繁，请稍后再试",

    /**
     * 是否启动限流，用于那些不想注释代码的人
     */
    val enabled: Boolean = true,
)
