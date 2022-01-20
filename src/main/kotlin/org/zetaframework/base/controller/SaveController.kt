package org.zetaframework.base.controller

import cn.hutool.core.bean.BeanUtil
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.annotations.ApiOperation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode

/**
 * 基础新增 Controller
 *
 * @param <Entity> 实体
 * @param <SaveDTO> 保存对象
 * @author gcc
 */
interface SaveController<Entity, SaveDTO>: BaseController<Entity> {

    /**
     * 新增
     *
     * @param saveDTO SaveDTO 保存对象
     * @return ApiResult<Entity>
     */
    @PreCheckPermission(value = ["{}:add", "{}:save"], mode = PreMode.OR)
    @ApiOperationSupport(order = 40, author = "AutoGenerate")
    @ApiOperation(value = "新增")
    @PostMapping
    fun save(@RequestBody @Validated saveDTO: SaveDTO): ApiResult<Boolean> {
        val result = handlerSave(saveDTO)
        if(result.defExec) {
            // SaveDTO -> Entity
            val entity = BeanUtil.toBean(saveDTO, getEntityClass())
            result.setData(getBaseService().save(entity))
        }
        return result
    }

    /**
     * 自定义新增
     *
     * @param saveDTO SaveDTO 保存对象
     * @return ApiResult<Entity>
     */
    fun handlerSave(saveDTO: SaveDTO): ApiResult<Boolean> {
        return ApiResult.successDef()
    }

}
