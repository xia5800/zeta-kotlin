package org.zetaframework.base.controller.extra

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants
import cn.afterturn.easypoi.excel.ExcelImportUtil
import cn.afterturn.easypoi.excel.entity.ExportParams
import cn.afterturn.easypoi.excel.entity.ImportParams
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType
import cn.afterturn.easypoi.view.PoiBaseView
import cn.hutool.core.convert.Convert
import cn.hutool.core.io.FileTypeUtil
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.annotations.ApiOperation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.zetaframework.base.controller.BaseController
import org.zetaframework.base.entity.ImportPoi
import org.zetaframework.base.param.ImportExcelTemplateParam
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.annotation.SysLog
import org.zetaframework.core.saToken.annotation.PreCheckPermission
import org.zetaframework.core.saToken.annotation.PreMode
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 * Excel导入Controller
 *
 * @param ImportBean  Excel导入实体  必须继承[ImportPoi]类
 * @param Entity      实体
 *
 * @author gcc
 */
interface ImportController<ImportBean: ImportPoi, Entity>: BaseController<Entity> {

    /**
     * 获取导入实体的类型
     */
    fun getImportExcelClass(): Class<ImportBean>? {
        /**
         * 代码解释：
         * // 获取子类的所有接口。
         * `this.javaClass.genericInterfaces`
         * 例如：
         * class ChildController: SaveController<xxx,xxx>, UpdateController<xxxx,xxxx>, ImportController<xxx,xxx> {}
         * ChildController类就实现了3个接口。
         *
         * // 筛选出符合条件的第一个对象
         * first {}
         * 在['interface SaveController', 'interface UpdateController', 'interface ImportController']中筛选出符合条件的第一个对象
         *
         * // 筛选条件
         * (it as ParameterizedType).rawType.typeName == ImportController::class.java.typeName
         * 本质就是判断 `'interface ImportController' == 'interface ImportController'`
         */
        val type: Type? = this.javaClass.genericInterfaces.first {
            (it as ParameterizedType).rawType.typeName == ImportController::class.java.typeName
        }

        // 筛选不到返回null，否则获取ImportController接口的第一个泛型值，反射成具体对象
        // ?.let 如果不为空则进行xxx操作。
        return type?.let { (it as ParameterizedType).actualTypeArguments[0] as Class<ImportBean> }
    }

