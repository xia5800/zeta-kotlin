package org.zetaframework.core.mybatisplus

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
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

    /**
     * mybatis插件配置
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

        // 防止全表更新与删除
        if(databaseProperties.isBlockAttack) {
            interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        }

        // sql性能规范
        if(databaseProperties.isIllegalSql) {
            interceptor.addInnerInterceptor(IllegalSQLInnerInterceptor())
        }
        return interceptor
    }

    /**
     * id生成策略
     * @return UidGenerator
     */
    @Bean
    fun getUidGenerator(): UidGenerator {
        return HuToolUidGenerator(databaseProperties.hutoolId.workerId, databaseProperties.hutoolId.dataCenterId)
    }

}
