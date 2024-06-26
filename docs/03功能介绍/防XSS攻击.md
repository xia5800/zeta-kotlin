# 防XSS攻击

> XSS攻击通常指的是通过利用网页开发时留下的漏洞，通过巧妙的方法注入恶意指令代码到网页，使用户加载并执行攻击者恶意制造的网页程序。
>
> 这些恶意网页程序通常是JavaScript，但实际上也可以包括Java、 VBScript、ActiveX、 Flash 或者甚至是普通的HTML。
>
> 攻击成功后，攻击者可能得到包括但不限于更高的权限（如执行一些操作）、私密网页内容、会话和cookie等各种内容。 
>
> -- 摘自：百度百科

假如未对XSS攻击做防范，黑客向`/api/system/dict`接口POST了如下数据

```json
{
  "code": "<script>alert(\"黑掉你了哦\")</script>",
  "describe": "<a href=\"javascript:alert('正在窃取数据')\">奇怪描述</a>",
  "name": "<h1>黑掉你了哦</h1>",
  "sortValue": 0
}
```

很显然，后端会老老实实将数据存入到数据库中。然后管理员查询字典列表的时候...boom!!!页面弹出了奇怪的弹窗，

不仅如此，管理员浏览器上的cookie、缓存等数据一并被黑客打包带走了。黑客一旦知晓了管理员的令牌（token）等信息... 想想就可怕


## 如何防范
针对这种情况，网上有很多方法来防范XSS攻击，本项目采用了比较大众化的方式（自定义拦截器+自定义Json反序列化器）

**注意：** XSS配置默认关闭，需要在配置文件`application.yml`中手动启动
```yaml
zeta:
  xss:
    # XSS防护开关 默认：false
    enabled: true
    # 忽略xss防护的地址
    excludeUrl:
      - /**/noxss/**
      # 忽略指定接口的xss防护
      - /api/test/test1
      # 忽略新增、修改接口的xss防护
      - POST:/api/demo  
      - PUT:/api/demo
```

## 使用@NoXss注解来排除接口XSS防护

为了解决本项目使用xss防护的痛点，即：

- 没有忽略xss防护的注解，每次都要手动添加需要忽略xss防护的接口地址
- 项目接口用的是REST API风格。如果想要忽略新增接口的XSS防护，会把修改接口的XSS防护也忽略掉

本人新增了一个自定义注解`@NoXss`来解决这些问题。使用方式很简单,只要在接口上加上`@NoXss`注解即可

```kotlin
@RestController
@RequestMapping("/api/demo")
class DemoController: SuperSimpleController<IDemoService, Demo>(),
    SaveController<Demo, DemoSaveDTO>,
    UpdateController<Demo, DemoUpdateDTO>
{
    /**
     * 新增接口不需要XSS防护
     */
    @NoXss
    override fun save(saveDTO: DemoSaveDTO): ApiResult<Boolean> {
        logger.info("请求数据：${JSONUtil.toJsonStr(saveDTO)}")
        return success(true)
    }

    /**
     * 修改接口需要XSS防护
     *
     * @param updateDTO UpdateDTO 修改对象
     * @return ApiResult<Boolean>
     */
    override fun update(updateDTO: DemoUpdateDTO): ApiResult<Boolean> {
        logger.info("请求数据：${JSONUtil.toJsonStr(updateDTO)}")
        return success(true)
    }
    
    @ApiOperation(value = "有xss防护")
    @GetMapping("/test1")
    fun test1(@RequestParam @ApiParam("姓名") name: String): ApiResult<String> {
        return success("hello $name")
    }

    @ApiOperation(value = "无xss防护写法1")
    @GetMapping("/noxss/test2")
    fun test2(@RequestParam @ApiParam("姓名") name: String): ApiResult<String> {
        return success("hello $name")
    }

    @NoXss
    @ApiOperation(value = "无xss防护写法2")
    @GetMapping("/test3")
    fun test3(@RequestParam @ApiParam("姓名") name: String): ApiResult<String> {
        return success("hello $name")
    }
    
}
```

## 效果

开启XSS防护之后，如果黑客依旧向`/api/system/dict`接口POST如下数据

```json
{
  "code": "<script>alert(\"黑掉你了哦\")</script>",
  "describe": "<a href=\"javascript:alert('正在窃取数据')\">奇怪描述</a>",
  "name": "<h1>黑掉你了哦</h1>",
  "sortValue": 0
}
```

此时会在`XssStringJsonDeserializer`的反序列化方法中对数据进行去XSS处理，最终存入数据库中的结果如下：

```json
{
  "code": "alert(&quot;黑掉你了哦&quot;)",
  "describe": "<a href=\"#alert('正在窃取数据')\">奇怪描述</a>",
  "name": "黑掉你了哦",
  "sortValue": 0
}
```

上面的例子只说了前端提交`application/json`类型的数据这种情况。

假如黑客往`/api/system/dict/saveTest?name=<script>alert("黑掉你了哦")</script>`接口POST数据呢？

```kotlin
// 伪代码，其它略
@RestController
@RequestMapping("/api/system/dict")
class DictController {

    @ApiOperation(value = "测试")
    @PostMapping("/saveTest")
    fun saveTest(@RequestParam("name") name: String): ApiResult<String> {
        return success("你post的数据是$name")
    }
    
}
```

此时会被`XssFilter`拦截器拦截到`XssRequestWrapper`类中对数据进行去XSS处理，最终接口返回结果如下：

```json
{
  "code": 200,
  "message": "你post的数据是alert(&quot;黑掉你了哦&quot;)",
  "data": null,
  "error": null
}
```


以上流程涉及到的类有：
```
/** zetaframework包 */
// XSS跨站脚本攻击防护配置
org.zetaframework.core.xss.XssConfiguration
// 自定义用于XSS防护的 过滤器
org.zetaframework.core.xss.filter.XssFilter
// 自定义用于XSS防护的 请求包装器
org.zetaframework.core.xss.wrapper.XssRequestWrapper
// 自定义用于XSS防护的 Json反序列化器
org.zetaframework.core.xss.serializer.XssStringJsonDeserializer
// XSS文本清理接口实现
org.zetaframework.core.xss.cleaner.HutoolXssCleaner
```
