package com.zeta.system.poi

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler
import com.zeta.system.model.entity.SysDict
import com.zeta.system.model.poi.SysDictImportPoi
import com.zeta.system.service.ISysDictService
import org.springframework.stereotype.Component
import org.zetaframework.base.param.ExistParam

/**
 * 字典Excel导入校验接口实现类
 *
 * @author gcc
 */
@Component
class SysDictExcelVerifyHandler(private val service: ISysDictService): IExcelVerifyHandler<SysDictImportPoi> {

    /**
     * 导入校验方法
     *
     * @param obj 导入对象
     * @return [ExcelVerifyHandlerResult]
     */
    override fun verifyHandler(obj: SysDictImportPoi): ExcelVerifyHandlerResult {
        var message = ""
        var flag = true

        if (obj.code.isNullOrBlank()) {
            message = "字典编码不能为空"
            flag = false
        } else {
            // 判断是否存在
            if (ExistParam<SysDict, Long>(SysDict::code, obj.code).isExist(service)) {
                message = "编码已存在"
                flag = false
            }
        }

        return ExcelVerifyHandlerResult(flag, message)
    }
}
