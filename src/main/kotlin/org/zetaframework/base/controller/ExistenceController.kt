package org.zetaframework.base.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.zetaframework.base.param.ExistParam
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreCheckPermission

/**
 * 验证存在 Controller
 *
 * @param <Entity> 实体
 * @param <Id>     主键字段类型
 * @author gcc
 */
interface ExistenceController<Entity, Id>: BaseController<Entity> {

    /**
     * 验证字段是否存在
     * @param param ExistenceParam<Entity, Id>
     * @return ApiResult<String>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperation("验证字段是否存在")
    @GetMapping("/existence")
    fun existence(param: ExistParam<Entity, Id>): ApiResult<Boolean> {
        if(param.isExist(getBaseService())) {
            return success("${param.value}已存在", true)
        }
        return success("${param.value}不存在", false)
    }

}
