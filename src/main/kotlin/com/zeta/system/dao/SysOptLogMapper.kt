package com.zeta.system.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.zeta.system.model.dto.sysOptLog.SysOptLogTableDTO
import com.zeta.system.model.entity.SysOptLog
import com.zeta.system.model.param.SysOptLogQueryParam
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * 操作日志 Mapper 接口
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@Repository
interface SysOptLogMapper: BaseMapper<SysOptLog> {

    /**
     * 分页查询操作日志
     *
     * 说明：
     * 前端数据表格用，不查询请求参数、返回值、异常描述字段
     *
     * @param page 分页对象
     * @param param 分页查询参数
     */
    fun pageTable(
        @Param("page") page: IPage<SysOptLogTableDTO>,
        @Param("param") param: SysOptLogQueryParam
    ): List<SysOptLogTableDTO>

}
