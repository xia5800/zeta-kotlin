package org.zetaframework.core.mybatisplus.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import org.zetaframework.base.entity.Entity
import org.zetaframework.base.entity.SuperEntity
import org.zetaframework.core.mybatisplus.generator.UidGenerator
import org.zetaframework.core.utils.ContextUtil
import java.time.LocalDateTime

/**
 * 自动填充处理
 * @author gcc
 */
@Component
class MybatisPlusMetaObjectHandler(private val uidGenerator: UidGenerator): MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    override fun insertFill(metaObject: MetaObject) {
        fillCreated(metaObject)
        fillUpdated(metaObject)
        fillId(metaObject)
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    override fun updateFill(metaObject: MetaObject) {
        fillUpdated(metaObject)
    }


    /**
     * 填充创建时间、创建人
     * @param metaObject MetaObject
     */
    private fun fillCreated(metaObject: MetaObject) {
        // 有createTime字段，且字段值为null
        if (metaObject.hasGetter(SuperEntity.CREATE_TIME)) {
            val value = metaObject.getValue(SuperEntity.CREATE_TIME)
            // 设置创建时间
            if (value == null) {
                setFieldValByName(SuperEntity.CREATE_TIME, LocalDateTime.now(), metaObject)
            }
        }

        // 有createdBy字段，且字段值为null
        if (metaObject.hasGetter(SuperEntity.CREATED_BY)) {
            val value = metaObject.getValue(SuperEntity.CREATED_BY)
            // 设置创建人
            if (value == null) {
                // 判断创建人字段的类型
                val createBy: Any = when (metaObject.getGetterType(SuperEntity.CREATED_BY).name) {
                    "java.lang.String" -> ContextUtil.getUserId().toString()
                    // 雪花算法生成的id长度超过了Int类型的长度，故此处toInt()会丢失精度。建议实体类主键类型为Int的时候，不继承SuperEntity或Entity。
                    // "java.lang.Integer" -> ContextUtil.getUserId().toInt()
                    else -> ContextUtil.getUserId()
                }
                setFieldValByName(SuperEntity.CREATED_BY, createBy, metaObject)
            }
        }
    }

    /**
     * 填充修改时间修改人
     * @param metaObject MetaObject
     */
    private fun fillUpdated(metaObject: MetaObject) {
        // 有updateTime字段，且字段值为null
        if (metaObject.hasGetter(Entity.UPDATE_TIME)) {
            val value = metaObject.getValue(Entity.UPDATE_TIME)
            if (value == null) {
                setFieldValByName(Entity.UPDATE_TIME, LocalDateTime.now(), metaObject)
            }
        }

        // 有updatedBy字段，且字段值为null
        if (metaObject.hasGetter(Entity.UPDATED_BY)) {
            val value = metaObject.getValue(Entity.UPDATED_BY)
            // 设置用户id
            if (value == null) {
                // 判断修改人字段的类型
                val updateBy: Any = when (metaObject.getGetterType(Entity.UPDATED_BY).name) {
                    "java.lang.String" -> ContextUtil.getUserId().toString()
                    // 雪花算法生成的id长度超过了Int类型的长度，故此处toInt()会丢失精度。建议实体类主键类型为Int的时候，不继承SuperEntity或Entity。
                    // "java.lang.Integer" -> ContextUtil.getUserId().toInt()
                    else -> ContextUtil.getUserId()
                }
                setFieldValByName(Entity.UPDATED_BY, updateBy, metaObject)
            }
        }
    }

    /**
     * 填充主键
      * @param metaObject MetaObject
     */
    private fun fillId(metaObject: MetaObject) {
        // 有Id字段，且字段值为null
        if (metaObject.hasGetter(SuperEntity.FIELD_ID)) {
            val value = metaObject.getValue(SuperEntity.FIELD_ID)
            if (value == null) {
                // 判断Id字段的类型
                val id: Any = when (metaObject.getGetterType(SuperEntity.FIELD_ID).name) {
                    "java.lang.String" -> uidGenerator.getUid().toString()
                    // 雪花算法生成的id长度超过了Int类型的长度，故此处toInt()会丢失精度。建议实体类主键类型为Int的时候，不继承SuperEntity或Entity。
                    // "java.lang.Integer" -> uidGenerator.getUid().toInt()
                    else -> uidGenerator.getUid()
                }
                setFieldValByName(SuperEntity.FIELD_ID, id, metaObject)
            }
        }
    }
}
