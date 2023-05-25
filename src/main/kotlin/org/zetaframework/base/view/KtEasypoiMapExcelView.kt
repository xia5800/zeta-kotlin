package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.MapExcelConstants
import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller

/**
 * Map视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
@Controller(MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW)
class KtEasypoiMapExcelView(): MiniAbstractExcelView() {

    override fun renderMergedOutputModel(
        model: MutableMap<String, Any>,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        var codedFileName: String? = "临时文件"
        val workbook = ExcelExportUtil.exportExcel(
            model[MapExcelConstants.PARAMS] as ExportParams?,
            model[MapExcelConstants.ENTITY_LIST] as List<ExcelExportEntity?>?,
            model[MapExcelConstants.MAP_LIST] as Collection<MutableMap<*, *>?>?
        )
        if (model.containsKey(MapExcelConstants.FILE_NAME)) {
            codedFileName = model[MapExcelConstants.FILE_NAME] as String?
        }
        out(workbook, codedFileName!!, request, response)
    }
}
