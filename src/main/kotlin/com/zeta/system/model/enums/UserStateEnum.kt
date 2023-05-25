package com.zeta.system.model.enums

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 用户状态 枚举
 * @author gcc
 */
@Schema(description = "用户状态 枚举")
enum class UserStateEnum(
    @Schema(description = "状态码")
    var code: Int,
    @Schema(description = "状态描述")
    var msg: String)
{
    /** 禁用 */
    FORBIDDEN(0, "禁用"),
    /** 正常 */
    NORMAL(1, "正常");


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
