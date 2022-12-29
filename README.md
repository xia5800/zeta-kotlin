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
| zeta-kotlin-module    | zeta-kotlin项目多模块版              | [gitee](https://gitee.com/xia5800/zeta-kotlin-module) [github](https://github.com/xia5800/springboot-kotlin-module)|

## 已有功能

- 用户管理
- 角色管理
- 操作日志
- 登录日志
- 数据字典
- 文件管理
- websocket
- XSS防护
- Ip2region离线IP地址查询

## 前端(无)

[~~zeta-kotlin-web~~](https://gitee.com/xia5800/zeta-kotlin-web) (写的太辣鸡了，不好意思放出来)


## 写在后面

本人的初衷只是想用kotlin写一个简单的curd项目练练手，可是后面写着写着就偏离了初心，于是便有了这个开源项目。

就如同上面简介中说的，本项目相当“精简”。所以我尽量控制项目功能数量，不想给本项目添加太多的功能和业务代码。

可惜事与愿违，还是添加了几个我本不想添加的功能。因为并非所有功能都是你的业务所需要的，我个人认为需要用到的时候再去开发与集成才是最适合的。

使用别人开发好的功能，它不一定适合你的业务，你只能按照它制定的规则去使用，不能灵活更改成符合业务需要的。

所以，你已经是一个成熟的程序员了，需要啥功能自己去实现吧。（笑


## 友情链接 & 特别鸣谢

- lamp-boot：[https://github.com/zuihou/lamp-boot](https://github.com/zuihou/lamp-boot)
- sa-token [https://sa-token.dev33.cn/](https://sa-token.dev33.cn/)
- mybatis-plus：[https://baomidou.com/](https://baomidou.com/)
- knife4j：[https://doc.xiaominfo.com/](https://doc.xiaominfo.com/)
- hutool：[https://hutool.cn/](https://hutool.cn/)
- Soybean Admin：[https://github.com/honghuangdc/soybean-admin](https://github.com/honghuangdc/soybean-admin)
