package com.zeta.system.model.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 角色 查询参数
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@ApiModel(description = "角色查询参数")
data class SysRoleQueryParam (

    /** 角色名 */
    @ApiModelProperty("角色名")
    var name: String? = null,

    /** 角色编码 */
    @ApiModelProperty("角色编码")
    var code: String? = null,

    /** 描述 */
    @ApiModelProperty("描述")
    var describe: String? = null,
)
