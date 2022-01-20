package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.dto.sysUser.SysUserSaveDTO
import com.zeta.system.model.dto.sysUser.SysUserUpdateDTO
import com.zeta.system.model.entity.SysUser

/**
 * 用户 服务类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
interface ISysUserService : IService<SysUser> {

    /**
     * 添加用户
     * @param saveDTO SysUserSaveDTO
     * @return Boolean
     */
    fun saveUser(saveDTO: SysUserSaveDTO): Boolean

    /**
     * 修改用户
     * @param updateDTO SysUserUpdateDTO
     * @return Boolean
     */
    fun updateUser(updateDTO: SysUserUpdateDTO): Boolean

    /**
     * 获取用户角色
     *
     * @param userId Long
     * @return List<SysRole?>
     */
    fun getUserRoles(userId: Long): List<SysRoleDTO>

    /**
     * 批量获取用户角色
     * @param userIds List<Long>
     * @return Map<Long, List<SysRole?>>
     */
    fun getUserRoles(userIds: List<Long>): Map<Long, List<SysRoleDTO>>

    /**
     * 通过账号查询用户 （演示使用xml查询）
     * @param account String
     * @return User
     */
    fun getByAccount(account: String): SysUser?

    /**
     * 加密用户密码
     *
     * @param password String 明文
     * @return String   密文
     */
    fun encodePassword(password: String): String


    /**
     * 比较密码
     *
     * @param inputPwd String 用户输入的密码
     * @param dbPwd String    用户数据库中的密码
     * @return Boolean
     */
    fun comparePassword(inputPwd: String, dbPwd: String): Boolean

}
