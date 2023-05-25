package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.MapExcelGraphConstants
import cn.afterturn.easypoi.excel.ExcelExportUtil
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity
import cn.afterturn.easypoi.excel.graph.builder.ExcelChartBuildService
import cn.afterturn.easypoi.excel.graph.entity.ExcelGraph
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller

/**
 * Map图表视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
@Controller(MapExcelGraphConstants.MAP_GRAPH_EXCEL_VIEW)
class KtMapGraphExcelView(): MiniAbstractExcelView() {

    override fun renderMergedOutputModel(
        model: MutableMap<String, Any>,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        var codedFileName: String? = "临时文件"
        val params = model[MapExcelGraphConstants.PARAMS] as ExportParams?
        val entityList = model[MapExcelGraphConstants.ENTITY_LIST] as List<ExcelExportEntity>?
        val mapList = model[MapExcelGraphConstants.MAP_LIST] as List<Map<String, Any>?>?
        val graphDefinedList = model[MapExcelGraphConstants.GRAPH_DEFINED] as List<ExcelGraph>?
        // 构建数据
        val workbook = ExcelExportUtil.exportExcel(params, entityList, mapList)
        ExcelChartBuildService.createExcelChart(
            workbook,
            graphDefinedList,
            params!!.isDynamicData,
            params!!.isAppendGraph
        )

        if (model.containsKey(MapExcelGraphConstants.FILE_NAME)) {
            codedFileName = model[MapExcelGraphConstants.FILE_NAME] as String?
        }
        out(workbook, codedFileName!!, request, response)
    }
}
