package org.zetaframework.extra.crypto

import cn.hutool.crypto.symmetric.AES
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.extra.crypto.properties.AesCryptoProperties
import org.zetaframework.extra.crypto.helper.AESHelper

/**
 * 加密解密配置
 *
 * 说明：
 * 只做了加密工具类的配置，使用方法为注入AESHelper之后进行加密解密操作。
 * 没有做接口入参解密、返回值加密。有需求的可以自行实现
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(AesCryptoProperties::class)
class CryptoConfiguration(private val aesCryptoProperties: AesCryptoProperties) {

    /**
     * 配置AES加密解密类
     */
    @Bean
    fun aes(): AES {
        // 检查key的长度
        return AES(
            aesCryptoProperties.mode,
            aesCryptoProperties.padding,
            aesCryptoProperties.cryptoKey.toByteArray(),
            aesCryptoProperties.cryptoIv.toByteArray()
        )
    }

    /**
     * 配置AES加密解密Helper类
     *
     * @param ase 上面配置好的hutool的AES加密解密类
     */
    @Bean
    fun aesHelper(aes: AES): AESHelper = AESHelper(aes)

}
