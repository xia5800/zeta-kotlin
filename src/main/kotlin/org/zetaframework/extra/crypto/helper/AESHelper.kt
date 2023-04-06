package org.zetaframework.extra.crypto.helper

import cn.hutool.core.codec.Base64
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.HexUtil
import cn.hutool.core.util.RandomUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.crypto.symmetric.AES
import org.zetaframework.extra.crypto.enums.KeyLength
import java.util.*

/**
 * Aes加密解密Helper类
 *
 * 说明：
 * 1.JDK 的 PKCS5 就是按 PKCS7 实现的，直接用 PKCS5Padding 即可
 * 2.为什么叫Helper类，因为util类应该是只具有静态方法的类，不需要new出来也无需注入bean。所以用Helper命名比较好
 *
 * @author gcc
 */
class AESHelper(private val aes: AES) {

    /**
     * 加密, 返回bas64字符串
     *
     * @param data 需要加密的数据
     */
    fun encryptBase64(data: String): String {
        return Base64.encode(encrypt(data))
    }

    /**
     * 加密，返回16进制字符串
     *
     * @param data 需要加密的数据
     */
    fun encryptHex(data: String): String {
        return HexUtil.encodeHexStr(encrypt(data))
    }

    /**
     * 加密，返回字节数组
     *
     * @param data 需要加密的数据
     */
    fun encrypt(data: String): ByteArray {
        return aes.encrypt(data)
    }


    /**
     * 解密, 返回String
     *
     * @param data 需要解密的数据
     */
    fun decryptStr(data: String): String {
        return StrUtil.str(decrypt(data), CharsetUtil.CHARSET_UTF_8)
    }


    /**
     * 解密，返回字节数组
     *
     * @param data 需要解密的数据
     */
    fun decrypt(data: String): ByteArray {
        return aes.decrypt(data)
    }


    /**
     * 生成加密key
     *
     * @param length 长度。可选：16、24、32 默认：16
     */
    fun generateEncryptKey(length: KeyLength = KeyLength.L_16): String {
        val baseString = RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.uppercase(Locale.getDefault())
        return RandomUtil.randomString(baseString, length.value)
    }
}
