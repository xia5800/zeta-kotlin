package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel
import org.zetaframework.base.entity.ImportPoi
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 字典项Excel导入数据
 *
 * @author gcc
 */
class SysDictItemImportPoi: ImportPoi() {

    /** 字典id */
    @Excel(name = "字典id", width = 20.0)
    @NotNull(message = "不能为空")
    var dictId: Long? = null

    /** 字典项 */
    @Excel(name = "字典项", width = 15.0)
    @NotBlank(message = "不能为空")
    var name: String? = null

    /** 字典项值 */
    @Excel(name = "字典项值", width = 15.0)
    @NotBlank(message = "不能为空")
    var value: String? = null

    /** 描述 */
    @Excel(name = "描述", width = 30.0)
    var describe: String? = null

    /** 排序 */
    @Excel(name = "排序")
    var sortValue: Int? = null

}
