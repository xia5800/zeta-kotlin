package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.entity.SysRole
import com.zeta.system.model.entity.SysUserRole

/**
 * 用户角色 服务类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
interface ISysUserRoleService: IService<SysUserRole> {

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return List<Role>
     */
    fun listByUserId(userId: Long): List<SysRole>

    /**
     * 批量根据用户id查询角色
     *
     * @param userIds 用户id集合
     * @return List<RoleResult>
     */
    fun listByUserIds(userIds: List<Long>): List<SysRoleDTO>

}
