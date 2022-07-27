package org.zetaframework.extra.file.util

import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import cn.hutool.core.io.FileTypeUtil
import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.IORuntimeException
import cn.hutool.core.lang.UUID
import cn.hutool.core.util.StrUtil
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream

/**
 * 文件工具类
 *
 * @author gcc
 */
object FileUtil {

    /**
     * 获取唯一文件名
     *
     * 说明：
     * 利用uuid生成一个唯一的文件名
     * @param file
     */
    fun getUniqueFileName(file: MultipartFile): String {
        val simpleUUID = UUID.randomUUID(false).toString(true)

        // 获取文件后缀
        var suffix = getSuffix(file)
        if (suffix.isNotBlank()) {
            suffix = ".$suffix"
        }
        return "${simpleUUID}${suffix}"
    }

    /**
     * 获取文件名后缀
     *
     * 说明：
     * 1.不存在后缀则返回空字符串
     * 2.返回的后缀不带“.”
     * @param file MultipartFile
     */
    fun getSuffix(file: MultipartFile): String {
        return FileUtil.getSuffix(file.originalFilename) ?: ""
    }


    /**
     * 生成文件路径
     * 规则： bizType/年/月/日/uniqueFileName
     *
     * @param bizType        业务类型
     * @param uniqueFileName 唯一文件名
     */
    fun generatePath(bizType: String?, uniqueFileName: String): String {
        val dateFormat = DateUtil.format(DateTime(), "yyyy/MM/dd")
        val module = if (StrUtil.isNotBlank(bizType)) {
            "${bizType}/"
        } else {
            ""
        }
        return "${module}${dateFormat}/${uniqueFileName}"
    }

    /**
     * 根据文件流的头部信息获得文件类型
     *
     * @param file MultipartFile
     */
    fun getFileType(file: MultipartFile): String {
        return try {
            FileTypeUtil.getType(file.inputStream, file.originalFilename)
        } catch (e: IORuntimeException) {
            ""
        }
    }


    /**
     * 存储文件
     *
     * @param file MultipartFile
     * @param absolutePath web服务器存放文件的绝对路径
     */
    fun writeFile(file: MultipartFile, absolutePath: String) {
        FileUtil.writeBytes(file.bytes, FileUtil.file(absolutePath))
    }


    /**
     * 删除文件
     *
     * 注意：
     * 1.删除文件夹时不会判断文件夹是否为空
     * 2.如果文件不存在或已被删除 返回true
     * @param absolutePath web服务器存放文件的绝对路径
     */
    fun deleteFile(absolutePath: String): Boolean {
        return FileUtil.del(absolutePath)
    }

    /**
     * 获取本地文件
     *
     * @param absolutePath web服务器存放文件的绝对路径
     */
    fun getFile(absolutePath: String): BufferedInputStream {
        return FileUtil.getInputStream(absolutePath)
    }
}
