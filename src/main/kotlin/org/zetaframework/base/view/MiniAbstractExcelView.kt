package org.zetaframework.base.view

import cn.afterturn.easypoi.util.WebFilenameUtils
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook

/**
 * 基础抽象Excel View
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
abstract class MiniAbstractExcelView(): PoiBaseView() {

    init {
        // 设置此视图的contentType
        contentType = "text/html;application/vnd.ms-excel"
    }

    companion object {
        /** excel97-2003版本，扩展名 */
        const val HSSF = ".xls"
        /** excel2007+版本，扩展名 */
        const val XSSF = ".xlsx"
    }

    /**
     * 输出
     *
     * @param workbook Excel工作簿
     * @param codedFileName 编码后的文件名
     * @param request 请求
     * @param response 响应
     */
    fun out(workbook: Workbook, codedFileName: String, request: HttpServletRequest, response: HttpServletResponse) {
        // 处理文件名
        val fileName = if (workbook is HSSFWorkbook) {
            codedFileName + HSSF
        } else {
            codedFileName + XSSF
        }

        // 用工具类生成符合RFC 5987标准的文件名header, 去掉UA判断
        response.setHeader("content-disposition", WebFilenameUtils.disposition(fileName))
        val out: ServletOutputStream = response.outputStream
        workbook.write(out)
        out.flush()
    }

}
