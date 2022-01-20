package org.zetaframework.core.mybatisplus.properties

import com.baomidou.mybatisplus.annotation.DbType
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 数据源配置参数
 * @author gcc
 */
@ConfigurationProperties(prefix = "zeta.database")
class DatabaseProperties(
    /** 是否启用 防止全表更新与删除插件 */
    var isBlockAttack: Boolean = false,

    /** 是否启用 sql性能规范插件 */
    var isIllegalSql: Boolean = false,

    /** 分页大小限制 */
    var maxLimit: Long = -1,

    /** 数据库类型 */
    var dbType: DbType = DbType.MYSQL,

    /** 溢出总页数后是否进行处理 */
    var overflow: Boolean = true,

    /** 是否生成countSql优化掉left join */
    var optimizeJoin: Boolean = true,

    /** hutoolId生成配置 */
    var hutoolId: HutoolId = HutoolId()
) {

    data class HutoolId (
        /**
         * 终端ID (0-31)      单机配置0 即可。 集群部署，根据情况每个实例自增即可。
         */
        var workerId: Long = 0L,

        /**
         * 数据中心ID (0-31)   单机配置0 即可。 集群部署，根据情况每个实例自增即可。
         */
        var dataCenterId: Long = 0L,
    )
}
