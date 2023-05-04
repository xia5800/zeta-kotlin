package org.zetaframework.base.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable

/**
 * 树接口
 *
 * 说明：
 * 实现了该接口的，可以被转成树
 *
 * @author gcc
 */
interface ITree<E, T: Serializable> {

    /**
     * 获取树节点id
     */
    @JsonIgnore
    fun getTreeId(): T?

    /**
     * 获取树父节点id
     */
    @JsonIgnore
    fun getTreeParentId(): T?

    /**
     * 设置树子级
     */
    @JsonIgnore
    fun setTreeChildren(children: MutableList<E>?)

}
