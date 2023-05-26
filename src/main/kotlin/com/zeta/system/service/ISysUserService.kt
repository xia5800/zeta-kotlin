package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import com.zeta.system.model.dto.sysUser.SysUserDTO
import com.zeta.system.model.dto.sysUser.SysUserSaveDTO
import com.zeta.system.model.dto.sysUser.SysUserUpdateDTO
import com.zeta.system.model.entity.SysUser
import com.zeta.system.model.param.SysUserQueryParam
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult

/**
 * 用户 服务类
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
interface ISysUserService : IService<SysUser> {

    /**
     * 自定义分页查询
     *
     * @param param 分页查询参数
     * @return PageResult<[SysUserDTO]>
     */
    fun customPage(param: PageParam<SysUserQueryParam>): PageResult<SysUserDTO>

    /**
     * 添加用户
     *
     * @param saveDTO 新增用户参数
     * @return
     */
    fun saveUser(saveDTO: SysUserSaveDTO): Boolean

    /**
     * 修改用户
     *
     * @param updateDTO 修改用户参数
     * @return
     */
    fun updateUser(updateDTO: SysUserUpdateDTO): Boolean

    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return List<[SysRoleDTO]> 角色详情列表
     */
    fun getUserRoles(userId: Long): List<SysRoleDTO>

    /**
     * 批量获取用户角色
     *
     * @param userIds 用户id列表
     * @return Map<Long, List<[SysRoleDTO]>>
     */
    fun getUserRoles(userIds: List<Long>): Map<Long, List<SysRoleDTO>>

    /**
     * 通过账号查询用户 （演示使用xml查询）
     *
     * @param account 账号
     * @return [SysUser] 用户
     */
    fun getByAccount(account: String): SysUser?

    /**
     * 加密用户密码
     *
     * @param password 明文
     * @return 密文
     */
    fun encodePassword(password: String): String


    /**
     * 比较密码
     *
     * @param inputPwd 用户输入的密码
     * @param dbPwd 用户数据库中的密码
     * @return
     */
    fun comparePassword(inputPwd: String, dbPwd: String): Boolean

    /**
     * 批量导入用户
     *
     * @param userList 待导入的用户列表
     * @return
     */
    fun batchImportUser(userList: List<SysUser>): Boolean

}
