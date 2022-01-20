package org.zetaframework.core.saToken

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * 加密配置
 * @author gcc
 */
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
