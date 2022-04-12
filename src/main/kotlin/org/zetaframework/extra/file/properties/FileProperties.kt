package org.zetaframework.extra.file.properties

import cn.hutool.core.lang.Assert
import org.springframework.boot.context.properties.ConfigurationProperties
import org.zetaframework.extra.file.enums.FileStorageType

/**
 * 文件存储配置
 *
 * @author gcc
 */
@ConfigurationProperties(prefix = FileProperties.PREFIX)
class FileProperties(
    /** 文件存储策略，如不配置则默认为上传文件到本地 */
    var storageType: FileStorageType = FileStorageType.LOCAL,

    /** 本地存储配置 */
    var local: Local = Local(),

    /** minio存储配置 */
    var minio: Minio = Minio(),

    /** 阿里云存储配置 */
    var ali: Ali = Ali()

    /** 可以继续扩展，想接入什么oss就仿造着写 */

) {
    companion object {
        const val PREFIX = "zeta.file"
    }


    /**
     * 本地存储
     */
    data class Local(
        /** 根文件夹 */
        var bucket: String? = null,
        /**
         * 本地访问地址+端口号
         * eg: http://127.0.0.1
         * 说明：
         * 本地存储策略的endpoint值，仅用于拼接得到文件的访问地址
         * 不能直接访问该地址获取文件，需要配置nginx才行
         */
        var endpoint: String? = null,
        /** 文件存放路径 */
        var storagePath: String? = null,
    ) {
        /**
         * 获取文件访问URL前缀
         */
        fun getUrlPrefix(): String {
            Assert.notBlank(endpoint, "请配置本地文件访问域名endpoint")
            Assert.notBlank(bucket, "请配置本地文件根目录bucket")

            // 去除endpoint末尾的“/”
            if (endpoint!!.endsWith("/")) {
                endpoint = endpoint!!.substringBeforeLast("/")
            }

            var urlPrefix = "${endpoint}/${bucket}"
            // 如果没有以“/”结尾则添加上去
            if (!urlPrefix.endsWith("/")) {
                urlPrefix += "/"
            }
            return urlPrefix
        }
    }

    /**
     * minio存储
     */
    data class Minio(
        /** 桶 */
        var bucket: String? = null,
        /** minio地址+端口号 eg: https://play.min.io */
        var endpoint: String? = null,
        /** minio用户名 */
        var accessKey: String? = null,
        /** minio密码 */
        var secretKey: String? = null,
    ) {
        /**
         * 获取文件访问URL前缀
         */
        fun getUrlPrefix(): String {
            Assert.notBlank(bucket, "请配置Minio存储桶bucket")
            Assert.notBlank(endpoint, "请配置Minio访问域名endpoint")

            // 去除endpoint末尾的“/”
            if (endpoint!!.endsWith("/")) {
                endpoint = endpoint!!.substringBeforeLast("/")
            }

            var urlPrefix = "${endpoint}/${bucket}"
            // 如果没有以“/”结尾则添加上去
            if (!urlPrefix.endsWith("/")) {
                urlPrefix += "/"
            }
            return urlPrefix
        }
    }

    /**
     * 阿里云存储
     *
     * 说明：
     * 阿里云账号AccessKey拥有所有API的访问权限，风险很高。
     * 强烈建议您创建并使用RAM用户进行API访问或日常运维
     */
    data class Ali(
        /** 桶 */
        var bucket: String? = null,
        /** 阿里云oss访问域名 */
        var endpoint: String? = null,
        /** 阿里云ak */
        var accessKeyId: String? = null,
        /** 阿里云sk */
        var accessKeySecret: String? = null,
    ) {
        /**
         * 获取文件访问URL前缀
         *
         * 说明：
         * URL的格式为https://BucketName.Endpoint/ObjectName
         * 前缀为https://BucketName.Endpoint/
         */
        fun getUrlPrefix(): String {
            Assert.notBlank(bucket, "请配置阿里云oss存储桶bucket")
            Assert.notBlank(endpoint, "请配置阿里云oss访问域名endpoint")

            var urlPrefix = "https://${bucket}.${endpoint}"
            // 如果没有以“/”结尾则添加上去
            if (!urlPrefix.endsWith("/")) {
                urlPrefix += "/"
            }
            return urlPrefix
        }
    }

}
