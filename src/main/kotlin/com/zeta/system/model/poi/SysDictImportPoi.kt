package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel
import jakarta.validation.constraints.NotBlank
import org.zetaframework.base.entity.ImportPoi

/**
 * 字典Excel导入数据
 *
 * @author gcc
 */
class SysDictImportPoi: ImportPoi() {

    /** 名称 */
    @Excel(name = "字典名", width = 15.0)
    @NotBlank(message = "不能为空")
    var name: String? = null

    /** 编码 */
    @Excel(name = "字典编码", width = 15.0)
    @NotBlank(message = "不能为空")
    var code: String? = null

    /** 描述 */
    @Excel(name = "描述", width = 30.0)
    var describe: String? = null

    /** 排序 */
    @Excel(name = "排序")
    var sortValue: Int? = null

}
