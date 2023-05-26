package org.zetaframework.base.result

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 分页查询返回结果
 *
 * @author gcc
 */
@Schema(description = "分页查询返回结果")
class PageResult<T> private constructor(){

    /** 当前页数据 */
    @Schema(description = "当前页数据")
    var list: List<T>? = mutableListOf()

    /** 总数量 */
    @Schema(description = "总数量")
    var count: Long? = null


    constructor(list: List<T>, count: Long = 0) : this() {
        this.list = list
        this.count = count
    }

}
