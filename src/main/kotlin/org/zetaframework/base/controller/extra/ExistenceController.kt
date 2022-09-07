package org.zetaframework.base.controller.extra

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.zetaframework.base.controller.BaseController
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
     * @return ApiResult<Boolean>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperation(value = "验证字段是否存在", notes = """
例如：<br>
新增用户的时候，验证用户名(username)的值(张三)是否被人使用了 <br>
{"field": "username",  "value": "张三"} <br><br>
修改用户的时候，验证用户名(username)的值(李四)是否被除了当前用户id(2011214167781)的人使用了 <br>
{"field": "username",  "value": "李四",  "id": "2011214167781"}<br>
    """)
    @GetMapping("/existence")
    fun existence(param: ExistParam<Entity, Id>): ApiResult<Boolean> {
        if(param.isExist(getBaseService())) {
            return success("${param.value}已存在", true)
        }
        return success("${param.value}不存在", false)
    }

}
