package com.zeta.system.service.impl

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpInterface
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.collection.CollUtil
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysUserMapper
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.dto.sysUser.SysUserSaveDTO
import com.zeta.system.model.dto.sysUser.SysUserUpdateDTO
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.entity.SysRole
import com.zeta.system.model.entity.SysUser
import com.zeta.system.model.enumeration.UserStateEnum
import com.zeta.system.service.ISysRoleMenuService
import com.zeta.system.service.ISysUserRoleService
import com.zeta.system.service.ISysUserService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zetaframework.core.constants.RedisKeyConstants.USER_PERMISSION_KEY
import org.zetaframework.core.constants.RedisKeyConstants.USER_ROLE_KEY
import org.zetaframework.core.exception.BusinessException

/**
 * 用户 服务实现类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Service
class SysUserServiceImpl(
    private val userRoleService: ISysUserRoleService,
    private val roleMenuService: ISysRoleMenuService,
): ISysUserService, ServiceImpl<SysUserMapper, SysUser>(), StpInterface {

    /**
     * 添加用户
     * @param saveDTO SysUserSaveDTO
     * @return Boolean
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun saveUser(saveDTO: SysUserSaveDTO): Boolean {
        // 保存用户
        val user = BeanUtil.toBean(saveDTO, SysUser::class.java)
        user.password = encodePassword(saveDTO.password!!)
        user.readonly = false
        user.state = UserStateEnum.NORMAL.code
        if(!this.save(user)) {
            throw BusinessException("新增用户失败")
        }

        // 删除并重新关联角色
        return userRoleService.saveUserRole(user.id!!, saveDTO.roleIds!!)
    }

    /**
     * 修改用户
     * @param updateDTO SysUserUpdateDTO
     * @return Boolean
     */
    @CacheEvict(value = [USER_PERMISSION_KEY, USER_ROLE_KEY], key = "#updateDTO.id")
    @Transactional(rollbackFor = [Exception::class])
    override fun updateUser(updateDTO: SysUserUpdateDTO): Boolean {
        val user = BeanUtil.toBean(updateDTO, SysUser::class.java)
        if(!this.updateById(user)) {
            throw BusinessException("修改用户失败")
        }

        // 删除并重新关联角色
        return userRoleService.saveUserRole(user.id!!, updateDTO.roleIds)
    }

    /**
     * 获取用户角色
     *
     * @param id Long
     * @return List<SysRole?>
     */
    override fun getUserRoles(userId: Long): List<SysRoleDTO> {
        val result: MutableList<SysRoleDTO> = mutableListOf()

        // 根据用户id查询角色
        val roleList = userRoleService.listByUserId(userId)
        if(CollUtil.isNotEmpty(roleList)) {
            roleList.forEach {
                result.add(BeanUtil.toBean(it, SysRoleDTO::class.java))
            }
        }
        return result
    }

    /**
     * 批量获取用户角色
     * @param ids List<Long>
     * @return Map<Long, List<SysRole?>>
     */
    override fun getUserRoles(userIds: List<Long>): Map<Long, List<SysRoleDTO>> {
        // 批量根据用户id查询角色
        val roleList = userRoleService.listByUserIds(userIds)

        // 处理返回值
        var result: Map<Long, List<SysRoleDTO>> = mutableMapOf()
        if(CollUtil.isNotEmpty(roleList)) {
            // 处理得到 Map<用户id, 用户角色列表>
            result = roleList.filter { it.userId != null }.groupBy { it.userId!! }
        }
        return result
    }

    /**
     * 通过账号查询用户 （演示使用xml查询）
     * @param account String
     * @return User
     */
    override fun getByAccount(account: String): SysUser? {
        try {
            return baseMapper.selectByAccount(account)
        }catch (e: Exception) {
            // 可能查询到多个用户
            throw BusinessException("查询到多个用户")
        }
    }

    /**
     * 加密用户密码
     *
     * @param password String 明文
     * @return String   密文
     */
    override fun encodePassword(password: String): String = BCrypt.hashpw(password)

    /**
     * 比较密码
     *
     * @param inputPwd String 用户输入的密码
     * @param dbPwd String    用户数据库中的密码
     * @return Boolean
     */
    override fun comparePassword(inputPwd: String, dbPwd: String): Boolean = BCrypt.checkpw(inputPwd, dbPwd)

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Cacheable(value = [USER_PERMISSION_KEY], key = "#p0")
    override fun getPermissionList(loginId: Any?, loginType: String?): List<String> {
        loginId ?: let { return listOf() }
        val authorities: List<SysMenu> = roleMenuService.listMenuByUserId(loginId.toString().toLong())
        if(CollUtil.isEmpty(authorities)) {
            return listOf()
        }
        return authorities.mapNotNull { it.authority }.filterNot { it == "" }
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Cacheable(value = [USER_ROLE_KEY], key = "#p0")
    override fun getRoleList(loginId: Any?, loginType: String?): List<String> {
        loginId ?: let { return listOf() }
        val roleList: List<SysRole> = userRoleService.listByUserId(loginId.toString().toLong())
        if (CollUtil.isEmpty(roleList)) {
            return listOf()
        }
        return roleList.mapNotNull { it.code }.filterNot { it == "" }
    }
}
