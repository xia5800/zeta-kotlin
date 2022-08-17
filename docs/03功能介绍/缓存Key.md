# 缓存key
本项目之前的redis使用方式很原始，每次都需要构造缓存key再进行相关的查询、修改操作。

例如：
```kotlin
// 获取验证码缓存值
val captchaKey = "${SystemRedisKeyConstants.CAPTCHA_KEY}:${param.key}" // 构造缓存key
val verifyCode = redisUtil.get<String>(captchaKey) // 通过缓存key获取缓存值
```

这是不太优雅的，于是参考了lamp-boot项目的实现之后，自己封装了一个优雅的缓存使用方式。


## 编写一个自定义的缓存key

编写一个自定义缓存很简单，只要实现相应的接口`StringCacheKey`、`ListCacheKye`，重写相应的方法即可

例如：业务需要一个String类型的redis缓存，这个缓存的过期时间为5分钟，这个缓存的key前缀为:`system:captcha`

代码实现如下：

CaptchaStringCacheKey.kt

```kotlin
import com.zeta.common.constants.SystemRedisKeyConstants
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.zetaframework.core.redis.model.StringCacheKey
import java.time.Duration

@Component
class CaptchaStringCacheKey(private val redisTemplate: RedisTemplate<String, Any>): StringCacheKey {

    /**
     * key 前缀
     */
    override fun getPrefix(): String {
        return "system:captcha"
    }

    /**
     * key 过期时间
     */
    override fun getExpire(): Duration? {
        return Duration.ofMinutes(5)
    }

    /**
     * 获取redisTemplate
     */
    override fun getRedisTemplate(): RedisTemplate<String, Any> {
        return redisTemplate
    }

}
```

## 如何使用？

下面只例举了简单使用方法，更加详细的使用方式请查看`CaptchaStringCacheKey`、`StringCacheKey`的注释

```kotlin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import com.zeta.common.cacheKey.CaptchaStringCacheKey

@RestController
@RequestMapping("/api/demo")
class DemoController(private val captchaCacheKey: CaptchaStringCacheKey) {
    
    @GetMapping
    fun example() {
        // 设置缓存
        captchaCacheKey.set("123", "验证码的值")
        
        // 获取缓存值
        val cacheValue: String? = captchaCacheKey.get<String>("123")
        println(cacheValue)
        
        // 删除缓存
        captchaCacheKey.delete("123")
    }
    
}
```

以上demo涉及到的类有：
```
/** 业务包 */
// 验证码缓存key
com.zeta.common.cacheKey.CaptchaStringCacheKey

/** zetaframework包 */
// 缓存key
org.zetaframework.core.redis.model.CacheKey.kt
// Hash(Map)类型的缓存key
org.zetaframework.core.redis.model.HashCacheKey
// List类型的缓存key
org.zetaframework.core.redis.model.ListCacheKey
// Set类型的缓存key
org.zetaframework.core.redis.model.SetCacheKey
// String类型的缓存key
org.zetaframework.core.redis.model.StringCacheKey
// ZSet类型的缓存key
org.zetaframework.core.redis.model.ZSetCacheKey
```
