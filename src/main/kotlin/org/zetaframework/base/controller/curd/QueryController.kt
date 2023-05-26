package org.zetaframework.base.controller.curd

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.ApiResult
import org.zetaframework.base.result.PageResult
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import java.io.Serializable

/**
 * 基础查询 Controller
 *
 * @param <Id>          主键字段类型
 * @param <Entity>      实体
 * @param <QueryParam>   分页参数
 * @author gcc
 */
interface QueryController<Entity, Id: Serializable, QueryParam> : PageController<Entity, QueryParam> {

    /**
     * 分页查询
     *
     * @param param 分页查询参数
     * @return ApiResult<IPage<Entity>>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperationSupport(order = 10, author = "AutoGenerate")
    @Operation(summary = "分页查询")
    @SysLog(response = false)
    @PostMapping("/page")
    fun page(@RequestBody param: PageParam<QueryParam>): ApiResult<PageResult<Entity>> {
        return success(super.query(param))
    }

    /**
     * 批量查询
     *
     * @param param 批量查询参数
     * @return ApiResult<List<Entity>>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperationSupport(order = 20, author = "AutoGenerate")
    @Operation(summary = "批量查询")
    @SysLog(response = false)
    @PostMapping("/query")
    fun list(@RequestBody param: QueryParam): ApiResult<MutableList<Entity>> {
        return success(handlerBatchQuery(param))
    }


    /**
     * 自定义批量查询
     *
     * @param param 批量查询参数
     * @return MutableList<Entity>
     */
    fun handlerBatchQuery(param: QueryParam): MutableList<Entity> {
        val entity = BeanUtil.toBean(param, getEntityClass())
        // 批量查询
        val list = getBaseService().list(QueryWrapper<Entity>(entity))
        // 处理批量查询数据
        handlerBatchData(list)
        return list
    }

    /**
     * 处理批量查询数据
     * @param list 实体列表
     */
    fun handlerBatchData(list: MutableList<Entity>) { }


    /**
     * 单体查询
     * @param id 主键
     * @return R<Entity?>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperationSupport(order = 30, author = "AutoGenerate")
    @Operation(summary = "单体查询", description = "根据主键查询唯一数据，若查询不到则返回null")
    @SysLog
    @GetMapping("/{id}")
    fun get(@PathVariable("id") @Parameter(description = "主键") id: Id): ApiResult<Entity?> {
        val entity = getBaseService().getById(id)
        // 处理单体查询数据
        handlerGetData(entity)
        return success(entity)
    }


    /**
     * 处理单体查询数据
     * @param entity 实体对象
     */
    fun handlerGetData(entity: Entity?) { }

}
