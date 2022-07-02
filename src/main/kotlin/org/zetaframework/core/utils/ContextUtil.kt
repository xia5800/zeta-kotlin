package org.zetaframework.core.utils

import cn.hutool.core.convert.Convert
import com.alibaba.ttl.TransmittableThreadLocal
import java.util.concurrent.ConcurrentHashMap

/**
 * 获取当前线程变量中的 用户id、令牌等信息
 *
 * 说明：参考lamp-util项目ContextUtil类实现
 * @author zuihou
 * @author gcc
 * @since 1.1 修改为object单例类，使用方式和之前无任何区别
 */
object ContextUtil {

    /**
     * 支持多线程传递参数
     */
    private val THREAD_LOCAL: ThreadLocal<MutableMap<String, String>> = TransmittableThreadLocal()

    fun putAll(map: Map<String, String?>) = map.forEach(ContextUtil::set)

    /**
     * set 运算符重载
     *
     * 说明：
     * 可以使用ContextUtil["token"] = xxx 来设置取值。效果等价于 ContextUtil.setToken("xxx")
     * @param key String
     * @param value Any?
     */
    operator fun set(key: String, value: Any?) {
        val map = getLocalMap()
        map[key] = value?.toString() ?: ""
    }

    operator fun <T> get(key: String, type: Class<T>?): T {
        val map: Map<String, String> = getLocalMap()
        return Convert.convert(type, map[key])
    }

    operator fun <T> get(key: String, type: Class<T>?, def: Any?): T {
        val map: Map<String, String> = getLocalMap()
        return Convert.convert(type, map[key] ?: (def?.toString() ?: ""))
    }

    /**
     * get 运算符重载
     *
     * 说明：
     * 可以使用ContextUtil["token"] 来获取值。效果等价于 ContextUtil.getToken()
     * @param key String
     * @return String
     */
    operator fun get(key: String): String {
        val map: Map<String, String> = getLocalMap()
        return map[key] ?: ""
    }

    /**
     * 获取ThreadLocal中的值
     *
     * @return MutableMap<String, String>
     */
    fun getLocalMap(): MutableMap<String, String> {
        var map = THREAD_LOCAL.get()
        if (map == null) {
            map = ConcurrentHashMap(10)
            THREAD_LOCAL.set(map)
        }
        return map
    }

    /**
     * 设置ThreadLocal中的值
     *
     * @return MutableMap<String, String>
     */
    fun setLocalMap(localMap: MutableMap<String, String>) = THREAD_LOCAL.set(localMap)

    /**
     * 防止内存溢出
     */
    fun remove() = THREAD_LOCAL.remove()


    /*********************** 用户id ***********************/

    /**
     * 设置用户ID
     */
    fun setSubjectId(userId: Int) = set("subjectId", userId)

    /**
     * 设置用户ID
     *
     * @param userId
     */
    fun setUserId(userId: Long) = set("userId", userId)

    /**
     * 设置用户ID
     *
     * @param userId
     */
    fun setUserId(userId: String) = set("userId", userId)

    /**
     * 获取用户ID
     *
     * @return Long
     */
    fun getUserId(): Long = get("userId", Long::class.java, 0)

    /**
     * 获取用户ID
     *
     * @return String
     */
    fun getUserIdStr(): String = get("userId", String::class.java, "")

    /**
     * 获取用户ID
     *
     * @return Int
     */
    fun getSubjectId(): Int = get("setSubjectId", Int::class.java, 0)
    /*********************** 用户id end ***********************/


    /*********************** 令牌 ***********************/
    /**
     * 设置token
     *
     * @param token String
     */
    fun setToken(token: String) = set("token", token)

    /**
     * 获取token
     *
     * @return String
     */
    fun getToken() = get("token")
    /*********************** 令牌 end ***********************/

}
