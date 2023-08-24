package com.zeta.system.service.impl

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpInterface
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.collection.CollUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysUserMapper
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.dto.sysUser.SysUserDTO
import com.zeta.system.model.dto.sysUser.SysUserSaveDTO
import com.zeta.system.model.dto.sysUser.SysUserUpdateDTO
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.entity.SysRole
import com.zeta.system.model.entity.SysUser
import com.zeta.system.model.enums.UserStateEnum
import com.zeta.system.model.param.SysUserQueryParam
import com.zeta.system.service.ISysRoleMenuService
import com.zeta.system.service.ISysUserRoleService
import com.zeta.system.service.ISysUserService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult
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
     * 自定义分页查询
     *
     * @param param 分页查询参数
     * @return PageResult<SysUserDTO>
     */
    override fun customPage(param: PageParam<SysUserQueryParam>): PageResult<SysUserDTO> {
        // 构造分页page
        var page = param.buildPage<SysUser>()

        // 构造查询条件
        val model = param.model ?: SysUserQueryParam()
        val entity = BeanUtil.toBean(model, SysUser::class.java)

        // 分页查询
        page = this.page(page, KtQueryWrapper(entity))

        // 批量获取用户角色 Map<用户id, 用户角色列表>
        val userIds = page.records.filterNotNull().map { it.id!! }
        val userRoleMap: Map<Long, List<SysRoleDTO>> = if (userIds.isNotEmpty()) {
            this.getUserRoles(userIds)
        } else mutableMapOf()

        // 处理返回结果
        val result = page.records.map { user ->
            // 设置用户角色
            user.roles = userRoleMap.getOrDefault(user.id, mutableListOf())
            // Entity -> EntityDTO
            BeanUtil.toBean(user, SysUserDTO::class.java)
        }

        return PageResult(result, page.total)
    }

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
        if (!this.save(user)) {
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
        if (!this.updateById(user)) {
            throw BusinessException("修改用户失败")
        }

        // 删除并重新关联角色
        return userRoleService.saveUserRole(user.id!!, updateDTO.roleIds)
    }

    /**
     * 获取用户角色
     *
     * @param userId Long
     * @return List<SysRole?>
     */
    override fun getUserRoles(userId: Long): List<SysRoleDTO> {
        // 根据用户id查询角色
        val roleList: List<SysRole> = userRoleService.listByUserId(userId)
        if (roleList.isEmpty()) return emptyList()

        // List<Entity> -> List<EntityDTO>
        return roleList.map { BeanUtil.toBean(it, SysRoleDTO::class.java) }
    }

    /**
     * 批量获取用户角色
     * @param userIds List<Long>
     * @return Map<Long, List<SysRole?>>
     */
    override fun getUserRoles(userIds: List<Long>): Map<Long, List<SysRoleDTO>> {
        // 批量根据用户id查询角色
        val roleList = userRoleService.listByUserIds(userIds)
        if (roleList.isEmpty()) return emptyMap()

        // 处理返回值, 得到 Map<用户id, 用户角色列表>
        return roleList.filter { it.userId != null }.groupBy { it.userId!! }
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
     * 批量导入用户
     *
     * @param userList 待导入的用户列表
     * @return Boolean
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun batchImportUser(userList: List<SysUser>): Boolean {
        // 保存用户
        if (!this.saveBatch(userList)) {
            throw BusinessException("新增用户失败")
        }

        try {
            // 筛选出有角色的用户
            userList.filterNot { it.roles.isNullOrEmpty() }.forEach { user ->
                // 删除并重新关联角色
                val roleIds: List<Long> = user.roles!!.mapNotNull { it.id }
                userRoleService.saveUserRole(user.id!!, roleIds)
            }
        } catch (e: Exception) {
            throw BusinessException("关联用户角色失败")
        }

        return true
    }

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
        if (CollUtil.isEmpty(authorities)) {
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
