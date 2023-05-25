package org.zetaframework.base.controller.curd

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.zetaframework.base.controller.BaseController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode
import java.io.Serializable

/**
 * 基础删除 Controller
 *
 * @param <Entity> 实体
 * @param <Id>     主键字段类型
 * @author gcc
 */
interface DeleteController<Entity, Id: Serializable>: BaseController<Entity> {

    /**
     * 单体删除
     *
     * @param id
     * @return R<Boolean>
     */
    @PreCheckPermission(value = ["{}:delete", "{}:remove"], mode = PreMode.OR)
    @ApiOperationSupport(order = 60, author = "AutoGenerate")
    @Operation(summary = "单体删除")
    @SysLog
    @DeleteMapping("/{id}")
    fun delete(@PathVariable @Parameter(description = "主键") id: Id): ApiResult<Boolean> {
        val result = handlerDelete(id)
        if(result.defExec) {
            result.setData(getBaseService().removeById(id))
        }
        return result
    }

    /**
     * 自定义单体删除
     *
     * @param id Id
     * @return R<Boolean>
     */
    fun handlerDelete(id: Id): ApiResult<Boolean> {
        return ApiResult.successDef()
    }

    /**
     * 批量删除
     *
     * @param ids List<Long>
     * @return R<Boolean>
     */
    @PreCheckPermission(value = ["{}:delete", "{}:remove"], mode = PreMode.OR)
    @ApiOperationSupport(order = 70, author = "AutoGenerate")
    @Operation(summary = "批量删除")
    @SysLog
    @DeleteMapping("/batch")
    fun batchDelete(@RequestBody ids: MutableList<Id>): ApiResult<Boolean> {
        val result = handlerBatchDelete(ids)
        if(result.defExec) {
            result.setData(getBaseService().removeByIds(ids))
        }
        return result
    }

    /**
     * 自定义批量删除
     *
     * @param ids Id
     * @return R<Boolean>
     */
    fun handlerBatchDelete(ids: MutableList<Id>): ApiResult<Boolean> {
        return ApiResult.successDef()
    }
}
