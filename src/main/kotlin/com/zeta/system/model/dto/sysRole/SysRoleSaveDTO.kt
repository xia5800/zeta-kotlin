package com.zeta.system.model.dto.sysRole

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

/**
 * 新增 角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "新增角色")
data class SysRoleSaveDTO(

    /** 角色名 */
    @ApiModelProperty(value = "角色名")
    @get:NotBlank(message = "角色名不能为空")
    var name: String? = null,

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码")
    @get:NotBlank(message = "角色编码不能为空")
    var code: String? = null,

    /** 描述 */
    @ApiModelProperty(value = "描述")
    var describe: String? = null,
)
