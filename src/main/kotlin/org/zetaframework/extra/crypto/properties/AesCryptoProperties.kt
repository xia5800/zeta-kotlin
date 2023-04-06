package org.zetaframework.extra.crypto.properties

import cn.hutool.crypto.Mode
import cn.hutool.crypto.Padding
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Aes加密配置参数
 * @author gcc
 */
@ConfigurationProperties(prefix = "zeta.crypto.aes")
class AesCryptoProperties(
    /** AES加密秘钥 长度规定：16、24、32 */
    var cryptoKey: String = "",

    /** AES加密盐 */
    var cryptoIv: String = "",

    /** 加密模式 */
    var mode: Mode = Mode.ECB,

    /**
     * 填充方式
     *
     * 说明：
     * JDK的PKCS5就是按PKCS7实现的，直接用 PKCS5Padding 即可
     */
    var padding: Padding = Padding.PKCS5Padding,
)

