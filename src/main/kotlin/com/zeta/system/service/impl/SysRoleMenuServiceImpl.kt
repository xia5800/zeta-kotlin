package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.common.cacheKey.SaPermissionStringCacheKey
import com.zeta.common.cacheKey.SaRoleStringCacheKey
import com.zeta.system.dao.SysRoleMenuMapper
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.entity.SysRoleMenu
import com.zeta.system.model.entity.SysUserRole
import com.zeta.system.service.ISysRoleMenuService
import com.zeta.system.service.ISysUserRoleService
import org.springframework.stereotype.Service

/**
 * 角色菜单 服务实现类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Service
class SysRoleMenuServiceImpl(
    private val userRoleService: ISysUserRoleService,
    private val saRoleStringCacheKey: SaRoleStringCacheKey,
    private val saPermissionStringCacheKey: SaPermissionStringCacheKey
): ISysRoleMenuService, ServiceImpl<SysRoleMenuMapper, SysRoleMenu>() {

    /**
     * 查询用户对应的菜单
     *
     * @param userId    用户id
     * @param menuType  菜单类型
     * @return List<Menu>
     */
    override fun listMenuByUserId(userId: Long, menuType: String?): MutableList<SysMenu> {
        return baseMapper.listMenuByUserId(userId, menuType)
    }

    /**
     * 根据角色id查询菜单
     *
     * @param roleIds   角色id
     * @param menuType  菜单类型
     * @return List<Menu>
     */
    override fun listMenuByRoleIds(roleIds: List<Long>, menuType: String?): MutableList<SysMenu> {
        return baseMapper.listMenuByRoleIds(roleIds, menuType)
    }

    /**
     * 删除用户角色、权限缓存
     *
     * @param roleId Long
     * @return Boolean
     */
    override fun clearUserCache(roleId: Long) {
        // 查询角色对应的用户
        val userRoleList = userRoleService.list(KtQueryWrapper(SysUserRole())
            .eq(SysUserRole::roleId, roleId))
        if (userRoleList.isEmpty()) return

        val userIds = userRoleList!!.map { it.userId }
        // 删除用户权限缓存
        saPermissionStringCacheKey.delete(userIds)
        // 删除用户角色缓存
        saRoleStringCacheKey.delete(userIds)
    }
}
