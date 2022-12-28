package org.zetaframework.base.entity

import cn.afterturn.easypoi.handler.inter.IExcelDataModel
import cn.afterturn.easypoi.handler.inter.IExcelModel

/**
 * Excel导入数据接收对象 父类
 *
 * @author gcc
 */
abstract class ImportPoi: IExcelModel, IExcelDataModel {

    /** 导入的错误信息 */
    var importErrorMsg: String? = null

    /** 导入的行号 */
    var importRowNum: Int? = null


    /**
     * 获取错误数据
     * @return
     */
    override fun getErrorMsg(): String? {
        return this.importErrorMsg
    }

    /**
     * 设置错误信息
     * @param errorMsg
     */
    override fun setErrorMsg(errorMsg: String?) {
        this.importErrorMsg = errorMsg
    }

    /**
     * 获取行号
     * @return
     */
    override fun getRowNum(): Int? {
        return this.importRowNum
    }

    /**
     * 设置行号
     * @param rowNum
     */
    override fun setRowNum(rowNum: Int?) {
        this.importRowNum = rowNum
    }
}
