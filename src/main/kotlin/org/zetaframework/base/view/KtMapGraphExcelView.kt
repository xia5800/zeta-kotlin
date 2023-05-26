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

    /**
     * 子类必须实现此方法才能实际呈现视图。
     *
     * @param model 构造视图所需要的参数
     * @param request 请求
     * @param response 响应
     */
    override fun renderMergedOutputModel(model: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        val params = model[MapExcelGraphConstants.PARAMS] as ExportParams? ?: return
        val entityList = model[MapExcelGraphConstants.ENTITY_LIST] as List<ExcelExportEntity>?
        val mapList = model[MapExcelGraphConstants.MAP_LIST] as List<Map<String, Any>?>?
        val graphDefinedList = model[MapExcelGraphConstants.GRAPH_DEFINED] as List<ExcelGraph>?

        // 构建Excel工作簿对象
        val workbook = ExcelExportUtil.exportExcel(params, entityList, mapList)
        ExcelChartBuildService.createExcelChart(
            workbook,
            graphDefinedList,
            params.isDynamicData,
            params.isAppendGraph
        )

        // 默认文件名处理
        var codedFileName = "临时文件"
        if (model.containsKey(MapExcelGraphConstants.FILE_NAME)) {
            // 如果model里面没有获取到自定义的文件名，则使用默认文件名
            codedFileName = model[MapExcelGraphConstants.FILE_NAME] as String? ?: codedFileName
        }

        out(workbook, codedFileName, request, response)
    }
}
