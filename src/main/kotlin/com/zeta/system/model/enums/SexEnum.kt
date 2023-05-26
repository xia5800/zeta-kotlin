package com.zeta.system.model.enums

/**
 * 性别枚举
 * @author gcc
 */
enum class SexEnum(
    /** 性别编码 */
    var code: Int,
    /** 性别描述 */
    var msg: String
) {
    /** 未知 */
    UNKNOWN(0, "未知"),
    /** 男 */
    MALE(1, "男"),
    /** 女 */
    FEMALE(2, "女");
}
