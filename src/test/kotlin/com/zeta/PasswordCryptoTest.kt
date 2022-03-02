package com.zeta

import cn.dev33.satoken.secure.BCrypt
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
        // 使用 spring security 提供的 BCryptPasswordEncoder 加密密码
        println(BCryptPasswordEncoder().encode("zetaAdmin"))
        // 使用 saToken提供的 BCrypt 加密密码
        println(BCrypt.hashpw("zetaAdmin"))
    }

    /**
     * 比较密码
     */
    @Test
    fun comparePassword() {
        // 使用 spring security 提供的 BCryptPasswordEncoder 解密密码
        println(
            BCryptPasswordEncoder().matches(
                "zetaAdmin",
                "\$2a\$10\$1aXDYeBpVRaTPr7Fd2tY1.u43r.Uj5t1eyQ5iIySryuht2lv4Oauy"
            )
        )

        // 使用 saToken提供的 BCrypt 解密密码
        println(BCrypt.checkpw(
            "zetaAdmin",
            "\$2a\$10\$1aXDYeBpVRaTPr7Fd2tY1.u43r.Uj5t1eyQ5iIySryuht2lv4Oauy"
        ))
    }

}
