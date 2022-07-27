package org.zetaframework.base.result

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 分页查询返回结果
 *
 * @author gcc
 */
@ApiModel(description = "分页查询返回结果")
class PageResult<T> private constructor(){

    @ApiModelProperty(value = "当前页数据")
    var list: List<T>? = mutableListOf()

    @ApiModelProperty(value = "总数量")
    var count: Long? = null


    constructor(list: List<T>, count: Long = 0) : this() {
        this.list = list
        this.count = count
    }

}
