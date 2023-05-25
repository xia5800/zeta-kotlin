package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants
import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.excel.export.ExcelExportService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Controller

/**
 * 单文件视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
@Controller(NormalExcelConstants.EASYPOI_EXCEL_VIEW)
class KtEasypoiSingleExcelView: MiniAbstractExcelView() {

    override fun renderMergedOutputModel(
        model: MutableMap<String, Any>,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        var codedFileName: String? = "临时文件"
        var workbook: Workbook? = null
        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
            val list = model[NormalExcelConstants.MAP_LIST] as List<Map<String, Any>>?
            if (list!!.size == 0) {
                throw RuntimeException("MAP_LIST IS NULL")
            }
            workbook = ExcelExportUtil.exportExcel(
                list!![0][NormalExcelConstants.PARAMS] as ExportParams?,
                list!![0][NormalExcelConstants.CLASS] as Class<*>?,
                list!![0][NormalExcelConstants.DATA_LIST] as Collection<*>?
            )
            for (i in 1 until list!!.size) {
                ExcelExportService().createSheet(
                    workbook,
                    list!![i][NormalExcelConstants.PARAMS] as ExportParams?,
                    list[i][NormalExcelConstants.CLASS] as Class<*>?,
                    list!![i][NormalExcelConstants.DATA_LIST] as Collection<*>?
                )
            }
        } else {
            workbook = ExcelExportUtil.exportExcel(
                model[NormalExcelConstants.PARAMS] as ExportParams?,
                model[NormalExcelConstants.CLASS] as Class<*>?,
                model[NormalExcelConstants.DATA_LIST] as Collection<*>?
            )
        }
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            codedFileName = model[NormalExcelConstants.FILE_NAME] as String?
        }
        out(workbook, codedFileName!!, request, response)
    }
}
