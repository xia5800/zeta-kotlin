package org.zetaframework.core.mybatisplus.generator

import cn.hutool.core.lang.Snowflake
import cn.hutool.core.util.IdUtil

/**
 * 基于Hutool工具类实现的雪花id生成器
 *
 * 说明：参考lamp-util项目HuToolUidGenerator类实现
 * @author zuihou
 * @author gcc
 */
class HuToolUidGenerator(workerId: Long, datacenterId: Long): UidGenerator {
    private var snowflake: Snowflake = IdUtil.getSnowflake(workerId, datacenterId)

    /**
     * 获取id
     */
    override fun getUid(): Long = snowflake.nextId()

    /**
     * 解析uid
     *
     * @param uid
     */
    override fun parseUid(uid: Long): String {
        val workerId = snowflake.getWorkerId(uid)
        val dataCenterId = snowflake.getDataCenterId(uid)
        val timestamp = snowflake.getGenerateDateTime(uid)
        return """{"UID":"$uid","timestamp":"$timestamp","workerId":"$workerId","dataCenterId":"$dataCenterId"}"""
    }
}
