package org.zetaframework.base.controller

import com.baomidou.mybatisplus.extension.service.IService
import java.io.Serializable
import java.lang.reflect.ParameterizedType

/**
 * 完整增删改查 BaseController
 *
 * 实现了Query、Save、Update、Delete、BatchDelete
 *
 * @param <S>           Service
 * @param <Id>          主键字段类型
 * @param <Entity>      实体
 * @param <QueryParam>  查询参数
 * @param <SaveDTO>     保存对象
 * @param <UpdateDTO>   修改对象
 * @author gcc
 */
abstract class SuperController<S: IService<Entity>, Id: Serializable, Entity, QueryParam, SaveDTO, UpdateDTO> :
    SuperSimpleController<S, Entity>(),
    QueryController<Entity, Id, QueryParam>,
    SaveController<Entity, SaveDTO>,
    UpdateController<Entity, UpdateDTO>,
    DeleteController<Entity, Id>
{

    /**
     * 获取实体类型
     * @return Class<Entity>
     */
    override fun getEntityClass(): Class<Entity> {
        if (super.clazz == null) {
            super.clazz =
                (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[2] as Class<Entity>
        }
        return super.clazz!!
    }
}
