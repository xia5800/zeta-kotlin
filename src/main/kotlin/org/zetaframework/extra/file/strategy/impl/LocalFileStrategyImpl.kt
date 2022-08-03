package org.zetaframework.extra.file.strategy.impl

import cn.hutool.core.lang.Assert
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.extra.file.enums.FileStorageType
import org.zetaframework.extra.file.model.FileDeleteParam
import org.zetaframework.extra.file.model.FileInfo
import org.zetaframework.extra.file.properties.FileProperties
import org.zetaframework.extra.file.util.FileUtil
import java.io.InputStream
import java.nio.file.Paths

/**
 * 本地 文件管理策略实现
 * @author gcc
 */
@Component("LOCAL")
class LocalFileStrategyImpl(
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
        val local = fileProperties.local
        Assert.notBlank(local.bucket, "请配置本地文件根目录bucket")
        Assert.notBlank(local.endpoint, "请配置本地文件访问域名endpoint")
        Assert.notBlank(local.storagePath, "请配置本地文件存放路径storagePath")

        // 获取文件在服务器上的绝对路径
        val absolutePath = Paths.get(local.storagePath!!, local.bucket, fileInfo.path).toString()

        // 存储文件
        FileUtil.writeFile(file, absolutePath)
        logger.info("上传本地文件成功")

        // 设置文件详情
        fileInfo.bucket = local.bucket
        fileInfo.url = "${local.getUrlPrefix()}${fileInfo.path}"
        fileInfo.storageType = FileStorageType.LOCAL.name
    }

    /**
     * 获取文件输入流
     *
     * @param path
     */
    override fun getObject(path: String): InputStream? {
        // 获取文件在服务器上的绝对路径
        val local = fileProperties.local
        val absolutePath = Paths.get(local.storagePath!!, local.bucket, path).toString()

        return try {
            // 获取本地文件
            FileUtil.getFile(absolutePath)
        } catch (e: Exception) {
            logger.error("获取本地文件异常：", e)
            null
        }
    }

    /**
     * 删除文件
     *
     * @param param 文件删除参数
     */
    override fun delete(param: FileDeleteParam): Boolean {
        // 获取文件在服务器上的绝对路径
        val local = fileProperties.local
        val absolutePath = Paths.get(local.storagePath!!, local.bucket, param.path).toString()

        return try {
            // 删除本地文件
            FileUtil.deleteFile(absolutePath)
        } catch (e: Exception) {
            logger.error("删除本地文件异常：", e)
            false
        }
    }
}
