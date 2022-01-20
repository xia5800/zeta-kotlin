package com.zeta.system.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.entity.SysRoleMenu
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * 角色菜单 Mapper 接口
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Repository
interface SysRoleMenuMapper: BaseMapper<SysRoleMenu> {

    /**
     * 查询用户对应的菜单
     *
     * @param userId Long
     * @param menuType String?
     * @return List<Menu?>
     */
    fun listMenuByUserId(@Param("userId")userId: Long,
                         @Param("menuType")menuType: String?): MutableList<SysMenu>

    /**
     * 根据角色id查询菜单
     *
     * @param roleIds   角色id
     * @param menuType  菜单类型
     * @return List<Menu>
     */
    fun listMenuByRoleIds(@Param("roleIds")roleIds: List<Long>,
                          @Param("menuType")menuType: String?): MutableList<SysMenu>

}
