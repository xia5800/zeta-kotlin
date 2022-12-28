package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.entity.SysRole

/**
 * 角色 服务类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
interface ISysRoleService: IService<SysRole> {

    /**
     * 通过角色名查询角色
     *
     * @param name 角色名
     * @return 角色名对应的角色
     */
    fun getRoleByName(name: String): SysRole?

    /**
     * 通过角色名查询角色
     *
     * @param names 角色名列表
     * @return 角色名对应的角色
     */
    fun getRolesByNames(names: List<String>): List<SysRole>


    /**
     * 通过角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色编码对应的角色
     */
    fun getRoleByCode(code: String): SysRole?


    /**
     * 通过角色编码查询角色
     *
     * @param codes 角色编码列表
     * @return 角色编码对应的角色
     */
    fun getRolesByCodes(codes: List<String>): List<SysRole>

}
