package org.zetaframework.core.mybatisplus.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.zetaframework.base.entity.Entity
import org.zetaframework.base.entity.SuperEntity
import org.zetaframework.core.mybatisplus.generator.UidGenerator
import org.zetaframework.core.utils.ContextUtil
import java.time.LocalDateTime
import com.baomidou.mybatisplus.annotation.FieldFill as FieldFill

/**
 * 自动填充处理
 * @author gcc
 */
@Component
class MybatisPlusMetaObjectHandler(private val uidGenerator: UidGenerator): MetaObjectHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

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
     *
     * 说明：
     * 字段上未设置填充方式 fill = [FieldFill].INSERT也能进行填充
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
                when (metaObject.getGetterType(SuperEntity.CREATED_BY).name) {
                    "java.lang.String" -> {
                        setFieldValByName(SuperEntity.CREATED_BY, ContextUtil.getUserIdStr(), metaObject)
                    }
                    "java.lang.Long" -> {
                        setFieldValByName(SuperEntity.CREATED_BY, ContextUtil.getUserId(), metaObject)
                    }
                    else -> {
                        logger.warn("【${SuperEntity.CREATED_BY_COLUMN}】字段仅支持String和Long类型填充，不是这两种类型的请插入时手动设置值")
                    }
                }
            }
        }
    }

    /**
     * 填充修改时间修改人
     *
     * 说明：
     * 字段上未设置填充方式 fill = [FieldFill].UPDATE也能进行填充
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
            when (metaObject.getGetterType(Entity.UPDATED_BY).name) {
                "java.lang.String" -> {
                    setFieldValByName(Entity.UPDATED_BY, ContextUtil.getUserIdStr(), metaObject)
                }
                "java.lang.Long" -> {
                    setFieldValByName(Entity.UPDATED_BY, ContextUtil.getUserId(), metaObject)
                }
                else -> {
                    logger.warn("【${Entity.UPDATE_TIME_COLUMN}】字段仅支持String和Long类型填充，不是这两种类型的请更新时手动设置值")
                }
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
            when (metaObject.getGetterType(SuperEntity.FIELD_ID).name) {
                "java.lang.String" -> {
                    setFieldValByName(SuperEntity.FIELD_ID, uidGenerator.getUid().toString(), metaObject)
                }
                "java.lang.Long" -> {
                    setFieldValByName(SuperEntity.FIELD_ID, uidGenerator.getUid(), metaObject)
                }
                else -> {
                    logger.warn("【${SuperEntity.FIELD_ID}】字段仅支持String和Long类型填充，不是这两种类型的请手动设置值")
                }
            }
        }
    }
}
