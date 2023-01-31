package com.zeta.system.model.enums

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 性别枚举
 * @author gcc
 */
@ApiModel(description = "性别 枚举")
enum class SexEnum(
    @ApiModelProperty(value = "性别编码")
    var code: Int,
    @ApiModelProperty(value = "性别描述")
    var msg: String
) {
    /** 未知 */
    UNKNOWN(0, "未知"),
    /** 男 */
    MALE(1, "男"),
    /** 女 */
    FEMALE(2, "女");
}
