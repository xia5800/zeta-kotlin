package org.zetaframework.core.async

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.zetaframework.core.async.properties.AsyncProperties
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy


/**
 * 异步线程配置
 *
 * @author gcc
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncProperties::class)
class AsyncConfiguration(private val asyncProperties: AsyncProperties): AsyncConfigurer {

    /**
     * 配置自定义线程池
     *
     * 配置之后@Async注解会默认使用这个线程池
     */
    @Bean("taskExecutor")
    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        // 核心线程数
        executor.corePoolSize = asyncProperties.corePoolSize
        // 最大线程数
        executor.maxPoolSize = asyncProperties.maxPoolSize
        // 队列大小
        executor.queueCapacity = asyncProperties.queueCapacity
        // 线程最大空闲时间
        executor.keepAliveSeconds = asyncProperties.keepAliveSeconds
        // 线程名前缀
        executor.setThreadNamePrefix(asyncProperties.threadNamePrefix)
        // 拒绝策略
        /*
         * ThreadPoolExecutor.AbortPolicy 丢弃任务并抛出RejectedExecutionException异常(默认)。
         * ThreadPoolExecutor.DiscardPolicy 丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy 丢弃队列最前面的任务，然后重新尝试执行任务
         * ThreadPoolExecutor.CallerRunsPolicy 由调用线程处理该任务
         */
        executor.setRejectedExecutionHandler(CallerRunsPolicy())
        // 初始化线程池
        executor.initialize()
        return executor
    }

    /**
     * 配置异步线程未捕获异常处理器
     */
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return SimpleAsyncUncaughtExceptionHandler()
    }

}
