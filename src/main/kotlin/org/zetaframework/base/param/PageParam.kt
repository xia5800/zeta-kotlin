package org.zetaframework.base.param

import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

/**
 * 分页查询参数
 *
 * @author gcc
 */
@Schema(description = "分页查询参数")
class PageParam<T> private constructor(){
    /** 当前页 */
    @Schema(description = "当前页", example = "1", required = true)
    var page: Long = 1

    /** 每页显示条数 */
    @Schema(description = "每页显示条数", example = "10", required = true)
    var limit: Long = 10

    /** 查询条件 */
    @Schema(description = "查询条件", required = true)
    @Valid  // 见[docs/03功能介绍/参数校验.md]常见问题
    var model: T? = null

    /** 排序字段 */
    @Schema(description = "排序字段", allowableValues = ["id","createTime","updateTime"], example = "id", required = false)
    var sort: String? = "id"

    /** 排序规则 */
    @Schema(description = "排序规则", allowableValues = ["desc","asc"], example = "desc", required = false)
    var order: String? = "desc"


    constructor(page: Long, limit: Long): this() {
        this.page = page
        this.limit = limit
    }

    constructor(page: Long, limit: Long, model: T? = null): this(page, limit) {
        this.model = model
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

    /**
     * 排序字段别名处理
     *
     * 说明适用于下面这种情况：
     * ```
     * select t1.id, t2.name from order t1 left join user t2 on t1.user_id = t2.id
     * order by t1.id desc
     * ```
     *
     * 使用方式：
     * ```
     * // 查询条件
     * { "page": 1, "limit": 10, "model": { }, "order": "desc", "sort": "id" }
     *
     * // 使用
     * PageParam(1, 10, Order()).setSortAlias("t1.")
     * 或者
     * fun customPage(@RequestBody param: PageParam<QueryParam>) {
     *     param.setSortAlias("t1.")
     *     // ...
     * }
     *
     * // 实际生成sql
     * select t1.id, t2.name from order t1 left join user t2 on t1.user_id = t2.id
     * order by t1.id desc
     * ```
     * @param alias 别名 eg: "t1.","a.","order."
     */
    @JsonIgnore
    fun setSortAlias(alias: String) {
        val sortArr = StrUtil.splitToArray(this.sort, StrUtil.COMMA)
        if (sortArr.isNotEmpty()) {
            this.sort = sortArr.joinToString(StrUtil.COMMA) {
                if (!it.startsWith(alias)) {
                    "$alias$it"
                } else {
                    it
                }
            }
        }
    }

}
