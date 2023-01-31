package org.zetaframework.base.param

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

/**
 * 导出Excel参数
 *
 * @author gcc
 */
@ApiModel(description = "导出Excel参数")
class ExportExcelParam<QueryParam> {

    /** 查询条件 */
    @ApiModelProperty(value = "查询条件", required = false)
    var queryParam: QueryParam? = null

    /** excel文件名 */
    @ApiModelProperty(value = "excel文件名,不带后缀", required = true)
    @get:NotBlank(message = "excel文件名不能为空")
    var fileName: String? = null

    /** 表格标题 */
    @ApiModelProperty(value = "表格标题", required = false)
    var title: String? = null

    /** sheet名称 */
    @ApiModelProperty(value = "sheet名称", required = false)
    var sheetName: String? = null

    /** excel文件类型 */
    @ApiModelProperty(value = "excel文件类型", allowableValues = "HSSF、XSSF", example = "XSSF", required = true)
    @get:NotBlank(message = "excel文件类型不能为空")
    var type: String? = null

}
