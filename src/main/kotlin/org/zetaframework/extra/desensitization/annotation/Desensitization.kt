package org.zetaframework.extra.desensitization.annotation

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.zetaframework.extra.desensitization.serializer.DesensitizationJsonSerializer

/**
 * 字段脱敏注解
 *
 * 说明：
 * 1.脱敏规则参考easypoi
 * 2.只能用于字段上
 * 3.配合jackson使用
 *
 * @author gcc
 */
@kotlin.annotation.Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@JacksonAnnotationsInside // 标记@Desensitization是一个Jackson复合注解
@JsonSerialize(using = DesensitizationJsonSerializer::class)
annotation class Desensitization(

    /**
     * # 脱敏规则
     *
     * **规则一**：采用保留头和尾的方式,中间数据按打码方式配置的字符进行打码
     *
     * 如：3_4 表示保留前3位以及后4位
     * ```
     * 3_4    13112311234 --> 131****1234
     * 6_4    370101123456781234 --> 370101********1234
     * ```
     *
     * **规则二**：采用确定隐藏字段的进行隐藏,优先保留头
     *
     * 如：1,3 表示最大隐藏3位,最小一位
     * ```
     *    李 -->  *
     *    李三 --> 李*
     *    张全蛋  --> 张*蛋
     *    李张全蛋 --> 李**蛋
     *    尼古拉斯.李张全蛋 -> 尼古拉***张全蛋
     *```
     *
     * **规则三**：特殊符号后保留
     *
     * 如：1~@ 表示只保留第一位和@之后的字段
     * ```
     * 1~@   alibaba@mail.com -> a********@mail.com
     * 3~#   236121678126381#2236 -> 236***********#2236
     *```
     *
     * 说明：
     * 脱敏规则无默认值，必填
     */
    val rule: String,

    /**
     * 打码方式，默认*号打码
     */
    val symbol: String = "*",


)
