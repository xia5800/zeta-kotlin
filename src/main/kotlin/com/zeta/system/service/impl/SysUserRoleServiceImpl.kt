package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysUserRoleMapper
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.entity.SysRole
import com.zeta.system.model.entity.SysUserRole
import com.zeta.system.service.ISysUserRoleService
import org.springframework.stereotype.Service

/**
 * 用户角色 服务实现类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Service
class SysUserRoleServiceImpl: ISysUserRoleService, ServiceImpl<SysUserRoleMapper, SysUserRole>() {

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return List<Role>
     */
    override fun listByUserId(userId: Long): List<SysRole> {
        return baseMapper.selectByUserId(userId)
    }

    /**
     * 批量根据用户id查询角色
     *
     * @param userIds 用户id集合
     * @return List<RoleResult>
     */
    override fun listByUserIds(userIds: List<Long>): List<SysRoleDTO> {
        return baseMapper.selectByUserIds(userIds)
    }
}
