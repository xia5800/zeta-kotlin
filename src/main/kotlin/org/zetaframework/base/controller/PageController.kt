package org.zetaframework.base.controller

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import org.zetaframework.base.param.PageParam
import org.zetaframework.base.result.PageResult

/**
 * 分页 Controller
 *
 * @param <Entity>     实体
 * @param <QueryParam>  查询参数
 * @author gcc
 */
interface PageController<Entity, QueryParam>: BaseController<Entity> {

    /**
     * 分页查询
     *
     * @param param PageParam<QueryParam>
     * @return IPage<Entity>
     */
    fun query(param: PageParam<QueryParam>): PageResult<Entity> {
        // 处理查询参数
        handlerQueryParams(param)

        // 构建分页对象
        val page: IPage<Entity> = param.buildPage<Entity>()
        // PageQuery -> Entity
        val model: Entity = BeanUtil.toBean(param.model, getEntityClass())

        // 构造分页查询条件
        val wrapper = handlerWrapper(model, param)
        // 执行单表分页查询
        getBaseService().page(page, wrapper)

        // 处理查询后的分页结果
        handlerResult(page)

        return PageResult(page.records, page.total)
    }


    /**
     * 构造查询条件
     *
     * @param model Entity?
     * @param param PageParams<PageQuery>
     * @return QueryWrapper<Entity>
     */
    fun handlerWrapper(model: Entity?, param: PageParam<QueryParam>): QueryWrapper<Entity> {
        // ?.let 不为空执行
        return model?.let { QueryWrapper<Entity>(model) } ?: QueryWrapper<Entity>()
    }


    /**
     * 处理查询参数
     *
     * @param param 查询参数
     */
    fun handlerQueryParams(param: PageParam<QueryParam>) { }

    /**
     * 处理查询后的数据
     *
     * @param page IPage<Entity>
     */
    fun handlerResult(page: IPage<Entity>) { }

}
