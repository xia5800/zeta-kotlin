package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.entity.SysLoginLog
import org.zetaframework.core.log.model.SysLoginLogDTO

/**
 * 登录日志 服务类
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
interface ISysLoginLogService: IService<SysLoginLog> {

    /**
     * 保存用户登录日志
     *
     * @param loginLogDTO 新增登录日志参数
     */
    fun save(loginLogDTO: SysLoginLogDTO)
}
