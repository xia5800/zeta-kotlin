package org.zetaframework.base.param

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 修改状态参数
 *
 * @author gcc
 */
@Schema(description = "修改状态参数")
data class UpdateStateParam<T, U>(
    /** id */
    @Schema(description = "id", required = true)
    var id : T? = null,

    /** 状态 */
    @Schema(description = "state", required = true)
    var state : U? = null
)
