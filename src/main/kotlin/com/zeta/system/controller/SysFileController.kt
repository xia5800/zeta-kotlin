package com.zeta.system.controller

import com.zeta.system.model.entity.SysFile
import com.zeta.system.model.param.SysFileQueryParam
import com.zeta.system.service.ISysFileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.base.controller.SuperSimpleController
import org.zetaframework.base.controller.curd.DeleteController
import org.zetaframework.base.controller.curd.QueryController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode

/**
 * 系统文件 前端控制器
 *
 * @author AutoGenerator
 * @date 2022-04-11 11:18:44
 */
@Tag(name = "系统文件", description = "系统文件")
@PreAuth(replace = "sys:file")
@RestController
@RequestMapping("/api/system/file")
class SysFileController: SuperSimpleController<ISysFileService, SysFile>(),
    QueryController<SysFile, Long, SysFileQueryParam>,
    DeleteController<SysFile, Long>
{

    /**
     * 上传文件
     *
     * 吐个槽：注解地狱
     * @param file 文件对象
     * @param bizType 业务类型 例如：order、user_avatar等 会影响文件url的值
     */
    @SysLog(request = false)
    @PreCheckPermission(value = ["{}:add", "{}:save"], mode = PreMode.OR)
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    fun upload(
        @RequestParam
        file: MultipartFile,
        @RequestParam(required = false)
        @Parameter(description = "业务类型 例如：order、user_avatar等 会影响文件url的值", example = "avatar")
        bizType: String? = null
    ): ApiResult<SysFile> {
        return success(service.upload(file, bizType))
    }


    /**
     * 下载文件
     *
     * @param id
     * @param response
     */
    @SysLog(response = false)
    @PreCheckPermission(value = ["{}:export"])
    @Operation(summary = "下载文件")
    @GetMapping(value = ["/download"], produces = ["application/octet-stream"])
    fun download(@RequestParam @Parameter(description = "文件id") id: Long, response: HttpServletResponse) {
        service.download(id, response)
    }

    /**
     * 自定义单体删除文件
     *
     * @param id 主键
     * @return ApiResult<Boolean>
     */
    override fun handlerDelete(id: Long): ApiResult<Boolean> {
        return success(service.delete(id))
    }

    /**
     * 自定义批量删除文件
     *
     * @param ids 主键列表
     * @return ApiResult<Boolean>
     */
    override fun handlerBatchDelete(ids: MutableList<Long>): ApiResult<Boolean> {
        return success(service.batchDelete(ids))
    }
}
