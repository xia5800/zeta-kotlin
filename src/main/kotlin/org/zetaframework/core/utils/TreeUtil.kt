package org.zetaframework.core.utils

import org.zetaframework.base.entity.ITree
import java.io.Serializable

/**
 * List转tree工具类
 *
 * @author gcc
 * @since 1.1 修改为object单例类，使用方式和之前无任何区别
 * @since 1.2 修改为只要实现了ITree接口的类，都能转换为TreeList
 *            完善注释
 */
object TreeUtil {

    /**
     * buildTree
     *
     * @param list List<TreeEntity>
     * @param isShowNullChildren Boolean 是否显示为空的children
     * @return List<E>
     */
    fun <E: ITree<E, out Serializable>> buildTree(list: MutableList<E>, isShowNullChildren: Boolean = true): List<E> {
        if (list.isEmpty() || list.size == 0) { return list }

        /**
         * 将
         * [
         *  { id: 1, pid: 0, name: "1" },
         *  { id: 2, pid: 1, name: "1-1" },
         *  { id: 3, pid: 1, name: "1-2" },
         *  { id: 4, pid: 0, name: "2" },
         * ]
         *
         * 转换为：
         * [
         *   { id: 1, pid: 0, name: "1", children: [{ id: 2, pid: 1, name: "1-1" }, { id: 3, pid: 1, name: "1-2" }] },
         *   { id: 2, pid: 1, name: "1-1", children: [] },
         *   { id: 3, pid: 1, name: "1-2", children: [] },
         *   { id: 4, pid: 0, name: "2", children: [] },
         * ]
         */
        for (entity in list) {
            // filter出 entity 的子节点。eg: entity.id = 1, children = [{ id: 2, pid: 1, name: "1-1" }, { id: 3, pid: 1, name: "1-2" }]
            val children = list.filter { entity.getTreeId() == it.getTreeParentId() }

            // eg: { id: 1, pid: 0, name: "1", children: [{ id: 2, pid: 1, name: "1-1" }, { id: 3, pid: 1, name: "1-2" }] },
            if (isShowNullChildren) {
                entity.setTreeChildren(children as MutableList<E>)
            } else {
                if (children.isNotEmpty()) {
                    entity.setTreeChildren(children as MutableList<E>)
                }
            }
        }

        /**
         * filter出 entity 的父节点
         *
         * 得到：
         * [
         *   { id: 1, pid: 0, name: "1", children: [{ id: 2, pid: 1, name: "1-1" }, { id: 3, pid: 1, name: "1-2" }] },
         *   { id: 4, pid: 0, name: "2", children: [] },
         * ]
         */
        val parentTreeList = list.filter {
            it.getTreeParentId() == null
            || it.getTreeParentId().toString() == ""
            || it.getTreeParentId().toString() == "0"
        }

        // 如果parentTreeList不为空，返回parentTreeList，否则返回list。
        return parentTreeList.ifEmpty { list }
    }


}
