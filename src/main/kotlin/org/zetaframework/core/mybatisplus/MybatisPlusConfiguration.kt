package org.zetaframework.core.mybatisplus

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.zetaframework.core.mybatisplus.generator.HuToolUidGenerator
import org.zetaframework.core.mybatisplus.generator.UidGenerator
import org.zetaframework.core.mybatisplus.properties.DatabaseProperties

/**
 * mybatis plus 配置类
 *
 * @author gcc
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DatabaseProperties::class)
class MybatisPlusConfiguration(private val databaseProperties: DatabaseProperties) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * mybatis插件配置
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()

        // 分页插件配置
        val paginationInnerInterceptor = PaginationInnerInterceptor()
        paginationInnerInterceptor.isOverflow = databaseProperties.overflow
        paginationInnerInterceptor.maxLimit = databaseProperties.maxLimit
        paginationInnerInterceptor.dbType = databaseProperties.dbType
        paginationInnerInterceptor.isOptimizeJoin = databaseProperties.optimizeJoin
        interceptor.addInnerInterceptor(paginationInnerInterceptor)

        // 乐观锁插件
        if (databaseProperties.isOptimisticLocker) {
            logger.info("mybatis-plus乐观锁插件：启用")
            interceptor.addInnerInterceptor(OptimisticLockerInnerInterceptor())
        }

        // 防止全表更新与删除
        if (databaseProperties.isBlockAttack) {
            logger.info("mybatis-plus防止全表更新与删除插件：启用")
            interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        }

        // sql性能规范
        if (databaseProperties.isIllegalSql) {
            logger.info("mybatis-plus sql性能规范插件：启用")
            interceptor.addInnerInterceptor(IllegalSQLInnerInterceptor())
        }
        return interceptor
    }

    /**
     * id生成策略
     *
     * @return UidGenerator
     */
    @Bean
    fun getUidGenerator(): UidGenerator {
        return HuToolUidGenerator(databaseProperties.hutoolId.workerId, databaseProperties.hutoolId.dataCenterId)
    }

}
