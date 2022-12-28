package com.zeta.system.controller

import cn.afterturn.easypoi.excel.entity.ImportParams
import cn.hutool.core.bean.BeanUtil
import com.zeta.system.model.dto.sysDict.SysDictSaveDTO
import com.zeta.system.model.dto.sysDict.SysDictUpdateDTO
import com.zeta.system.model.entity.SysDict
import com.zeta.system.model.param.SysDictQueryParam
import com.zeta.system.model.poi.SysDictExportPoi
import com.zeta.system.model.poi.SysDictImportPoi
import com.zeta.system.poi.SysDictExcelVerifyHandler
import com.zeta.system.service.ISysDictService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperController
import org.zetaframework.base.controller.extra.PoiController
import org.zetaframework.base.param.ExistParam
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreAuth

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:09
 */
@Api(tags = ["字典"])
@PreAuth(replace = "sys:dict")
@RestController
@RequestMapping("/api/system/dict")
class SysDictController(
    private val sysDictExcelVerifyHandler: SysDictExcelVerifyHandler
) : SuperController<ISysDictService, Long, SysDict, SysDictQueryParam, SysDictSaveDTO, SysDictUpdateDTO>(),
    PoiController<SysDictImportPoi, SysDictExportPoi, SysDict, SysDictQueryParam>
{

    /**
     * 自定义新增
     *
     * @param saveDTO SaveDTO 保存对象
     * @return ApiResult<Boolean>
     */
    override fun handlerSave(saveDTO: SysDictSaveDTO): ApiResult<Boolean> {
        // 判断是否存在
        if(ExistParam<SysDict, Long>(SysDict::code, saveDTO.code).isExist(service)) {
            return fail("编码已存在")
        }
        return super.handlerSave(saveDTO)
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
        // 校验处理接口：字典编码重复校验
        importParams.verifyHandler = sysDictExcelVerifyHandler
    }

    /**
     * 处理导入数据
     *
     * 说明：
     * 你需要手动实现导入逻辑
     */
    override fun handlerImport(list: MutableList<SysDictImportPoi>): ApiResult<Boolean> {
        val batchList: List<SysDict> = list.map {
            BeanUtil.toBean(it, SysDict::class.java)
        }
        return success(service.saveBatch(batchList))
    }

    /**
     * 获取待导出的数据
     *
     * @param param QueryParam
     * @return MutableList<Entity>
     */
    override fun findExportList(param: SysDictQueryParam): MutableList<SysDictExportPoi> {
        // 条件查询Entity数据
        val list = super.handlerBatchQuery(param)
        if (list.isNullOrEmpty()) return mutableListOf()

        // Entity -> ExportBean
        return list.map {
            BeanUtil.toBean(it, SysDictExportPoi::class.java)
        }.toMutableList()
    }

}
