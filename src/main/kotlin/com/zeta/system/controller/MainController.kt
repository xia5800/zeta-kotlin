package com.zeta.system.controller

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import com.zeta.system.model.entity.SysUser
import com.zeta.system.model.enumeration.UserStateEnum
import com.zeta.system.model.param.LoginParam
import com.zeta.system.model.result.LoginResult
import com.zeta.system.model.result.LoginUserDTO
import com.zeta.system.service.ISysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.context.ApplicationContext
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zetaframework.base.controller.SuperSimpleController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.enums.LoginStateEnum
import org.zetaframework.core.log.event.SysLoginEvent
import org.zetaframework.core.log.model.SysLoginLogDTO
import org.zetaframework.core.utils.ContextUtil
import javax.servlet.http.HttpServletRequest

/**
 * 登录认证
 * @author gcc
 */
@Api(tags = ["登录认证"])
@RestController
@RequestMapping("/api")
class MainController(private val applicationContext: ApplicationContext): SuperSimpleController<ISysUserService, SysUser>() {


    /**
     * 用户登录
     * @param param LoginParam
     * @return ApiResult<LoginResult>
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    fun login(@RequestBody @Validated param: LoginParam, request: HttpServletRequest): ApiResult<LoginResult> {
        // 查询用户, 因为账号已经判空过了所以这里直接param.account!!
        val user = service.getByAccount(param.account!!) ?: return fail("用户不存在")

        // 判断密码
        if(!service.comparePassword(param.password!!, user.password!!)) {
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.build(
                param.account!!, LoginStateEnum.ERROR_PWD.name, "密码不正确",  request
            )))
            return fail("密码不正确")
        }
        // 判断用户状态
        if(user.state == UserStateEnum.FORBIDDEN.code) {
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.build(
                param.account!!, LoginStateEnum.FAIL.name, "用户被禁用，无法登录",  request
            )))
            return fail("用户被禁用，无法登录")
        }

        // 踢人下线并登录
        StpUtil.kickout(user.id)
        StpUtil.login(user.id)
        ContextUtil.setUserId(user.id!!)

        // 登录日志
        applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.build(
            param.account!!, LoginStateEnum.SUCCESS.name, request = request
        )))

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
            // 登录日志
            applicationContext.publishEvent(SysLoginEvent(SysLoginLogDTO.build(
                user.account ?: "", LoginStateEnum.LOGOUT.name, "注销登录", request
            )))
            // 注销登录
            StpUtil.logout()
            return success(true)
        }
        return fail("用户异常")
    }

}
