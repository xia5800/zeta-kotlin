package org.zetaframework.base.param

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

/**
 * 获取导入Excel模板 参数
 *
 * @author gcc
 */
@Schema(description = "获取导入Excel模板参数")
class ImportExcelTemplateParam {

    /** excel模板文件名 */
    @Schema(description = "excel模板文件名,不带后缀", required = true)
    @get:NotBlank(message = "excel模板文件名不能为空")
    var fileName: String? = null

    /** 表格标题 */
    @Schema(description = "表格标题", required = false)
    var title: String? = null

    /** sheet名称 */
    @Schema(description = "sheet名称", required = false)
    var sheetName: String? = null

    /** excel文件类型 */
    @Schema(description = "excel模板类型", allowableValues = ["HSSF","XSSF"], example = "XSSF", required = true)
    @get:NotBlank(message = "excel模板类型不能为空")
    var type: String? = null

}