    /**
     * 获取导入模板
     *
     * 说明：
     * 1.默认导出ImportBean对象生成的Excel模板
     * 2.如果想要导出resource目录下的Excel模板，请重写该方法
     *
     * @param param  获取导入Excel模板 参数
     * @param request 请求
     * @param response 响应
     */
    @PreCheckPermission(value = ["{}:import", "{}:view"], mode = PreMode.OR)
    @ApiOperationSupport(order = 80, author = "AutoGenerate")
    @ApiOperation(value = "获取导入模板", notes = """
    获取导入模板接口传参示例：
    GET /api/xxxx/template?filename=用户列表&type=XSSF&sheetName=&title=
    
    参数说明：
    "fileName": "用户列表",  // 【必传】excel的文件名
    "sheetName": "",        // 【非必传】sheet名
    "title": "",            // 【非必传】第一页sheet表格的表头
    "type": "XSSF"          // 【必传】可选值：HSSF（excel97-2003版本，扩展名.xls）、XSSF（excel2007+版本，扩展名.xlsx）
    """)
    @SysLog(response = false)
    @GetMapping(value = ["/template"], produces = ["application/octet-stream"])
    fun getImportTemplate(param: ImportExcelTemplateParam, request: HttpServletRequest, response: HttpServletResponse) {
        // 处理excel文件类型
        val type = if (param.type!! == ExcelType.HSSF.name)  ExcelType.HSSF else  ExcelType.XSSF
        val title = if (param.title.isNullOrBlank()) null else param.title

        // 构造ExportParams
        val exportParams = ExportParams(title, param.sheetName ?: "sheet", type)

        // 获取导入模板基础数据
        val list = getImportTemplateData()

        // 构造excel导出视图所需要的参数
        val map = mutableMapOf(
            NormalExcelConstants.DATA_LIST to list,
            NormalExcelConstants.CLASS to getImportExcelClass(),
            NormalExcelConstants.PARAMS to exportParams,
            NormalExcelConstants.FILE_NAME to param.fileName
        )
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW)
    }

    /**
     * 获取导入模板基础数据
     *
     * 说明：
     * 1.前端下载导入模板，如果需要导入模板里面有一些基础数据，可以重写本方法
     * 2.本方法默认在导入模板里面添加一条空的数据行
     */
    fun getImportTemplateData(): MutableList<ImportBean?> {
        // 反射创建一个ImportBean对象
        val entity = getImportExcelClass()?.getDeclaredConstructor()?.newInstance()
        return mutableListOf(entity)
    }

    /**
     * 导入Excel
     *
     * @param file   导入的excel文件
     * @param request  请求
     */
    @PreCheckPermission(value = ["{}:import", "{}:save"], mode = PreMode.OR)
    @ApiOperationSupport(order = 81, author = "AutoGenerate")
    @ApiOperation(value = "导入Excel", notes = """
    【注意】请求类型为form-data
    导入参数示例：
    ----------------------------------
    file：               【必传】文件对象
    verifyFailReturn：   【非必传】true, false。默认true, 如果Excel数据校验失败“是否不处理校验通过的数据”而直接返回通知前端校验失败。
    titleRows：          【非必传】默认0, 表格标题行数, 见文章：https://blog.csdn.net/weixin_43009990/article/details/106609660
    headRows：           【非必传】默认1, 表头行数
    startRows：          【非必传】默认0, 字段真正值和列标题之间的距离
    startSheetIndex：    【非必传】默认0, 开始读取的sheet位置
    sheetNum：           【非必传】默认1, 上传表格需要读取的sheet数量
    needVerify：         【非必传】true, false。默认false, 是否需要校验上传的Excel
    lastOfInvalidRow：   【非必传】默认0, 最后的无效行数
    readRows：           【非必传】默认0, 手动控制读取的行数
    """)
    @SysLog
    @PostMapping(value = ["/import"])
    @Transactional(rollbackFor = [Exception::class])
    fun importExcel(@RequestParam file: MultipartFile, request: HttpServletRequest): ApiResult<Boolean> {
        // 判断文件类型是否是excel文件
        val typeName = try {
            FileTypeUtil.getType(file.inputStream, file.originalFilename)
        } catch (e: Exception) { "" }
        if (!typeName.equals("xls", true) && !typeName.equals("xlsx", true)) {
            return fail("不允许的文件类型")
        }

        // 如果需要对导入的excel数据进行校验，如果校验失败“是否不处理校验通过的数据”而直接返回通知前端校验失败  说明：仅isNeedVerify有值时生效
        val flag = request.getParameter("verifyFailReturn")
        val verifyFailReturn: Boolean = if (flag.isNullOrBlank()) true else Convert.toBool(flag, true)

        // 获取导入参数
        val importParams = getImportParams(request)

        // 获取导入的数据
        val list: MutableList<ImportBean> = if (importParams.isNeedVerify) {
            // 解析并校验导入的excel数据
            val result = ExcelImportUtil.importExcelMore<ImportBean>(file.inputStream, getImportExcelClass(), importParams)

            // 有校验不通过的数据是否直接返回
            if (result.isVerifyFail && verifyFailReturn) {
                val message = result.failList.joinToString("\n") {
                    "第${it.rowNum}行校验错误：${it.errorMsg}"
                }
                return fail(message)
            }
            result.list
        } else {
            // 解析导入的excel数据
            ExcelImportUtil.importExcel(file.inputStream, getImportExcelClass(), importParams)
        }
        if (list.isNullOrEmpty()) return fail("导入数据为空!")

        // 处理导入数据
        return handlerImport(list)
    }


    /**
     * 构造导入参数
     *
     * @param request
     * @return ImportParams
     */
    fun getImportParams(request: HttpServletRequest): ImportParams {
        val importParams = ImportParams()

        // 获取所有请求参数名
        val names = request.parameterNames
        while (names.hasMoreElements()) {
            // 获取请求参数值，如果值为空不处理
            val name = names.nextElement()
            val value = request.getParameter(name)
            if (value.isNullOrBlank()) continue

            // 如果参数名和importParams参数名匹配，则设置值
            when(name) {
                // 表格标题行数,默认0 见文章：https://blog.csdn.net/weixin_43009990/article/details/106609660
                "titleRows" -> importParams.titleRows = Convert.toInt(value, 0)
                // 表头行数,默认1
                "headRows" -> importParams.headRows = Convert.toInt(value, 1)
                // 字段真正值和列标题之间的距离 默认0
                "startRows" -> importParams.startRows = Convert.toInt(value, 0)
                // 开始读取的sheet位置,默认为0
                "startSheetIndex" -> importParams.startSheetIndex = Convert.toInt(value, 0)
                // 上传表格需要读取的sheet数量,默认为1
                "sheetNum" -> importParams.sheetNum = Convert.toInt(value, 1)
                // 是否需要校验上传的Excel,默认为false
                "needVerify" -> importParams.isNeedVerify = Convert.toBool(value, false)
                // 最后的无效行数
                "lastOfInvalidRow" -> importParams.lastOfInvalidRow = Convert.toInt(value, 0)
                // 手动控制读取的行数
                "readRows" -> importParams.readRows = Convert.toInt(value, 0)
            }
        }

        // ImportParams设置补充
        enhanceImportParams(importParams)
        return importParams
    }

    /**
     * 导入参数增强
     *
     * 说明：
     * 你可以在这里对ImportParams配置进行一些补充
     * 例如设置excel验证规则、校验组、校验处理接口等
     */
    fun enhanceImportParams(importParams: ImportParams) { }

    /**
     * 处理导入数据
     *
     * 说明：
     * 你需要手动实现导入逻辑
     */
    fun handlerImport(list: MutableList<ImportBean>): ApiResult<Boolean> {
        return fail("请在子类Controller重写导入方法，实现导入逻辑")
    }

}
