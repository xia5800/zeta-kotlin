package org.zetaframework.extra.file

import cn.hutool.core.lang.Assert
import io.minio.MinioClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.extra.file.enums.FileStorageType
import org.zetaframework.extra.file.properties.FileProperties
import org.zetaframework.extra.file.strategy.FileContext
import org.zetaframework.extra.file.strategy.FileStrategy

/**
 * 文件管理配置
 *
 * 说明：
 * 参考[lamp-file](https://github.com/zuihou/lamp-boot/tree/master/lamp-file)实现
 *
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(FileProperties::class)
class FileConfiguration(
    private val strategyMap: Map<String, FileStrategy>,
    private val fileProperties: FileProperties
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * # 配置使用策略的客户端
     *
     * 说明：
     * 和lamp-file动态策略方式不同。本项目根据项目配置文件中zeta.file.storageType的值来设置具体的策略。
     * 也就是说，不会像lamp-file那样根据用户传入的策略值来使用具体的策略上传文件。
     * 因为作者认为一般的项目基本不会在多个OSS之间来回切换着上传文件，基本上都是选定一种OSS就一直用着。
     *
     * 说说这种方式的缺点吧：
     * 假如你项目之前使用的是阿里云，然后业务突然要求使用七牛云或者其它什么乱七八糟云
     * 虽然修改配置可以切换使用指定的上传策略，但是因为策略实现变成了七牛云，这会导致删除文件功能会受到一定的影响
     * 不能删除原来阿里云的文件了，所以开发需要对这种情况做一下预防。
     * 或者直接对删除文件接口做一下修改，像lamp-file那样可以删除不同oss的文件。
     * 当然你不在意文件是否删除也是可以的。数据库里面对应的数据是肯定会删除的
     */
    @Bean
    fun fileContext(): FileContext {
        // 获取项目配置的策略
        val strategyType = fileProperties.storageType
        when (strategyType) {
            FileStorageType.LOCAL -> {
                logger.info("检测到oss策略为：LOCAL")
            }
            FileStorageType.MINIO -> {
                logger.info("检测到oss策略为：MINIO")
            }
            FileStorageType.ALI_OSS -> {
                logger.info("检测到oss策略为：ALI_OSS")
            }
            else -> {
                logger.info("检测到oss策略为: 自定义")
            }
        }
        val fileStrategy: FileStrategy? = strategyMap[strategyType.name]
        Assert.notNull(fileStrategy, "请配置正确的文件存储类型")
        return FileContext(fileStrategy!!)
    }


    /**
     * 配置minio客户端
     *
     * 说明：
     * 只有oss策略为MINIO的时候才配置
     */
    @Bean
    @ConditionalOnExpression(value = "#{'MINIO'.equals(environment['zeta.file.storageType'])}")
    fun minioClient(): MinioClient {
        val minio = fileProperties.minio
        logger.info("正在初始化minio客户端...")
        Assert.notBlank(minio.bucket, "请配置Minio存储桶bucket")
        Assert.notBlank(minio.endpoint, "请配置Minio访问域名endpoint")
        Assert.notBlank(minio.accessKey, "请配置Minio用户名")
        Assert.notBlank(minio.secretKey, "请配置Minio密码")

        return MinioClient.Builder()
            .endpoint(minio.endpoint)
            .credentials(minio.accessKey, minio.secretKey)
            .build()
    }
}
