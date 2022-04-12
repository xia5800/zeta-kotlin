package org.zetaframework.extra.file.strategy.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.core.exception.BusinessException
import org.zetaframework.extra.file.model.FileInfo
import org.zetaframework.extra.file.strategy.FileStrategy
import org.zetaframework.extra.file.util.FileUtil

/**
 * 文件抽象策略 处理类
 *
 * 说明：
 * 为什么要有这么一层抽象
 * 因为各个具体的策略实现类都有一些重复的操作。所以将这些重复的操作提取出来
 * @author gcc
 */
abstract class AbstractFileStrategy : FileStrategy {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 上传文件
     *
     * @param file MultipartFile 文件对象
     * @param bizType 业务类型 影响文件相对路径和文件的访问地址
     * @return FileInfo 上传文件的详细信息
     */
    override fun upload(file: MultipartFile, bizType: String?): FileInfo {
        // 处理文件存放路径
        val uniqueFileName = FileUtil.getUniqueFileName(file)
        val path = FileUtil.generatePath(bizType, uniqueFileName)

        // 上传文件的信息。
        val fileInfo = FileInfo().apply {
            this.originalFileName = file.originalFilename
            this.contentType = file.contentType
            this.size = file.size
            this.suffix = FileUtil.getSuffix(file)
            this.fileType = FileUtil.getFileType(file)
            this.bizType = bizType
            this.uniqueFileName = uniqueFileName
            this.path = path
        }

        try {
            // 交给对应的策略去实现文件上传
            onUpload(file, fileInfo)
        } catch (e: Exception) {
            logger.error("文件上传失败:", e)
            throw BusinessException("文件上传失败")
        }

        return fileInfo
    }


    /**
     * 策略类实现该方法进行文件上传
     *
     * @param file     文件对象
     * @param fileInfo 文件详情
     */
    open fun onUpload(file: MultipartFile, fileInfo: FileInfo) {}
}
