# token配置

以下是token相关的配置
```yaml
zeta:
  # token配置
  token:
    # 请求头中token参数的名字 token: xxxxx
    tokenName: token
    # token类型 DEFAULT、STYLE、MIX、STATELESS
    type: DEFAULT
    # jwt签名前缀 例如填写 Bearer 实际传参 token: Bearer xxxxxx
    prefix:
    # jwt签名加密key 仅当type不为DEFAULT时有效
    signerKey: zeta-kotlin
    # token过期时间 单位：秒, -1代表永不过期
    expireTime: 7200

# Sa-Token配置 https://sa-token.dev33.cn/doc/index.html#/use/config
sa-token:
  # token名称 (同时也是cookie名称)
  token-name: ${zeta.token.tokenName}
  # token有效期 1天，单位s 默认30天, -1代表永不过期
  timeout: ${zeta.token.expireTime}
  # jwt秘钥 仅当zeta.token.type不为DEFAULT时有效
  jwt-secret-key: ${zeta.token.signerKey}
  # token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
  activity-timeout: -1
  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
  is-concurrent: true
  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
  is-share: false
  # 是否尝试从 cookie 里读取 Token
  is-read-cookie: false
  # 是否输出操作日志
  is-log: false
  # 是否在初始化配置时打印版本字符画
  is-print: false
  # 自定义token样式
  token-style: tik
```

## zeta.token.tokenName 
配置说明: 

对应前端请求头中存放token值的参数名字

例如：

zeta.token.tokenName = access_token

```
请求标头
Accept: */*
...
access_token: we_HsCE7dLfyxuM1N_f1wEXck9gw31yRrD__
Host: 127.0.0.1:8080
User-Agent: xxxx
```

## zeta.token.type
配置说明：

用来改变token的外貌，DEFAULT是短短的token，非DEFAULT是jwt风格的token。

当zeta.token.type的值为DEFAULT时， 我们可以修改sa-token.token-style的值来改变token值的样式

sa-token样式的说明见文档[token-style](https://sa-token.dev33.cn/doc/index.html#/up/token-style)

zeta.token.type的选项说明见`org.zetaframework.core.saToken.enums.TokenTypeEnum.kt`有详细注释说明



## zeta.token.prefix
配置说明：
1. zeta.token.type的值不为DEFAULT时生效
2. jwt签名前缀 例如填写 Bearer 实际传参 token: Bearer xxxxxx

sa-token前缀见说明文档[token-prefix](https://sa-token.dev33.cn/doc/index.html#/up/token-prefix)



## zeta.token.signerKey

配置说明：
1. zeta.token.type的值不为DEFAULT时生效
2. jwt签名加密用的



## zeta.token.expireTime

配置说明：
1. token过期时间 单位：秒, -1代表永不过期
2. 前端建议和后端保持一致
