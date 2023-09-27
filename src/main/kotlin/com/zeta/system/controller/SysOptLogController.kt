package com.zeta.system.controller

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import com.zeta.system.model.dto.sysOptLog.SysOptLogTableDTO
import com.zeta.system.model.entity.SysOptLog
import com.zeta.system.model.param.SysOptLogQueryParam
import com.zeta.system.service.ISysOptLogService
import com.zeta.system.service.ISysUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.zetaframework.base.controller.SuperSimpleController
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.ApiResult
import org.zetaframework.base.result.PageResult
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.saToken.annotation.PreCheckPermission

/**
 * 操作日志 前端控制器
 *
 * @author gcc
 * @date 2022-03-18 15:27:15
 */
@Tag(name = "操作日志", description = "操作日志")
@PreAuth(replace = "sys:optLog")
@RestController
@RequestMapping("/api/system/optLog")
class SysOptLogController(private val userService: ISysUserService): SuperSimpleController<ISysOptLogService, SysOptLog>() {

    /**
     * 分页查询
     * @param param PageParam<QueryParam> 分页查询参数
     * @return ApiResult<PageResult<Entity>>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperationSupport(order = 10)
    @Operation(summary = "分页查询")
    @PostMapping("/page")
    fun page(@RequestBody param: PageParam<SysOptLogQueryParam>): ApiResult<PageResult<SysOptLogTableDTO>> {
        return success(service.pageTable(param))
    }


    /**
     * 单体查询
     * @param id 主键
     * @return ApiResult<Entity?>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperationSupport(order = 20)
    @Operation(summary = "单体查询", description = "根据主键查询唯一数据，若查询不到则返回null")
    @GetMapping("/{id}")
    fun get(@PathVariable("id") @Parameter(description = "主键") id: Long): ApiResult<SysOptLog?> {
        val entity = service.getById(id) ?: return success(null)

        // 查询操作人
        val user = userService.getById(entity.createdBy)
        entity.userName = user?.username
        return success(entity)
    }
}
