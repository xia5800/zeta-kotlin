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

    /** token类型 */
    var type: TokenTypeEnum = TokenTypeEnum.STYLE,

    /** jwt签名前缀 例如填写 Bearer 实际传参 token: Bearer xxxxxx */
    var prefix: String? = ""
)

