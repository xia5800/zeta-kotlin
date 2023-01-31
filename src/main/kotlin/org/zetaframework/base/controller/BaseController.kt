package org.zetaframework.base.controller

import com.baomidou.mybatisplus.extension.service.IService

/**
 * 基础接口
 *
 * @param <Entity> 实体
 * @author gcc
 */
interface BaseController<Entity>: SuperBaseController {

    /**
     * 获取实体类型
     * @return Class<Entity>
     */
    fun getEntityClass(): Class<Entity>

    /**
     * 获取service
     */
    fun getBaseService(): IService<Entity>

}
