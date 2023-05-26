package org.zetaframework.base.view

import cn.afterturn.easypoi.entity.vo.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.view.AbstractView
import java.util.*

/**
 * Poi视图
 *
 * 说明：
 * 因为EasyPoi基于jdk8编译，使用的还是javax包，而jdk17使用的是jakarta包
 * 故将项目里使用到的对应工具类方法转换为jdk17下能使用的
 *
 * @author gcc
 */
abstract class PoiBaseView: AbstractView() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        /**
         * 是否IE浏览器
         *
         * @param request 请求
         */
        fun isIE(request: HttpServletRequest): Boolean {
            return request.getHeader("USER-AGENT").lowercase(Locale.getDefault()).indexOf("msie") > 0
                    || request.getHeader("USER-AGENT").lowercase(Locale.getDefault()).indexOf("rv:11.0") > 0
                    || request.getHeader("USER-AGENT").lowercase(Locale.getDefault()).indexOf("edge") > 0
        }

        /**
         * 渲染
         *
         * @param model 构造视图所需要的参数
         * @param request 请求
         * @param response 响应
         * @param viewName 视图名
         * @return
         */
        fun render(model: MutableMap<String, Any?>, request: HttpServletRequest, response: HttpServletResponse, viewName: String) {
            // 通过视图名获取对应的视图对象
            val view: PoiBaseView = when(viewName) {
                BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW -> KtEasypoiBigExcelExportView()
                MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW -> KtEasypoiMapExcelView()
                NormalExcelConstants.EASYPOI_EXCEL_VIEW -> KtEasypoiSingleExcelView()
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW -> KtEasypoiTemplateExcelView()
                MapExcelGraphConstants.MAP_GRAPH_EXCEL_VIEW -> KtMapGraphExcelView()
                else -> null
            } ?: return

            try {
                // 渲染合并输出模型
                view.renderMergedOutputModel(model, request, response)
            } catch (e: Exception) {
                logger.error(e.message, e)
            }
        }
    }

}
