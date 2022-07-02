package org.zetaframework.core.saToken

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * 加密配置
 * @author gcc
 */
@Deprecated(message = "系统中已改为使用SaToken提供的BCrypt方法来进行加密解密，将在下个版本中移除该配置")
@Configuration
class SecurityCryptoConfigure {

    /**
     * 配置bCryptPasswordEncoder用来加密用户密码
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

}
