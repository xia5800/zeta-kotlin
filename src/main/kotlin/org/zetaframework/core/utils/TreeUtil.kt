package org.zetaframework.core.utils

import org.zetaframework.base.entity.TreeEntity
import java.io.Serializable

/**
 * List转tree工具类
 *
 * @author gcc
 * @since 1.1 修改为object单例类，使用方式和之前无任何区别
 */
object TreeUtil {

    /**
     * buildTree
     * @param treeList List<E>
     * @param isShowNullChildren Boolean 是否显示为空的children
     * @return List<E>
     */
    fun <E : TreeEntity<E, out Serializable>> buildTree(list: MutableList<E>, isShowNullChildren: Boolean = true): List<E> {
        if (list.isEmpty() || list.size == 0) { return list }

        for (entity in list) {
            // filter出 entity 的子节点
            val children = list.filter { entity.id == it.parentId }

            if(isShowNullChildren) {
                entity.children = children as MutableList<E>
            } else {
                if(children.isNotEmpty()) {
                    entity.children = children as MutableList<E>
                }
            }
        }

        // filter出 entity 的父节点
        val parentTreeList = list.filter {
            it.parentId == null || it.parentId.toString() == "" || it.parentId.toString() == "0"
        }

        return parentTreeList.ifEmpty { list }
    }


}
