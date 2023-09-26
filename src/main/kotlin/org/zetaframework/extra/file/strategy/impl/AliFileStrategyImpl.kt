package org.zetaframework.extra.file.strategy.impl

import cn.hutool.core.lang.Assert
import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.model.PutObjectRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.core.utils.JSONUtil
import org.zetaframework.extra.file.enums.FileStorageType
import org.zetaframework.extra.file.model.FileDeleteParam
import org.zetaframework.extra.file.model.FileInfo
import org.zetaframework.extra.file.properties.FileProperties
import java.io.InputStream

/**
 * 阿里云OSS 文件管理策略实现
 *
 * 参考文档：[阿里云对象存储 OSS](https://help.aliyun.com/document_detail/52834.html)
 * @author gcc
 */
@Component("ALI_OSS")
class AliFileStrategyImpl(
    private val fileProperties: FileProperties
) : AbstractFileStrategy() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 策略类实现该方法进行文件上传
     *
     * @param file     文件对象
     * @param fileInfo 文件详情
     */
    override fun onUpload(file: MultipartFile, fileInfo: FileInfo) {
        val ali = fileProperties.ali
        Assert.notBlank(ali.bucket, "请配置阿里云oss存储桶bucket")
        Assert.notBlank(ali.endpoint, "请配置阿里云oss访问域名endpoint")
        Assert.notBlank(ali.accessKeyId, "请配置阿里云accessKeyId")
        Assert.notBlank(ali.accessKeySecret, "请配置阿里云accessKeySecret")

        // 创建阿里oss客户端
        val ossClient = OSSClientBuilder().build(ali.endpoint, ali.accessKeyId, ali.accessKeySecret)

        // 文件桶不存在则创建
        if (!ossClient.doesBucketExist(ali.bucket)) {
            ossClient.createBucket(ali.bucket)
        }

        // 文件上传
        val request = PutObjectRequest(ali.bucket, fileInfo.path, file.inputStream)
        val response = ossClient.putObject(request)
        logger.info("阿里云oss文件上传结果：${JSONUtil.toJsonStr(response)}")

        ossClient.shutdown()

        // 设置文件详情
        fileInfo.bucket = ali.bucket
        fileInfo.url = "${ali.getUrlPrefix()}${fileInfo.path}"
        fileInfo.storageType = FileStorageType.ALI_OSS.name
    }


    /**
     * 获取文件输入流
     *
     * @param path
     */
    override fun getObject(path: String): InputStream? {
        val ali = fileProperties.ali

        val ossClient = OSSClientBuilder().build(ali.endpoint, ali.accessKeyId, ali.accessKeySecret)
        return try {
            // 获取文件
            val ossObject = ossClient.getObject(ali.bucket, path)

            ossObject.objectContent
        } catch (e: Exception) {
            ossClient.shutdown()
            logger.error("阿里云oss获取文件异常：", e)
            null
        }
    }

    /**
     * 删除文件
     *
     * @param param 文件删除参数
     */
    override fun delete(param: FileDeleteParam): Boolean {
        val ali = fileProperties.ali
        return try {
            // 删除文件
            val ossClient = OSSClientBuilder().build(ali.endpoint, ali.accessKeyId, ali.accessKeySecret)
            ossClient.deleteObject(ali.bucket, param.path)
            ossClient.shutdown()
            true
        } catch (e: Exception) {
            logger.error("阿里云oss删除文件异常：", e)
            false
        }
    }
}
