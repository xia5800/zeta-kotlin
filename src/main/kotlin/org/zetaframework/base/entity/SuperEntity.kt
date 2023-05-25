package org.zetaframework.base.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.zetaframework.core.validation.group.Update
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 包括id、create_time、create_by字段的表继承的基础实体
 *
 * @author gcc
 * @date 2021/10/18 下午2:23
 * @since 1.0.0
 */
abstract class SuperEntity<T>(
    /** id */
    @TableId(value = FIELD_ID, type = IdType.INPUT)
    @Schema(description = "主键")
    @get:NotNull(message = "id不能为空",groups = [Update::class])
    open var id: T? = null,

    /** 创建时间 */
    @Schema(description = "创建时间")
    @TableField(value = CREATE_TIME_COLUMN, fill = FieldFill.INSERT)
    open var createTime: LocalDateTime? = null,

    /** 创建人ID */
    @Schema(description = "创建人ID")
    @TableField(value = CREATED_BY_COLUMN, fill = FieldFill.INSERT)
    open var createdBy: T? = null,
): Serializable {

    companion object {
        const val FIELD_ID = "id"
        const val CREATE_TIME = "createTime"
        const val CREATE_TIME_COLUMN = "create_time"
        const val CREATED_BY = "createdBy"
        const val CREATED_BY_COLUMN = "created_by"
    }

}
