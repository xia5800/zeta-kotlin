package com.zeta.system.model.dto.sysRole

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 修改 角色
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "修改角色")
data class SysRoleUpdateDTO(

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    @get:NotNull(message = "id不能为空")
    var id: Long? = null,

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
