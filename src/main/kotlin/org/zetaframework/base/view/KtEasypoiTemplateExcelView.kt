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

    /**
     * 子类必须实现此方法才能实际呈现视图。
     *
     * @param model 构造视图所需要的参数
     * @param request 请求
     * @param response 响应
     */
    override fun renderMergedOutputModel(model: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        // 构建Excel工作簿对象
        @Suppress("deprecation") val workbook = ExcelExportUtil.exportExcel(
            model[TemplateExcelConstants.PARAMS] as TemplateExportParams?,
            model[TemplateExcelConstants.CLASS] as Class<*>?,
            model[TemplateExcelConstants.LIST_DATA] as List<*>?,
            model[TemplateExcelConstants.MAP_DATA] as Map<String?, Any?>?
        )

        // 默认文件名处理
        var codedFileName = "临时文件"
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            // 如果model里面没有获取到自定义的文件名，则使用默认文件名
            codedFileName = model[NormalExcelConstants.FILE_NAME] as String? ?: codedFileName
        }

        out(workbook, codedFileName, request, response)
    }

}
