package org.zetaframework.core.saToken.spring

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.stereotype.Component

/**
 * 自定义BeanDefinitionRegistryPostProcessor
 *
 * 说明：
 * 为了`覆盖、替换`第三方jar包里面已经定义好的bean，可以在这里处理
 *
 * @author gcc
 */
@Component
class MyBeanDefinitionRegistryPostProcessor: BeanDefinitionRegistryPostProcessor {

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        // 要替换的Bean的名字
        val beanName = "getSaTokenContextForSpringInJakartaServlet"

        // 先移除原来的Bean定义
        registry.removeBeanDefinition(beanName)

        // 注册我们自定义的Bean
        val rootBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(CustomSaTokenContext::class.java)
        registry.registerBeanDefinition(beanName, rootBeanDefinition.beanDefinition)
    }

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {}

}