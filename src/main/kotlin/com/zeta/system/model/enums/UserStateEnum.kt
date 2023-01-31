package com.zeta.system.model.enums

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 用户状态 枚举
 * @author gcc
 */
@ApiModel(description = "用户状态 枚举")
enum class UserStateEnum(
    @ApiModelProperty("状态码")
    var code: Int,
    @ApiModelProperty("状态描述")
    var msg: String)
{
    /** 正常 */
    NORMAL(0, "正常"),
    /** 禁用 */
    FORBIDDEN(1, "禁用");


    companion object {
        /**
         * 获取UserStateEnum类所有状态的状态码
         * @return MutableList<Int>
         */
        fun getAllCode(): MutableList<Int> {
            return mutableListOf(
                NORMAL.code, FORBIDDEN.code
            )
        }
    }
}
