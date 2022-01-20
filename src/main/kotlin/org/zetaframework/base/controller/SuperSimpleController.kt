package org.zetaframework.base.controller

import com.baomidou.mybatisplus.extension.service.IService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.lang.reflect.ParameterizedType

/**
 * 简单实现的BaseController
 *
 * 无Query、Save、Update、Delete等实现，需手动实现
 * @author gcc
 */
abstract class SuperSimpleController<S: IService<Entity>, Entity> : BaseController<Entity> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    protected lateinit var service: S

    var clazz: Class<Entity>? = null

    /**
     * 获取实体类型
     * @return Class<Entity>
     */
    override fun getEntityClass(): Class<Entity> {
        if (clazz == null) {
            clazz =
                (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<Entity>
        }
        return clazz!!
    }

    /**
     * 获取service
     */
    override fun getBaseService(): IService<Entity> {
        return service
    }

}
