package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
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

    /**
     * 通过角色名查询角色
     *
     * @param name 角色名
     * @return 角色名对应的角色
     */
    override fun getRoleByName(name: String): SysRole? {
        return this.getOne(KtQueryWrapper(SysRole())
            .eq(SysRole::name, name)
            .orderByDesc(SysRole::id)
        )
    }

    /**
     * 通过角色名查询角色
     *
     * @param names 角色名列表
     * @return 角色名对应的角色
     */
    override fun getRolesByNames(names: List<String>): List<SysRole> {
        return this.list(KtQueryWrapper(SysRole()).`in`(SysRole::name, names))
    }

    /**
     * 通过角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色编码对应的角色
     */
    override fun getRoleByCode(code: String): SysRole? {
        return this.getOne(KtQueryWrapper(SysRole())
            .eq(SysRole::code, code)
            .orderByDesc(SysRole::id)
        )
    }

    /**
     * 通过角色编码查询角色
     *
     * @param codes 角色编码列表
     * @return 角色编码对应的角色
     */
    override fun getRolesByCodes(codes: List<String>): List<SysRole> {
        return this.list(KtQueryWrapper(SysRole()).`in`(SysRole::code, codes))
    }

}
