package com.zeta.system.model.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.zeta.system.model.dto.sysRole.SysRoleDTO
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.zetaframework.base.entity.StateEntity
import java.time.LocalDate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

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
    @ApiModelProperty(value = "用户名", required = true)
    @get:NotBlank(message = "用户名不能为空")
    @get:Size(max = 32, message = "用户名长度不能大于32")
    @TableField(value = "username")
    var username: String? = null

    /** 账号 */
    @ApiModelProperty(value = "账号", required = true)
    @get:NotBlank(message = "账号不能为空")
    @get:Size(max = 32, message = "账号长度不能大于64")
    @TableField(value = "account")
    var account: String? = null

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true)
    @get:NotBlank(message = "密码不能为空")
    @get:Size(max = 32, message = "密码长度不能大于64")
    @TableField(value = "password")
    var password: String? = null

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", required = false)
    @TableField(value = "email")
    var email: String? = null

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = false)
    @TableField(value = "mobile")
    var mobile: String? = null

    /** 性别 */
    @ApiModelProperty(value = "性别 0未知 1男 2女", example = "0", allowableValues = "0,1,2", required = true)
    @get:NotNull(message = "性别不能为空")
    @TableField(value = "sex")
    var sex: Int? = null

    /** 头像 */
    @ApiModelProperty(value = "头像", required = false)
    @TableField(value = "avatar")
    var avatar: String? = null

    /** 生日 */
    @ApiModelProperty(value = "生日", required = false)
    @TableField(value = "birthday")
    var birthday: LocalDate? = null

    /** 是否内置 0否 1是 */
    @ApiModelProperty(value = "是否内置 0否 1是", required = false)
    @TableField(value = "readonly_")
    var readonly: Boolean? = null

    /** 逻辑删除字段 */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除字段", hidden = true, required = true)
    @TableLogic
    var deleted: Boolean? = null

    /** 用户角色 */
    @ApiModelProperty(value = "用户角色", required = false)
    @TableField(exist = false)
    var roles: List<SysRoleDTO>? = null

    override fun toString(): String {
        return "SysUser(id=$id, createTime=$createTime, createdBy=$createdBy, updateTime=$updateTime, updatedBy=$updatedBy, username=$username, account=$account, password=$password, email=$email, mobile=$mobile, sex=$sex, avatar=$avatar, birthday=$birthday, readonly=$readonly, deleted=$deleted)"
    }

}
