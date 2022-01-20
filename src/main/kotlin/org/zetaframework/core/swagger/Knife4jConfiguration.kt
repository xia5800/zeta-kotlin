package org.zetaframework.core.swagger

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.zetaframework.core.swagger.properties.SwaggerProperties
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

/**
 * Knife4j 配置
 *
 * @author gcc
 */
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration::class)
@EnableConfigurationProperties(SwaggerProperties::class)
class Knife4jConfiguration(
    private var swaggerProperties: SwaggerProperties,
    private var openApiExtensionResolver: OpenApiExtensionResolver
) {

    @Bean
    fun defaultApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .host(swaggerProperties.host)
            .apiInfo(apiInfo())
            .groupName(swaggerProperties.group)
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.basePackage))
            .paths(PathSelectors.any())
            .build()
            // 赋予插件体系 主要是为了让 knife4j.setting配置生效
            .extensions(openApiExtensionResolver.buildExtensions(swaggerProperties.group))
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title(swaggerProperties.title)
            .description(swaggerProperties.description)
            .license(swaggerProperties.license)
            .licenseUrl(swaggerProperties.licenseUrl)
            .termsOfServiceUrl(swaggerProperties.termsOfServiceUrl)
            .contact(Contact(
                    swaggerProperties.contact?.name,
                    swaggerProperties.contact?.url,
                    swaggerProperties.contact?.email,
                )
            )
            .version(swaggerProperties.version)
            .build()
    }

}
