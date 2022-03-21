package com.zeta.system.service

import com.zeta.system.model.entity.SysLoginLog
import com.baomidou.mybatisplus.extension.service.IService
import org.zetaframework.core.log.model.SysLoginLogDTO

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
     * @param loginLogDTO [SysLoginLogDTO]
     */
    fun save(loginLogDTO: SysLoginLogDTO)
}
