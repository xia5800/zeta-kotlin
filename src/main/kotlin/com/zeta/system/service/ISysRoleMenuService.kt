package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.entity.SysRoleMenu

/**
 * 角色菜单 服务类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
interface ISysRoleMenuService: IService<SysRoleMenu> {

    /**
     * 查询用户对应的菜单
     *
     * @param userId    用户id
     * @param menuType  菜单类型
     * @return List<Menu>
     */
    fun listMenuByUserId(userId: Long, menuType: String? = null): MutableList<SysMenu>

    /**
     * 根据角色id查询菜单
     *
     * @param roleIds   角色id
     * @param menuType  菜单类型
     * @return List<Menu>
     */
    fun listMenuByRoleIds(roleIds: List<Long>, menuType: String? = null): MutableList<SysMenu>

    /**
     * 删除用户角色、权限缓存
     *
     * @param roleId Long
     * @return Boolean
     */
    fun clearUserCache(roleId: Long)

}
