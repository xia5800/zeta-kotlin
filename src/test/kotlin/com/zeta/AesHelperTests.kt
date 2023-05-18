package com.zeta

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.zetaframework.extra.crypto.helper.AESHelper

/**
 * Aes加密解密Helper测试类
 *
 * @author gcc
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AesHelperTests(
    private val aesHelper: AESHelper
) {

    /**
     * 登录密码AES加密
     *
     * 说明：
     * 因为前端登录时将用户输入的明文密码用aes算法加密了一遍。
     * 所以本项目登录时需要将密文解密成明文，再去和数据中的密码进行比较
     */
    @Test
    fun cryptoPassword() {
        // 加密
        val result = aesHelper.encryptBase64("admin")
        println(result)
        assert(result == "dDEWFk6fJKwZ55cL3zVUsQ==")

        // 解密
        val password = aesHelper.decryptStr(result)
        assert(password == "admin")
    }

}
