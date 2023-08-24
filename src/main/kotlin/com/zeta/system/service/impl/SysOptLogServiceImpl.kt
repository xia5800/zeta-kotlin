package com.zeta.system.service.impl

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysOptLogMapper
import com.zeta.system.model.dto.sysOptLog.SysOptLogTableDTO
import com.zeta.system.model.entity.SysOptLog
import com.zeta.system.model.param.SysOptLogQueryParam
import com.zeta.system.service.ISysOptLogService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult
import org.zetaframework.core.log.model.LogDTO

/**
 * 操作日志 服务实现类
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@Service
class SysOptLogServiceImpl: ISysOptLogService, ServiceImpl<SysOptLogMapper, SysOptLog>() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 保存系统用户操作日志
     *
     * 说明：
     * [@SysLog]注解的业务实现
     * @param logDTO [LogDTO]
     */
    override fun save(logDTO: LogDTO) {
        val optLog = BeanUtil.toBean(logDTO, SysOptLog::class.java)
        this.save(optLog)
    }

    /**
     * 分页查询 前端数据表格用
     * @param param PageParam<SysOptLogQueryParam>
     */
    override fun pageTable(param: PageParam<SysOptLogQueryParam>): PageResult<SysOptLogTableDTO> {
        val page = param.buildPage<SysOptLogTableDTO>()
        // bug fix: 解决“分页查询参数model值为null时，mybatis的查询条件构造失败”问题 --by gcc
        val optLogList: List<SysOptLogTableDTO> = baseMapper.pageTable(page, param.model ?: SysOptLogQueryParam())
        return PageResult(optLogList, page.total)
    }


}
