package org.zetaframework.extra.file.strategy.impl

import cn.hutool.core.lang.Assert
import io.minio.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.core.utils.JSONUtil
import org.zetaframework.extra.file.enums.FileStorageType
import org.zetaframework.extra.file.model.FileDeleteParam
import org.zetaframework.extra.file.model.FileInfo
import org.zetaframework.extra.file.properties.FileProperties
import java.io.InputStream

/**
 * Minio 文件管理策略实现
 * @author gcc
 */
@Component("MINIO")
class MinioFileStrategyImpl(
    private val fileProperties: FileProperties,
    @Lazy private val minioClient: MinioClient
) : AbstractFileStrategy() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 策略类实现该方法进行文件上传
     *
     * @param file     文件对象
     * @param fileInfo 文件详情
     */
    override fun onUpload(file: MultipartFile, fileInfo: FileInfo) {
        val minio = fileProperties.minio
        // 可以注释掉这个判断，因为配置minio客户端的时候已经判断过minio.bucket是否为空了
        Assert.notBlank(minio.bucket, "请配置Minio存储桶bucket")

        // 文件桶不存在则创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minio.bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minio.bucket).build())
        }

        // 文件上传
        minioClient.putObject(PutObjectArgs.builder()
            .bucket(minio.bucket)
            .contentType(file.contentType)
            .`object`(fileInfo.path)
            .stream(file.inputStream, file.size, PutObjectArgs.MIN_MULTIPART_SIZE.toLong())
            .build())

        // 设置文件详情
        fileInfo.bucket = minio.bucket
        fileInfo.url = "${minio.getUrlPrefix()}${fileInfo.path}"
        fileInfo.storageType = FileStorageType.MINIO.name
    }

    /**
     * 获取文件输入流
     *
     * @param path
     */
    override fun getObject(path: String): InputStream? {
        val minio = fileProperties.minio
        return try {
            // 获取文件
            val getObjectArgs = GetObjectArgs.builder()
                .bucket(minio.bucket)
                .`object`(path)
                .build()
            minioClient.getObject(getObjectArgs)
        } catch (e: Exception) {
            logger.error("minio获取文件异常:", e)
            null
        }
    }

    /**
     * 删除文件
     *
     * @param param 文件删除参数
     */
    override fun delete(param: FileDeleteParam): Boolean {
        val minio = fileProperties.minio
        return try {
            // 删除文件
            val removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(minio.bucket)
                .`object`(param.path)
                .build()
            minioClient.removeObject(removeObjectArgs)
            true
        } catch (e: Exception) {
            logger.error("minio删除文件异常:", e)
            false
        }
    }
}
