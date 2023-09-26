package com.zeta.system.service.impl

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysLoginLogMapper
import com.zeta.system.model.entity.SysLoginLog
import com.zeta.system.service.ISysLoginLogService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.zetaframework.core.log.model.LoginLogDTO

/**
 * 登录日志 服务实现类
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@Service
class SysLoginLogServiceImpl: ISysLoginLogService, ServiceImpl<SysLoginLogMapper, SysLoginLog>() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 保存用户登录日志
     *
     * @param loginLogDTO 新增登录日志参数
     */
    override fun save(loginLogDTO: LoginLogDTO) {
        val loginLog = BeanUtil.toBean(loginLogDTO, SysLoginLog::class.java)
        loginLog.createdBy = loginLogDTO.userId
        this.save(loginLog)
    }

}
