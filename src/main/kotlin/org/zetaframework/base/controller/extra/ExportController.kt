package org.zetaframework.base.controller.extra

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType
import cn.afterturn.easypoi.view.PoiBaseView
import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.annotations.ApiOperation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.zetaframework.base.controller.BaseController
import org.zetaframework.base.param.ExportExcelParam
import org.zetaframework.core.exception.ArgumentException
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Excel导出Controller
 *
 * @param ExportBean  Excel导出实体
 * @param Entity      实体
 * @param QueryParam  查询参数
 *
 * @author gcc
 */
interface ExportController<ExportBean, Entity, QueryParam>: BaseController<Entity> {

    /**
     * 获取导出实体的类型
     */
    fun getExportExcelClass(): Class<ExportBean>? {
        val type: Type? = this.javaClass.genericInterfaces.first {
            (it as ParameterizedType).rawType.typeName == ExportController::class.java.typeName
        }
        return type?.let { (it as ParameterizedType).actualTypeArguments[0] as Class<ExportBean> }
    }


    /**
     * 导出Excel
     *
     * @param params   导出Excel参数
     * @param request  请求
     * @param response 响应
     */
    @PreCheckPermission(value = ["{}:export", "{}:view"], mode = PreMode.OR)
    @ApiOperationSupport(order = 85, author = "AutoGenerate")
    @ApiOperation(value = "导出Excel", notes = """
    导出参数示例：
    {
      "fileName": "用户列表",   // 【必传】excel的文件名
      "queryParam": { },       // 【必传】queryParam，数据查询条件可以为空，为空返回所有数据
      "sheetName": "",        // 【非必传】sheet名
      "title": "",            // 【非必传】第一页sheet表格的表头
      "type": "XSSF"          // 【必传】可选值：HSSF（excel97-2003版本，扩展名.xls）、XSSF（excel2007+版本，扩展名.xlsx）
    }
    """)
    @SysLog(response = false)
    @PostMapping(value = ["/export"], produces = ["application/octet-stream"])
    fun exportExcel(@RequestBody @Validated params: ExportExcelParam<QueryParam>, request: HttpServletRequest, response: HttpServletResponse) {
        val queryParam = params.queryParam ?: throw ArgumentException("查询条件不能为空")
        // 获取导出参数
        val exportParams = getExportParams(params)
        // 获取待导出的数据
        val list = findExportList(queryParam)

        // 构造excel导出视图所需要的参数
        val map = mutableMapOf(
            NormalExcelConstants.DATA_LIST to list,
            NormalExcelConstants.CLASS to getExportExcelClass(),
            NormalExcelConstants.PARAMS to exportParams,
            NormalExcelConstants.FILE_NAME to params.fileName
        )
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW)
    }


    /**
     * 获取导出参数
     *
     * @param params 导出Excel参数
     * @return ExportParams
     */
    fun getExportParams(params: ExportExcelParam<QueryParam>): ExportParams {
        // 处理excel文件类型
        val type = if (params.type!! == ExcelType.HSSF.name)  ExcelType.HSSF else  ExcelType.XSSF
        val title = if (params.title.isNullOrBlank()) null else params.title
        // 构造ExportParams
        val exportParams = ExportParams(title, params.sheetName ?: "sheet", type)
        // ExportParams设置补充
        enhanceExportParams(exportParams)
        return exportParams
    }

    /**
     * 导出参数增强
     *
     * 说明：
     * 你可以在这里对ExportParams配置进行一些补充
     * 例如设置表格第二行的名称、冻结一些列、表头颜色等
     */
    fun enhanceExportParams(exportParams: ExportParams) { }

    /**
     * 获取待导出的数据
     *
     * @param param QueryParam
     * @return MutableList<Entity>
     */
    fun findExportList(param: QueryParam): MutableList<ExportBean> {
        // 构造查询条件
        val entity = BeanUtil.toBean(param, getEntityClass())

        // 条件查询Entity数据
        val list = getBaseService().list(QueryWrapper(entity))
        if (list.isNullOrEmpty()) return mutableListOf()

        // Entity -> ExportBean
        return list.map {
            BeanUtil.toBean(it, getExportExcelClass())
        }.toMutableList()
    }

}
