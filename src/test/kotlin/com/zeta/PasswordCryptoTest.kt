package com.zeta

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * 密码加密测试
 * @author gcc
 */
class PasswordCryptoTest {

    /**
     * 生成加密后的密码
     */
    @Test
    fun cryptoPassword() {
        println(BCryptPasswordEncoder().encode("zetaAdmin"))
    }

    /**
     * 比较密码
     */
    @Test
    fun comparePassword() {
        println(
            BCryptPasswordEncoder().matches(
                "123456",
                "\$2a\$10\$dTjrbCN.0MZm.iX7aiDzSOW2PKMGAXC8z8p9Y5NU96V6ykW.XkvKW"
            )
        )
    }

}
