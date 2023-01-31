package org.zetaframework.extra.desensitization.utils

import cn.afterturn.easypoi.util.PoiDataDesensitizationUtil
import org.apache.commons.lang3.StringUtils
import kotlin.math.ceil

/**
 * 脱敏工具类
 *
 * 说明：
 * 参考easypoi的[PoiDataDesensitizationUtil]类实现
 *
 * @author gcc
 */
object DesensitizationUtil {
    /** 规则一 */
    private const val SPILT_START_END = "_"
    /** 规则二 */
    private const val SPILT_MAX = ","
    /** 规则三 */
    private const val SPILT_MARK = "~"


    /**
     * # 脱敏
     *
     * ## 脱敏规则说明：
     * **规则一**：采用保留头和尾的方式,中间数据按打码方式配置的字符进行打码
     *
     * 如：3_4 表示保留前3位以及后4位
     * ```
     * 3_4    13112311234 --> 131****1234
     * 6_4    370101123456781234 --> 370101********1234
     * ```
     *
     * **规则二**：采用确定隐藏字段的进行隐藏,优先保留头
     *
     * 如：1,3 表示最大隐藏3位,最小一位
     * ```
     *    李 -->  *
     *    李三 --> 李*
     *    张全蛋  --> 张*蛋
     *    李张全蛋 --> 李**蛋
     *    尼古拉斯.李张全蛋 -> 尼古拉***张全蛋
     *```
     *
     * **规则三**：特殊符号后保留
     *
     * 如：1~@ 表示只保留第一位和@之后的字段
     * ```
     * 1~@   alibaba@mail.com -> a********@mail.com
     * 3~#   236121678126381#2236 -> 236***********#2236
     *```
     *
     * @param value 要脱敏的数据
     * @param rule 脱敏规则
     * @param symbol 打码方式
     * @return String 脱敏后的数据
     */
    fun deserialization(value: String, rule: String?, symbol: String? = ""): String {
        if (value.isBlank()) return value
        if (rule.isNullOrBlank()) return value

        return when {
            // 规则一
            rule.contains(SPILT_START_END) -> {
                val arr = rule.split(SPILT_START_END)
                subStartEndString(arr[0].toInt(), arr[1].toInt(), value, symbol!!)
            }
            // 规则二
            rule.contains(SPILT_MAX) -> {
                val arr = rule.split(SPILT_MAX)
                subMaxString(arr[0].toInt(), arr[1].toInt(), value, symbol!!)
            }
            // 规则三
            rule.contains(SPILT_MARK) -> {
                val arr = rule.split(SPILT_MARK)
                markSpilt(arr[0].toInt(), arr[1], value, symbol!!)
            }
            else -> value
        }
    }

    /**
     * 收尾截取数据
     *
     * @param start
     * @param end
     * @param value
     * @param symbol
     */
    private fun subStartEndString(start: Int, end: Int, value: String, symbol: String): String{
        if (value.length <= start + end) return value

        return StringUtils.left(value, start) + StringUtils.leftPad(
            StringUtils.right(value, end),
            StringUtils.length(value) - start,
            symbol
        )
    }

    /**
     * 部分数据截取，优先对称截取
     *
     * @param start
     * @param end
     * @param value
     * @param symbol
     */
    private fun subMaxString(start: Int, end: Int, value: String, symbol: String): String {
        require(start <= end) { "start must less end" }

        val len = value.length
        return if (len <= start) {
            // 李  ->  *
            StringUtils.leftPad("", len, symbol)
        } else if (len in (start + 1)..end) {
            // len > start && len <= end
            if (len == 1) {
                return value
            }
            if (len == 2) {
                return StringUtils.left(value, 1) + symbol
            }
            return StringUtils.left(value, 1) + StringUtils.leftPad(
                StringUtils.right(value, 1),
                StringUtils.length(value) - 1,
                symbol
            )
        } else {
            val newStart = ceil((len - end + 0.0) / 2).toInt()
            var newEnd = len - newStart - end
            newEnd = if (newEnd == 0) 1 else newEnd
            StringUtils.left(value, newStart) + StringUtils.leftPad(StringUtils.right(value, newEnd), len - newStart, symbol)
        }
    }

    /**
     * 特定字符分隔，添加星号
     *
     * @param start
     * @param mark 特定字符
     * @param value
     * @param symbol
     */
    private fun markSpilt(start: Int, mark: String, value: String, symbol: String): String {
        val end = value.lastIndexOf(mark)
        // 如果value中特定字符位置比start小
        if (end <= start) return value

        return StringUtils.left(value, start) + StringUtils.leftPad(
            StringUtils.right(value, value.length - end),
            value.length - start,
            symbol
        )
    }

}
