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

    /**
     * 子类必须实现此方法才能实际呈现视图。
     *
     * @param model 构造视图所需要的参数
     * @param request 请求
     * @param response 响应
     */
    override fun renderMergedOutputModel(model: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        // 构建Excel工作簿对象
        val workbook = ExcelExportUtil.exportExcel(
            model[MapExcelConstants.PARAMS] as ExportParams?,
            model[MapExcelConstants.ENTITY_LIST] as List<ExcelExportEntity?>?,
            model[MapExcelConstants.MAP_LIST] as Collection<MutableMap<*, *>?>?
        )

        // 默认文件名处理
        var codedFileName = "临时文件"
        if (model.containsKey(MapExcelConstants.FILE_NAME)) {
            // 如果model里面没有获取到自定义的文件名，则使用默认文件名
            codedFileName = model[MapExcelConstants.FILE_NAME] as String? ?: codedFileName
        }

        out(workbook, codedFileName, request, response)
    }
}
