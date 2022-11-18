package org.zetaframework.core.saToken.enums

/**
 * token类型枚举
 * 详见：<a href="https://sa-token.dev33.cn/doc/index.html#/plugin/jwt-extend?id=%e5%92%8c-jwt-%e9%9b%86%e6%88%90">sa-token doc</a>
 * @author gcc
 */
enum class TokenTypeEnum {
    /**
     * sa-token [非jwt模式]： 通过sa-token.token-style=xxx来控制token风格
     * 默认可配置的风格有：uuid、simple-uuid、random-32、random-64、random-128、tik
     */
    DEFAULT,

    /**
     * sa-token  [jwt-simple模式]：Token 风格替换
     */
    SIMPLE,

    /**
     * sa-token  [jwt-mixin模式]：jwt 与 Redis 逻辑混合
     */
    MIXIN,

    /**
     * sa-token  [jwt-stateless模式]：完全舍弃Redis，只用jwt
     */
    STATELESS
}
