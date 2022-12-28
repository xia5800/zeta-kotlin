package com.zeta.system.model.poi

import cn.afterturn.easypoi.excel.annotation.Excel

/**
 * 字典Excel导出数据
 *
 * @author gcc
 */
class SysDictExportPoi {

    /** id */
    @Excel(name = "id", width = 20.0)
    var id: Long? = null

    /** 名称 */
    @Excel(name = "字典名", width = 15.0)
    var name: String? = null

    /** 编码 */
    @Excel(name = "字典编码", width = 15.0)
    var code: String? = null

    /** 描述 */
    @Excel(name = "描述", width = 30.0)
    var describe: String? = null

    /** 排序 */
    @Excel(name = "排序")
    var sortValue: Int? = null

}
