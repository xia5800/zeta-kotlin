package org.zetaframework.core.utils

import cn.hutool.core.util.StrUtil
import org.lionsoul.ip2region.xdb.Searcher
import org.slf4j.LoggerFactory
import java.util.regex.Pattern


/**
 * ip地址查询工具类
 *
 * 说明：
 * 基于ip2region
 *
 * @author gcc
 */
object IpAddressUtil {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val SPLIT_PATTERN: Pattern = Pattern.compile("\\|")
    private const val ZERO: String = "0"
    private var searcher: Searcher? = null
    private var vectorIndex: ByteArray? = null

    init {
        val dbPath = IpAddressUtil::class.java.getResource("/ip2region.xdb")?.path
        try {
            // 1.从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用
            vectorIndex = Searcher.loadVectorIndexFromFile(dbPath)

            // 2.使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
            searcher = Searcher.newWithVectorIndex(dbPath, vectorIndex)
        }catch (e: Exception) {
            logger.error("ip2region初始化失败", e)
        }
    }

    /**
     * 查询ip地址所在地区
     * @param ip
     * @return String ip地址所在地区 eg: 美国|0|华盛顿|0|谷歌
     */
    fun search(ip: String): String {
        if (searcher == null) return StrUtil.EMPTY
        if (ip == "0:0:0:0:0:0:0:1") return "0|0|0|内网IP|内网IP"

        return try {
            searcher!!.search(ip)
        } catch (e: Exception) {
            logger.error("根据ip地址查询地区信息失败", e)
            StrUtil.EMPTY
        }
    }

    /**
     * 查询ip地址所在地区
     * @param ip
     * @return IpInfo
     */
    fun searchInfo(ip: String): IpInfo {
        val address = search(ip)
        if (address.isBlank()) {
            return IpInfo().apply { this.source = address }
        }

        var info = SPLIT_PATTERN.split(address)
        if (info.size < 5) {
            info = info.copyOf(5)
        }
        return IpInfo().apply {
            this.country = filterZero(info[0])
            this.region = filterZero(info[1])
            this.province = filterZero(info[2])
            this.city = filterZero(info[3])
            this.isp = filterZero(info[4])
            this.source = address
        }
    }

    /**
     * 数据过滤，ip2Region会用0填充没有数据的字段
     * @param info
     * @return String
     */
    private fun filterZero(info: String?): String? {
        return if (info.isNullOrBlank() || ZERO == info) null else info
    }


    /**
     * ip地址信息
     */
    data class IpInfo (
        /** 国家 */
        var country: String? = null,
        /** 地区 */
        var region: String? = null,
        /** 省 */
        var province: String? = null,
        /** 市 */
        var city: String? = null,
        /** 网络供应商 */
        var isp: String? = null,
        /** 源数据 */
        var source: String? = null,
    )

}
