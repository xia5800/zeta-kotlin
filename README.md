# zeta-kotlin 基础开发框架

## 简介
zeta-kotlin是使用kotlin语言基于`spring boot`、`mybatis-plus`、`sa-token`等框架开发的项目脚手架。

zeta-kotlin目前只提供了一个最基础的RBAC用户角色权限功能。不像其它开源项目那样大而全，zeta-kotlin项目相当精简。

## 项目结构

| 包                         | 说明                                                                    |
| -------------------------- | ---------------------------------------------------------------------- |
| com.zeta                  | 业务包，专注于业务代码的编写                                               |
| org.zetaframework         | zeta框架核心配置包，包含sa-token、redis、mybatis-plus、knife4j等框架的配置   |


## 技术选型

| 技术                       | 名称                                                         |
| -------------------------- | ------------------------------------------------------------ |
| spring boot                | 核心框架                                                     |
| sa-token                   | 权限认证框架                                                     |
| mybatis-plus               | [MyBatis扩展](https://doc.xiaominfo.com/)                      |
| Redis                      | 分布式缓存数据库                                             |
| knife4j                    | [一个增强版本的Swagger 前端UI](https://doc.xiaominfo.com/knife4j/)  |
| hutool                     | [Java工具类大全](https://hutool.cn/docs/#/)                  |
| RedisUtil                  | [最全的Java操作Redis的工具类](https://gitee.com/whvse/RedisUtil) |

## 配套项目

| 名称                  | 说明                                  | 项目地址                                                     |
| --------------------- | ------------------------------------- | ------------------------------------------------------------ |
| zeta-kotlin-generator | 专门为zeta-kotlin项目定做的代码生成器 | [gitee](https://gitee.com/xia5800/zeta-kotlin-generator)  [github](https://github.com/xia5800/zeta-kotlin-generator) |
| zeta-kotlin-web       | zeta-kotlin项目的前端               | [gitee](https://gitee.com/xia5800/zeta-kotlin-web) |
| zeta-kotlin-module    | zeta-kotlin项目多模块版              | [gitee](https://gitee.com/xia5800/zeta-kotlin-module) |

## 待办
- [X] 代码生成器
- [X] 操作日志
- [X] 登录日志
- [X] websocket
- [X] 文件管理
- [X] 数据字典
- [ ] ~~定时任务~~ 
    - [ ] 不打算集成在本项目，请参考zeta-kotlin-demo(名称暂定)项目
- [ ] ~~国际化~~ 
    - [ ] 不打算集成在本项目，请参考zeta-kotlin-demo(名称暂定)项目

## 前端

[zeta-kotlin-web](https://gitee.com/xia5800/zeta-kotlin-web)


## 友情链接 & 特别鸣谢

- lamp-boot：[https://github.com/zuihou/lamp-boot](https://github.com/zuihou/lamp-boot)
- sa-token [https://sa-token.dev33.cn/](https://sa-token.dev33.cn/)
- mybatis-plus：[https://baomidou.com/](https://baomidou.com/)
- knife4j：[https://doc.xiaominfo.com/](https://doc.xiaominfo.com/)
- hutool：[https://hutool.cn/](https://hutool.cn/)
- Soybean Admin：[https://github.com/honghuangdc/soybean-admin](https://github.com/honghuangdc/soybean-admin)
