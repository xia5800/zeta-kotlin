package org.zetaframework.base.controller.curd

import cn.hutool.core.bean.BeanUtil
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.annotations.ApiOperation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.zetaframework.base.controller.BaseController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode
import org.zetaframework.core.validation.group.Update

/**
 * 基础修改 Controller
 *
 * @param <Entity> 实体
 * @param <UpdateDTO> 修改对象
 * @author gcc
 */
interface UpdateController<Entity, UpdateDTO>: BaseController<Entity> {

    /**
     * 修改
     *
     * @param updateDTO UpdateDTO 修改对象
     * @return ApiResult<Boolean>
     */
    @PreCheckPermission(value = ["{}:edit", "{}:update"], mode = PreMode.OR)
    @ApiOperationSupport(order = 50, author = "AutoGenerate")
    @ApiOperation(value = "修改")
    @SysLog
    @PutMapping
    fun update(@RequestBody @Validated(Update::class) updateDTO: UpdateDTO): ApiResult<Boolean> {
        val result = handlerUpdate(updateDTO)
        if (result.defExec) {
            // updateDTO -> Entity
            val entity = BeanUtil.toBean(updateDTO, getEntityClass())
            result.setData(getBaseService().updateById(entity))
        }
        return result
    }

    /**
     * 自定义修改
     *
     * @param updateDTO UpdateDTO 修改对象
     * @return ApiResult<Boolean>
     */
    fun handlerUpdate(updateDTO: UpdateDTO): ApiResult<Boolean> {
        return ApiResult.successDef()
    }

}
