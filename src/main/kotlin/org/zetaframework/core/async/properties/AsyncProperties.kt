package org.zetaframework.core.async.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 异步线程配置
 *
 * @author gcc
 */
@ConfigurationProperties(prefix = AsyncProperties.PREFIX)
class AsyncProperties {
    companion object {
        const val PREFIX = "zeta.async"
    }

    /** 异步核心线程数，默认：10 */
    var corePoolSize = 10

    /** 异步最大线程数，默认：20 */
    var maxPoolSize = 20

    /** 队列容量，默认：1000 */
    var queueCapacity = 1000

    /** 线程存活时间，默认：300 */
    var keepAliveSeconds = 300

    /** 线程名前缀 */
    var threadNamePrefix = "zeta-async-executor-"

}
