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


## 待办
- [ ] 文件管理
- [ ] 操作记录
- [ ] 登录记录
- [ ] 数据字典
- [ ] 定时任务
- [ ] websocket
- [ ] 国际化

## 前端

暂无


## 友情链接 & 特别鸣谢

- lamp-boot：[https://github.com/zuihou/lamp-boot](https://github.com/zuihou/lamp-boot)
- sa-token [https://sa-token.dev33.cn/](https://sa-token.dev33.cn/)
- mybatis-plus：[https://baomidou.com/](https://baomidou.com/)
- knife4j：[https://doc.xiaominfo.com/](https://doc.xiaominfo.com/)
- hutool：[https://hutool.cn/](https://hutool.cn/)

