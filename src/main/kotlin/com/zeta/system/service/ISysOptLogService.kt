package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysOptLog.SysOptLogTableDTO
import com.zeta.system.model.entity.SysOptLog
import com.zeta.system.model.param.SysOptLogQueryParam
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult
import org.zetaframework.core.log.model.SysLogDTO

/**
 * <p>
 * 操作日志 服务类
 * </p>
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
     * @param org.zetaframework.core.log.model.SysLogDTO
     */
    fun save(sysLogDTO: SysLogDTO)

    /**
     * 分页查询 前端数据表格用
     * @param param PageParam<SysOptLogQueryParam>
     */
    fun pageTable(param: PageParam<SysOptLogQueryParam>): PageResult<SysOptLogTableDTO>
}
