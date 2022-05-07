package org.zetaframework.base.param

import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 分页查询参数
 *
 * @author gcc
 */
@ApiModel(description = "分页查询参数")
class PageParam<T> private constructor(){

    @ApiModelProperty(value = "当前页", example = "0")
    var page: Long = 0

    @ApiModelProperty(value = "每页显示条数", example = "10")
    var limit: Long = 10

    @ApiModelProperty(value = "查询条件")
    var model: T? = null

    @ApiModelProperty(value = "排序字段", allowableValues = "id,createTime,updateTime", example = "id")
    var sort: String? = null

    @ApiModelProperty(value = "排序规则", allowableValues = "desc,asc", example = "desc")
    var order: String? = null


    constructor(page: Long, limit: Long): this() {
        this.page = page
        this.limit = limit
    }


    /**
     * 构建分页对象
     * @return IPage<E>
     */
    fun <E> buildPage(): IPage<E> {
        val page: Page<E> = Page<E>(this.page, this.limit)

        // 判断是否有排序
        if(StrUtil.isBlank(this.sort)) {
            return page
        }

        // 处理排序
        val orders: MutableList<OrderItem> = mutableListOf()
        val sortArr = StrUtil.splitToArray(this.sort, StrUtil.COMMA)
        val orderArr = StrUtil.splitToArray(this.order, StrUtil.COMMA)
        for (i in sortArr.indices) {
            // bug fix: 驼峰转下划线  说明：忘记处理了orz --by gcc
            val sortField = StrUtil.toUnderlineCase(sortArr[i])
            orders.add(
                if (StrUtil.equalsAny(orderArr[i], "asc", "ascending")) OrderItem.asc(sortField) else OrderItem.desc(sortField)
            )
        }
        page.orders = orders
        return page
    }

}
