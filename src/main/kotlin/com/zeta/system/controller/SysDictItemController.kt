package com.zeta.system.controller

import cn.hutool.core.lang.Assert
import com.zeta.system.model.dto.sysDictItem.SysDictItemDTO
import com.zeta.system.model.dto.sysDictItem.SysDictItemSaveDTO
import com.zeta.system.model.dto.sysDictItem.SysDictItemUpdateDTO
import com.zeta.system.model.entity.SysDictItem
import com.zeta.system.model.param.SysDictItemQueryParam
import com.zeta.system.service.ISysDictItemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.saToken.annotation.PreCheckPermission

/**
 * <p>
 * 字典项 前端控制器
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:10
 */
@Api(tags = ["字典项"])
@PreAuth(replace = "sys:dictItem")
@RestController
@RequestMapping("/api/system/dictItem")
class SysDictItemController: SuperController<ISysDictItemService, Long, SysDictItem, SysDictItemQueryParam, SysDictItemSaveDTO, SysDictItemUpdateDTO>() {

    /**
     * 根据字典编码查询字典项
     *
     * @param codes List<String>
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperation("根据字典编码查询字典项")
    @PostMapping("/codeList")
    fun codeList(@RequestBody @ApiParam("字典code") codes: List<String>): ApiResult<Map<String, List<SysDictItemDTO>>> {
        Assert.notEmpty(codes, "字典code不能为空")
        return success(service.listByCodes(codes))
    }


}
