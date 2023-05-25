package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants
import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants
import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.TemplateExportParams
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller

/**
 * 模板视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
@Controller(TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW)
class KtEasypoiTemplateExcelView(): MiniAbstractExcelView() {

    override fun renderMergedOutputModel(
        model: MutableMap<String, Any>,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        var codedFileName: String? = "临时文件"
        @Suppress("deprecation") val workbook = ExcelExportUtil.exportExcel(
            model[TemplateExcelConstants.PARAMS] as TemplateExportParams?,
            model[TemplateExcelConstants.CLASS] as Class<*>?,
            model[TemplateExcelConstants.LIST_DATA] as List<*>?,
            model[TemplateExcelConstants.MAP_DATA] as Map<String?, Any?>?
        )
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            codedFileName = model[NormalExcelConstants.FILE_NAME] as String?
        }
        out(workbook, codedFileName!!, request, response)
    }

}
