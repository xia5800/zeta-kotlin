package com.zeta.system.controller

import cn.afterturn.easypoi.excel.entity.ImportParams
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.lang.Assert
import com.zeta.system.model.dto.sysDictItem.SysDictItemDTO
import com.zeta.system.model.dto.sysDictItem.SysDictItemSaveDTO
import com.zeta.system.model.dto.sysDictItem.SysDictItemUpdateDTO
import com.zeta.system.model.entity.SysDict
import com.zeta.system.model.entity.SysDictItem
import com.zeta.system.model.param.SysDictItemQueryParam
import com.zeta.system.model.poi.SysDictItemExportPoi
import com.zeta.system.model.poi.SysDictItemImportPoi
import com.zeta.system.service.ISysDictItemService
import com.zeta.system.service.ISysDictService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperController
import org.zetaframework.base.controller.extra.PoiController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.saToken.annotation.PreCheckPermission

/**
 * 字典项 前端控制器
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:10
 */
@Api(tags = ["字典项"])
@PreAuth(replace = "sys:dictItem")
@RestController
@RequestMapping("/api/system/dictItem")
class SysDictItemController(
    private val dictService: ISysDictService
) : SuperController<ISysDictItemService, Long, SysDictItem, SysDictItemQueryParam, SysDictItemSaveDTO, SysDictItemUpdateDTO>(),
    PoiController<SysDictItemImportPoi, SysDictItemExportPoi, SysDictItem, SysDictItemQueryParam>
{

    /**
     * 根据字典编码查询字典项
     *
     * @param codes 字典code
     */
    @PreCheckPermission(value = ["{}:view"])
    @ApiOperation(value = "根据字典编码查询字典项")
    @PostMapping("/codeList")
    fun codeList(@RequestBody @ApiParam("字典code") codes: List<String>): ApiResult<Map<String, List<SysDictItemDTO>>> {
        Assert.notEmpty(codes, "字典code不能为空")
        return success(service.listByCodes(codes))
    }

    /**
     * 导入参数增强
     *
     * 说明：
     * 你可以在这里对ImportParams配置进行一些补充
     * 例如设置excel验证规则、校验组、校验处理接口等
     */
    override fun enhanceImportParams(importParams: ImportParams) {
        // 开启：校验上传的Excel数据
        importParams.isNeedVerify = true
    }

    /**
     * 处理导入数据
     *
     * 说明：
     * 你需要手动实现导入逻辑
     */
    override fun handlerImport(list: MutableList<SysDictItemImportPoi>): ApiResult<Boolean> {
        val batchList: List<SysDictItem> = list.map {
            BeanUtil.toBean(it, SysDictItem::class.java)
        }
        return success(service.saveBatch(batchList))
    }

    /**
     * 获取待导出的数据
     *
     * @param param QueryParam
     * @return MutableList<ExportBean>
     */
    override fun findExportList(param: SysDictItemQueryParam): MutableList<SysDictItemExportPoi> {
        // 条件查询Entity数据
        val list = super.handlerBatchQuery(param)
        if (list.isEmpty()) return mutableListOf()

        // 字典数据缓存
        val dictCacheMap: MutableMap<Long, SysDict> = mutableMapOf()

        // Entity -> ExportBean
        return list.map {
            val exportPoi = BeanUtil.toBean(it, SysDictItemExportPoi::class.java)
            // 通过id查询字典数据并缓存。 说明：保证每个字典只查一次数据库
            var dict = dictCacheMap[it.dictId]
            if (dict == null) {
                dict = dictService.getById(it.dictId)
                dictCacheMap[it.dictId!!] = dict
            }
            // 设置字典名
            exportPoi.dictName = dict?.name
            exportPoi
        }.toMutableList().also {
            dictCacheMap.clear()
        }
    }

}
