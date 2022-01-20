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
 */
class ContextUtil private constructor() {

    companion object {
        /**
         * 支持多线程传递参数
         */
        private val THREAD_LOCAL: ThreadLocal<MutableMap<String, String>> = TransmittableThreadLocal()

        fun putAll(map: Map<String, String?>) = map.forEach(ContextUtil::set)

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
        fun getUserIdStr(): String = get("userId")
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

}
