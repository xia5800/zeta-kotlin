package org.zetaframework.core.swagger

import io.swagger.v3.oas.models.*
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.GlobalOpenApiCustomizer
import org.springdoc.core.customizers.GlobalOperationCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zetaframework.core.saToken.properties.TokenProperties
import org.zetaframework.core.swagger.properties.SwaggerProperties
import java.util.*

/**
 * Knife4j 配置
 *
 * 说明：
 * Knife4j 4.0.0版本开始弃用springfox,转而使用[springdoc](https://springdoc.org/#kotlin-support)
 * 因此导致Swagger配置发生了很大的变化，注解也发生了[变化](https://springdoc.org/#migrating-from-springfox)
 *
 * @author gcc
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties::class)
class Knife4jConfiguration(
    private var swaggerProperties: SwaggerProperties,
    private var tokenProperties: TokenProperties
) {

    /**
     * 自定义GlobalOpenApiCustomizer
     *
     * 说明：
     * 1.解决knife4j分组排序问题。 前提：@Tag(description = "") description 必须有值
     *
     * 分租排序生效的写法：
     * ```
     * @Tag(name = "登录认证", description = "1")          // 值越小排到越前面
     * @Tag(name = "用户管理", description = "2")
     * @Tag(name = "角色管理", description = "3.角色管理")  // 可以有中文
     * @Tag(name = "菜单管理", description = "菜单管理")    // 没有数字，则按照字母排序
     * ```
     *
     * springdoc分组排序，除了配置上面的，还要修改如下配置
     * ```
     * # springdoc-openapi-ui 配置
     * springdoc:
     *   swagger-ui:
     *     # 替换前端的排序方法。按description排序
     *     tags-sorter: sort((a, b) => a.description - b.description)
     * ```
     */
    @Bean
    fun sortTagsAlphabetically(): GlobalOpenApiCustomizer = GlobalOpenApiCustomizer { apiInfo ->
        // Tips: @Tag(description = "") description 必须有值apiInfo.tags的值才不为空
        // tags按照description的值进行从小到大的排序
        apiInfo.tags = apiInfo.tags?.filterNotNull()?.sortedBy { it.description }
    }

    /**
     * 自定义GlobalOperationCustomizer
     *
     * 说明：
     * 1.为每个接口配置SecurityRequirement，方便请求自动携带token。
     *   如果某个接口不想携带token，可以在方法上添加@SecurityRequirements注解。注意最末尾有个s
     */
    @Bean
    fun globalOperationCustomizer(): GlobalOperationCustomizer {
        return GlobalOperationCustomizer { operation, handlerMethod ->
            // 未被@SecurityRequirements注解的接口自动添加SecurityRequirement配置。
            // 说明：被@SecurityRequirements注解的接口，operation.security的值是一个不可变的list，如果往里面添加数据会抛出异常
            if (operation.security == null) {
                operation.addSecurityItem(SecurityRequirement().addList(tokenProperties.tokenName))
            }
            operation
        }
    }


    /**
     * SpringDoc OpenApi配置
     */
    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .components(Components().addSecuritySchemes(tokenProperties.tokenName, securityScheme()))
            .info(apiInfo())
            .externalDocs(externalDocs())
    }

    /**
     * 配置swagger文档信息
     */
    private fun apiInfo(): Info {
        return Info()
            .title(swaggerProperties.title)
            .version(swaggerProperties.version)
            .description(swaggerProperties.description)
            .termsOfService(swaggerProperties.termsOfServiceUrl)
            // 许可证信息
            .license(License().name(swaggerProperties.license).url(swaggerProperties.licenseUrl))
            // 联系人信息
            .contact(Contact()
                .name(swaggerProperties.contact?.name)
                .email(swaggerProperties.contact?.email)
                .url(swaggerProperties.contact?.url)
            )
    }

    /**
     * 外部文档配置
     *
     * 说明：
     * 这个配置目前只在swagger-ui.html页面生效
     */
    private fun externalDocs(): ExternalDocumentation {
        return ExternalDocumentation()
            .description("github")
            .url("https://github.com/xia5800/zeta-kotlin")
    }

    /**
     * 安全方案配置
     *
     * 说明：
     * 1.配置请求头中鉴权信息
     * 2.[文档](https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#security-scheme-object)
     */
    private fun securityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .name(tokenProperties.tokenName)
            .`in`(SecurityScheme.In.HEADER)
            .scheme("basic")
    }
}
