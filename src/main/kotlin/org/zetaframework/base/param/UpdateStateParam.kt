package org.zetaframework.base.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 修改状态参数
 *
 * @author gcc
 */
@ApiModel(description = "修改状态参数")
data class UpdateStateParam<T, U>(
    /** id */
    @ApiModelProperty(value = "id")
    var id : T? = null,

    /** 状态 */
    @ApiModelProperty(value = "state")
    var state : U? = null
)
