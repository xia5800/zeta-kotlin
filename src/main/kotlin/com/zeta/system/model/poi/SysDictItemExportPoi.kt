package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel

/**
 * 字典项Excel导出数据
 *
 * @author gcc
 */
class SysDictItemExportPoi {

    /** id */
    @Excel(name = "id", width = 20.0)
    var id: Long? = null

    /** 字典id */
    @Excel(name = "字典id", mergeVertical = true, width = 20.0)
    var dictId: Long? = null

    /** 字典名称 */
    @Excel(name = "字典名称", mergeVertical = true, width = 15.0)
    var dictName: String? = null

    /** 字典项 */
    @Excel(name = "字典项", width = 15.0)
    var name: String? = null

    /** 字典项值 */
    @Excel(name = "字典项值", width = 15.0)
    var value: String? = null

    /** 描述 */
    @Excel(name = "描述", width = 30.0)
    var describe: String? = null

    /** 排序 */
    @Excel(name = "排序")
    var sortValue: Int? = null

}
