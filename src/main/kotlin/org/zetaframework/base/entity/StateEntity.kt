package org.zetaframework.base.entity

import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

/**
 * 带状态字段的 实体类
 * 包括id、create_time、create_by、update_by、update_time、state字段的表继承的基础实体
 *
 * 说明：
 * 用于前端修改数据状态
 * @author gcc
 * @date 2021/10/18 下午2:22
 * @since 1.0.0
 */
abstract class StateEntity<T: Serializable, U: Serializable>(
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @TableField(value = "state")
    open var state: U? = null
): Entity<T>()
