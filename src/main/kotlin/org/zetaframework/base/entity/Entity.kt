package org.zetaframework.base.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

/**
 * 包括id、create_time、create_by、update_by、update_time字段的表继承的基础实体
 *
 * @author gcc
 * @date 2021/10/18 下午2:22
 * @since 1.0.0
 */
abstract class Entity<T>(
    /** 最后修改时间 */
    @ApiModelProperty(value = "最后修改时间")
    @TableField(value = UPDATE_TIME_COLUMN, fill = FieldFill.INSERT_UPDATE)
    var updateTime: LocalDateTime? = null,

    /** 最后修改人ID */
    @ApiModelProperty(value = "最后修改人ID")
    @TableField(value = UPDATED_BY_COLUMN, fill = FieldFill.INSERT_UPDATE)
    var updatedBy: T? = null,
): SuperEntity<T>() {

    companion object {
        const val UPDATE_TIME = "updateTime"
        const val UPDATE_TIME_COLUMN = "update_time"
        const val UPDATED_BY = "updatedBy"
        const val UPDATED_BY_COLUMN = "updated_by"
    }

}
