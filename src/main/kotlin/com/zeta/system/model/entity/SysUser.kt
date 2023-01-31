package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.StateEntity
import java.time.LocalDate

/**
 * 用户
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "用户")
@TableName(value = "sys_user")
class SysUser: StateEntity<Long, Int>(){

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    var username: String? = null

    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField(value = "account")
    var account: String? = null

    /** 密码 */
    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    var password: String? = null

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    @TableField(value = "email")
    var email: String? = null

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    @TableField(value = "mobile")
    var mobile: String? = null

    /** 性别 */
    @ApiModelProperty(value = "性别 0未知 1男 2女", example = "0", allowableValues = "0,1,2")
    @TableField(value = "sex")
    var sex: Int? = null

    /** 头像 */
    @ApiModelProperty(value = "头像")
    @TableField(value = "avatar")
    var avatar: String? = null

    /** 生日 */
    @ApiModelProperty(value = "生日")
    @TableField(value = "birthday")
    var birthday: LocalDate? = null

    /** 是否内置 true or false */
    @ApiModelProperty(value = "是否内置 true or false")
    @TableField(value = "readonly_")
    var readonly: Boolean? = null

    /** 是否删除 true or false  */
    @ApiModelProperty(value = "是否删除 true or false")
    @TableLogic
    var deleted: Boolean? = null

    /** 用户角色 */
    @ApiModelProperty(value = "用户角色")
    @TableField(exist = false)
    var roles: List<SysRoleDTO>? = null

    override fun toString(): String {
        return "SysUser(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, username=$username, account=$account, password=$password, email=$email, mobile=$mobile, sex=$sex, avatar=$avatar, birthday=$birthday, readonly=$readonly, deleted=$deleted)"
    }

}
