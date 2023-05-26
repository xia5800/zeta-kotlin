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

    /**
     * 子类必须实现此方法才能实际呈现视图。
     *
     * @param model 构造视图所需要的参数
     * @param request 请求
     * @param response 响应
     */
    override fun renderMergedOutputModel(model: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        // 构建Excel工作簿对象
        val workbook: Workbook
        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
            val list = model[NormalExcelConstants.MAP_LIST] as List<Map<String, Any>>?
            if (list.isNullOrEmpty()) {
                throw RuntimeException("MAP_LIST IS NULL")
            }
            workbook = ExcelExportUtil.exportExcel(
                list[0][NormalExcelConstants.PARAMS] as ExportParams?,
                list[0][NormalExcelConstants.CLASS] as Class<*>?,
                list[0][NormalExcelConstants.DATA_LIST] as Collection<*>?
            )
            for (i in 1 until list.size) {
                ExcelExportService().createSheet(
                    workbook,
                    list[i][NormalExcelConstants.PARAMS] as ExportParams?,
                    list[i][NormalExcelConstants.CLASS] as Class<*>?,
                    list[i][NormalExcelConstants.DATA_LIST] as Collection<*>?
                )
            }
        } else {
            workbook = ExcelExportUtil.exportExcel(
                model[NormalExcelConstants.PARAMS] as ExportParams?,
                model[NormalExcelConstants.CLASS] as Class<*>?,
                model[NormalExcelConstants.DATA_LIST] as Collection<*>?
            )
        }

        // 默认文件名处理
        var codedFileName = "临时文件"
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            // 如果model里面没有获取到自定义的文件名，则使用默认文件名
            codedFileName = model[NormalExcelConstants.FILE_NAME] as String? ?: codedFileName
        }

        out(workbook, codedFileName, request, response)
    }
}
