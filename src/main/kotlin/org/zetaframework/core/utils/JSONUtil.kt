package org.zetaframework.core.utils

import cn.hutool.core.util.StrUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.slf4j.LoggerFactory

/**
 * 封装Jackson得到的JSON工具类
 *
 * @author gcc
 */
class JSONUtil private constructor(){
    companion object{
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val objectMapper: ObjectMapper = ObjectMapper()
        private val objectWrite: ObjectWriter = objectMapper.writerWithDefaultPrettyPrinter()


        /**
         * 对象转json字符串
         *
         * @param value Any         对象
         * @param pretty Boolean?   是否格式化输出
         * @return String           json字符串
         */
        fun toJsonStr(value: Any?, pretty: Boolean? = false): String? {
            if(value == null) {
                return null
            }
            // 如果对象是字符串，直接返回
            if(value is String) {
                return value
            }

            try {
                // 判断是否需要格式化输出
                return if (pretty == true) {
                    objectWrite.writeValueAsString(value)
                } else {
                    objectMapper.writeValueAsString(value)
                }
            }catch (e: Exception) {
                logger.error("对象转json字符串失败", e)
            }
            return null
        }


        /**
         * json字符串转对象
         *
         * @param json String?    json字符串
         * @param clazz Class<T>  对象class
         * @return T?
         */
        fun <T> parseObject(json: String?, clazz: Class<T>): T? {
            if(StrUtil.isBlank(json)) {
                return null
            }

            try {
                return objectMapper.readValue(json, clazz)
            }catch (e: Exception) {
                logger.error("json字符串转对象失败", e)
            }
            return null
        }
    }
}
