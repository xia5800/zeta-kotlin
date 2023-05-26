package org.zetaframework.core.mybatisplus.generator

/**
 * Uid生成器 接口类
 *
 * 说明：参考lamp-util项目UidGenerator类实现
 * @author yutianbao
 * @author zuihou
 * @author gcc
 */
interface UidGenerator {

    /**
     * 获取id
     */
    fun getUid(): Long

    /**
     * 解析uid
     *
     * @param uid
     */
    fun parseUid(uid: Long): String

}
