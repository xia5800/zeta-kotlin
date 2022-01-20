package org.zetaframework.base.entity

import com.baomidou.mybatisplus.annotation.SqlCondition
import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import javax.validation.constraints.NotEmpty

/**
 * 树形表结构 实体类
 * 包括id、create_time、create_by、update_by、update_time、label、parent_id、sort_value 字段的表继承的树形实体
 *
 * @author gcc
 */
abstract class TreeEntity<E, T: Serializable>(
    /** 名称 */
    @ApiModelProperty(value = "名称")
    @get:NotEmpty(message = "名称不能为空")
    @TableField(value = "label", condition = SqlCondition.LIKE)
    var label: String? = null,

    /** 父级Id */
    @ApiModelProperty(value = "父级Id")
    @TableField(value = "parent_id")
    var parentId: T? = null,

    /** 排序 */
    @ApiModelProperty(value = "排序")
    @TableField(value = "sort_value")
    var sortValue: Int? = null,

    /** 子节点 */
    @ApiModelProperty(value = "子节点")
    @TableField(exist = false)
    var children: MutableList<E> = mutableListOf()
): Entity<T>()
