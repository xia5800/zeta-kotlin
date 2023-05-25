package org.zetaframework.core.saToken.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.zetaframework.core.saToken.enums.TokenTypeEnum

/**
 * token配置参数
 * @author gcc
 */
@ConfigurationProperties(prefix = "zeta.token")
class TokenProperties(
    /** jwt签名key */
    var signerKey: String = "zeta-kotlin",

    /** 请求头中token参数的名字 默认：Authorization */
    var tokenName: String = "Authorization",

    /** token类型 */
    var type: TokenTypeEnum = TokenTypeEnum.DEFAULT,

    /** jwt签名前缀 例如填写 Bearer 实际传参 token: Bearer xxxxxx */
    var prefix: String? = "",

    /** token过期时间 单位：秒, -1代表永不过期 */
    var expireTime: Long = 7200,

    /** token自动续期，每次操作为token续期指定时间 */
    var renew: Boolean = false
)

