package com.zeta.system.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zeta.system.model.entity.SysUser
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * 用户 Mapper 接口
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Repository
interface SysUserMapper: BaseMapper<SysUser> {

    /**
     * 通过账号查询用户
     * @param account String
     */
    fun selectByAccount(@Param("account") account: String) : SysUser?

}
