package com.zeta.system.service.impl

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.io.IoUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysFileMapper
import com.zeta.system.model.entity.SysFile
import com.zeta.system.service.ISysFileService
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.core.exception.BusinessException
import org.zetaframework.extra.file.model.FileDeleteParam
import org.zetaframework.extra.file.model.FileInfo
import org.zetaframework.extra.file.strategy.FileContext
import java.io.InputStream
import javax.servlet.http.HttpServletResponse

/**
 * <p>
 * 系统文件 服务实现类
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-11 11:18:44
 */
@Service
class SysFileServiceImpl(private val fileContext: FileContext): ISysFileService, ServiceImpl<SysFileMapper, SysFile>() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param bizType 业务类型 例如：order、user_avatar等 会影响文件url的值
     */
    override fun upload(file: MultipartFile, bizType: String?): SysFile {
        // 调用具体策略(配置文件里面配置的那个，没配置默认上传到本地)上传文件
        val fileInfo: FileInfo = fileContext.upload(file, bizType)

        val model = BeanUtil.toBean(fileInfo, SysFile::class.java)
        if(!this.save(model)) {
            throw BusinessException("文件保存失败")
        }
        return model
    }

    /**
     * 下载文件
     */
    override fun download(id: Long, response: HttpServletResponse) {
        val sysFile = this.getById(id) ?: throw BusinessException("文件不存在或已被删除")

        // 获取文件输入流
        val inputStream: InputStream? = fileContext.getFileInputStream(sysFile.path!!)
        inputStream?.use {
            // 设置响应头
            response.contentType = "application/octet-stream; charset=utf-8"
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + sysFile.uniqueFileName)

            // 将文件流写入到输出中去
            IoUtil.copy(inputStream, response.outputStream)
        }
    }

    /**
     * 删除文件
     *
     * @param id
     */
    override fun delete(id: Long): Boolean {
        val sysFile = this.getById(id) ?: throw BusinessException("文件不存在或已被删除")
        // 先删除文件
        if(!fileContext.delete(FileDeleteParam(path = sysFile.path!!))) {
            throw BusinessException("文件删除失败")
        }
        // 再删除数据
        return this.removeById(id)
    }

    /**
     * 批量删除文件
     *
     * @param ids
     */
    override fun batchDelete(ids: MutableList<Long>): Boolean {
        // 批量查询文件
        val listFile = this.listByIds(ids)
        if(listFile.isEmpty()) {
            return true
        }

        // 排除相对路径为空的文件，构造批量删除参数
        val params = listFile.filterNot { it.path.isNullOrBlank() }.map {
            FileDeleteParam(path = it.path)
        }
        // 批量删除， params不用判空
        fileContext.batchDelete(params)

        // 删除数据
        return this.removeByIds(ids)
    }
}
