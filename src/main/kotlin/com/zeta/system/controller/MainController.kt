package com.zeta.system.controller

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.StrUtil
import com.wf.captcha.SpecCaptcha
import com.zeta.common.constants.SystemRedisKeyConstants.CAPTCHA_KEY
import com.zeta.system.model.entity.SysUser
import com.zeta.system.model.enumeration.UserStateEnum
import com.zeta.system.model.param.LoginParam
import com.zeta.system.model.result.CaptchaResult
import com.zeta.system.model.result.LoginResult
import com.zeta.system.model.result.LoginUserDTO
import com.zeta.system.service.ISysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zetaframework.base.controller.SuperSimpleController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.enums.LoginStateEnum
import org.zetaframework.core.log.event.SysLoginEvent
import org.zetaframework.core.log.model.SysLoginLogDTO
import org.zetaframework.core.redis.annotation.Limit
import org.zetaframework.core.redis.util.RedisUtil
import org.zetaframework.core.utils.ContextUtil
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest

/**
 * 登录认证
 * @author gcc
 */
@Api(tags = ["登录认证"])
@RestController
@RequestMapping("/api")
class MainController(
    private val applicationContext: ApplicationContext,
    private val redisUtil: RedisUtil
): SuperSimpleController<ISysUserService, SysUser>() {

    @Value("\${spring.profiles.active:prod}")
    private val env: String? = null


    /**
     * 用户登录
     * @param param LoginParam
     * @return ApiResult<LoginResult>
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    fun login(@RequestBody @Validated param: LoginParam, request: HttpServletRequest): ApiResult<LoginResult> {
        // 验证验证码
        val captchaKey = "${CAPTCHA_KEY}:${param.key}"
        val verifyCode = redisUtil.get<String>(captchaKey)
        if (StrUtil.isBlank(verifyCode)) {
            return fail("验证码过期")
        }
        if (!param.code.equals(verifyCode, true)) {
            return fail("验证码错误")
        }
        redisUtil.delete(captchaKey)

        // 查询用户, 因为账号已经判空过了所以这里直接param.account!!
        val user = service.getByAccount(param.account!!) ?: return fail("用户不存在")
        // 设置用户id，方便记录日志的时候设置创建人。ps，这里有点问题，得判断配置文件`zeta.database.userIdType`的值是啥。
        ContextUtil.setUserId(user.id!!)

        // 判断密码
        if(!service.comparePassword(param.password!!, user.password!!)) {
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.loginFail(
                param.account!!, LoginStateEnum.ERROR_PWD, request
            )))
            // 密码不正确
            return fail(LoginStateEnum.ERROR_PWD.desc)
        }
        // 判断用户状态
        if(user.state == UserStateEnum.FORBIDDEN.code) {
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.loginFail(
                param.account!!, LoginStateEnum.FAIL, "用户被禁用，无法登录", request
            )))
            return fail("用户被禁用，无法登录")
        }

        // 踢人下线并登录
        StpUtil.kickout(user.id)
        StpUtil.login(user.id)

        // 登录日志
        applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.loginSuccess(param.account!!, request = request)))

        // 构造登录返回结果
        val userDto = BeanUtil.toBean(user, LoginUserDTO::class.java)
        return success(LoginResult(StpUtil.getTokenValue(), userDto))
    }


    /**
     * 注销登录
     * @return ApiResult<Boolean>
     */
    @ApiOperation("注销登录")
    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): ApiResult<Boolean> {
        val user = service.getById(StpUtil.getLoginIdAsLong())
        if(user != null) {
            // 登出日志
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.loginFail(
                user.account ?: "", LoginStateEnum.LOGOUT, request
            )))
            // 注销登录
            StpUtil.logout()
            return success(true)
        }
        return fail("用户异常")
    }

    /**
     * 图形验证码
     *
     * 说明：
     * 限流规则一分钟十次调用
     */
    @Limit(name = "验证码接口限流", count = 10, describe="您的操作过于频繁，请稍后再试")
    @ApiOperation("图形验证码")
    @GetMapping("/captcha")
    fun captcha(): ApiResult<CaptchaResult> {
        val key = System.currentTimeMillis()

        // 验证码值缓存到redis, 5分钟有效
        val specCaptcha = SpecCaptcha(130, 48, 5)
        redisUtil.setEx("${CAPTCHA_KEY}:${key}", specCaptcha.text(), 5, TimeUnit.MINUTES)

        return if ("prod" === env) {
            // 如果生产环境，不返回验证码的值
            success(CaptchaResult(key, specCaptcha.toBase64()))
        } else success(CaptchaResult(key, specCaptcha.toBase64(), specCaptcha.text()))
    }

}
