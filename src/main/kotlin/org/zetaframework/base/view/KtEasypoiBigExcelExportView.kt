package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.BigExcelConstants
import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.handler.inter.IExcelExportServer
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller

/**
 * 大文件视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
@Controller(BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW)
class KtEasypoiBigExcelExportView(): MiniAbstractExcelView() {

    override fun renderMergedOutputModel(
        model: MutableMap<String, Any>,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        var codedFileName: String? = "临时文件"
        val workbook = ExcelExportUtil.exportBigExcel(
            model[BigExcelConstants.PARAMS] as ExportParams?,
            model[BigExcelConstants.CLASS] as Class<*>?,
            model[BigExcelConstants.DATA_INTER] as IExcelExportServer?,
            model[BigExcelConstants.DATA_PARAMS]
        )
        if (model.containsKey(BigExcelConstants.FILE_NAME)) {
            codedFileName = model[BigExcelConstants.FILE_NAME] as String?
        }
        out(workbook, codedFileName!!, request, response)
    }

}
