package com.zeta.system.model.enums

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 性别枚举
 * @author gcc
 */
@Schema(description = "性别 枚举")
enum class SexEnum(
    @Schema(description = "性别编码")
    var code: Int,
    @Schema(description = "性别描述")
    var msg: String
) {
    /** 未知 */
    UNKNOWN(0, "未知"),
    /** 男 */
    MALE(1, "男"),
    /** 女 */
    FEMALE(2, "女");
}
