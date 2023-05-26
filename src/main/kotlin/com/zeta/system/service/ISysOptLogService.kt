package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysOptLog.SysOptLogTableDTO
import com.zeta.system.model.entity.SysOptLog
import com.zeta.system.model.param.SysOptLogQueryParam
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult
import org.zetaframework.core.log.model.SysLogDTO

/**
 * 操作日志 服务类
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
interface ISysOptLogService: IService<SysOptLog> {

    /**
     * 保存系统用户操作日志
     *
     * 说明：
     * [@SysLog]注解的业务实现
     *
     * @param sysLogDTO 新增系统日志参数
     */
    fun save(sysLogDTO: SysLogDTO)

    /**
     * 分页查询 （前端数据表格用）
     *
     * @param param 分页查询参数
     */
    fun pageTable(param: PageParam<SysOptLogQueryParam>): PageResult<SysOptLogTableDTO>
}
