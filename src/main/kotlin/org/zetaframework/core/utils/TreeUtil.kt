package org.zetaframework.core.utils

import org.zetaframework.base.entity.TreeEntity
import java.io.Serializable

/**
 * List转tree工具类
 *
 * @author gcc
 */
class TreeUtil private constructor(){

    companion object {

        /**
         * buildTree
         * @param treeList List<E>
         * @return List<E>
         */
        fun <E : TreeEntity<E, out Serializable>> buildTree(list: MutableList<E>): List<E> {
            if (list.isEmpty() || list.size == 0) { return list }

            for (entity in list) {
                // filter出 entity 的子节点
                val children = list.filter { entity.id == it.parentId }
                entity.children = children as MutableList<E>
            }

            // filter出 entity 的父节点
            val parentTreeList = list.filter {
                it.parentId == null || it.parentId.toString() == "" || it.parentId.toString() == "0"
            }

            return parentTreeList.ifEmpty { list }
        }

    }


}
