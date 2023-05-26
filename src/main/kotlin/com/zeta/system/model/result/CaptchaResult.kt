package com.zeta.system.model.result

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 验证码返回结果
 *
 * @author gcc
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "验证码返回结果")
data class CaptchaResult (

    /**
     * 验证码key。
     * 后台利用该key去redis中查询正确的验证码值
     */
    @Schema(description = "key")
    val key: Long? = null,

    /**
     * 验证码base64数据
     */
    @Schema(description = "图形验证码base64数据")
    val base64: String? = null,

    /**
     * 验证码文本 生产环境不会返回该值
     */
    @Schema(description = "验证码文本")
    val text: String? = null,
)
