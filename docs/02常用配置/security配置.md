# security配置

以下是security相关的配置
```yaml
zeta:
  # security配置
  security:
    ignore:
      # 忽略鉴权的地址
      ignoreUrl:
        - /**/noToken/**
```



## zeta.security.ignore.ignoreUrl

配置说明： 忽略鉴权的地址

> 什么意思呢？

假如我新增了一个接口`/api/demo/test`, 这个接口不需要用户登录就可以访问。

那么我这个时候有三种方法可以实现我的目的。

**方法一：**

将接口名改为`/api/demo/noToken/test`

**方法二：**

在配置文件`ignoreUrl`中配置一下

```yaml
zeta:
  # security配置
  security:
    ignore:
      # 忽略鉴权的地址
      ignoreUrl:
        - /**/noToken/**
        - /api/demo/test 
```

**方法三(不推荐)：**

直接在`org.zetaframework.core.saToken.properties.IgnoreProperties`类中加

```kotlin
/**
 * security忽略鉴权配置类
 * @author gcc
 */
@ConfigurationProperties(prefix = "zeta.security.ignore")
class IgnoreProperties {

    /** 基础忽略鉴权地址 */
    var baseUrl: MutableList<String> = mutableListOf(
    	// ...省略其它忽略地址
        "/api/demo/test"
    )
    
    /** 忽略鉴权的地址 */
    var ignoreUrl: MutableList<String> = mutableListOf()
}
```

