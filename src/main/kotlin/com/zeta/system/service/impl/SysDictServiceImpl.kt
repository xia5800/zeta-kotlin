package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysDictMapper
import com.zeta.system.model.entity.SysDict
import com.zeta.system.service.ISysDictService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:09
 */
@Service
class SysDictServiceImpl: ISysDictService, ServiceImpl<SysDictMapper, SysDict>() {
    private val logger = LoggerFactory.getLogger(this::class.java)


}
