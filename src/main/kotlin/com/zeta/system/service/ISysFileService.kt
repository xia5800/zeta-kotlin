package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.entity.SysFile
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.multipart.MultipartFile

/**
 * <p>
 * 系统文件 服务类
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-11 11:18:44
 */
interface ISysFileService: IService<SysFile> {

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param bizType 业务类型 例如：order、user_avatar等 会影响文件url的值
     */
    fun upload(file: MultipartFile, bizType: String?): SysFile

    /**
     * 下载文件
     */
    fun download(id: Long, response: HttpServletResponse)

    /**
     * 删除文件
     *
     * @param id
     */
    fun delete(id: Long): Boolean

    /**
     * 批量删除文件
     *
     * @param ids
     */
    fun batchDelete(ids: MutableList<Long>): Boolean
}
