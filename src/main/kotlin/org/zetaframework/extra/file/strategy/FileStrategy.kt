package org.zetaframework.extra.file.strategy

import org.springframework.web.multipart.MultipartFile
import org.zetaframework.extra.file.model.FileDeleteParam
import org.zetaframework.extra.file.model.FileInfo
import java.io.InputStream

/**
 * 文件管理策略
 *
 * @author gcc
 */
interface FileStrategy {

    /**
     * 上传文件
     *
     * @param file MultipartFile 文件对象
     * @param bizType 业务类型 影响文件相对路径和文件的访问地址
     * @return FileInfo 上传文件的详细信息
     */
    fun upload(file: MultipartFile, bizType: String? = null): FileInfo


    /**
     * 删除文件
     *
     * @param param 文件删除参数
     */
    fun delete(param: FileDeleteParam): Boolean {
        return false
    }

    /**
     * 获取文件输入流
     *
     * @param path
     */
    fun getObject(path: String): InputStream? {
        return null
    }
}
