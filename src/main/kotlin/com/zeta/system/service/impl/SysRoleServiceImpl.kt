package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysRoleMapper
import com.zeta.system.model.entity.SysRole
import com.zeta.system.service.ISysRoleService
import org.springframework.stereotype.Service

/**
 * 角色 服务实现类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Service
class SysRoleServiceImpl: ISysRoleService, ServiceImpl<SysRoleMapper, SysRole>() {

}
