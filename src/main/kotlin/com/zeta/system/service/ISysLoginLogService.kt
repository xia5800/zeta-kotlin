package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.entity.SysLoginLog
import org.zetaframework.core.log.model.LoginLogDTO

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
interface ISysLoginLogService: IService<SysLoginLog> {

    /**
     * 保存用户登录日志
     *
     * @param loginLogDTO [LoginLogDTO]
     */
    fun save(loginLogDTO: LoginLogDTO)
}
