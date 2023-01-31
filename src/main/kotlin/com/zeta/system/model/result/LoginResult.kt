package com.zeta.system.model.result

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 登录返回结果
 * @author gcc
 */
@ApiModel(description = "登录返回结果")
data class LoginResult(

    /** token名称 */
    @ApiModelProperty(value = "token名称")
    val tokenName: String? = null,

    /** token值 */
    @ApiModelProperty(value = "token值")
    val token: String? = null
)
