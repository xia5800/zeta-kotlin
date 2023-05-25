package com.zeta.monitor.controller

import com.aizuda.monitor.OshiMonitor
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import com.zeta.monitor.model.ServerInfoDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperBaseController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.log.annotation.SysLog
import java.util.*


/**
 * 系统监控
 *
 * @author gcc
 */
@Tag(name = "系统监控", description = "系统监控")
@RestController
@RequestMapping("/api/monitor")
class MonitorController(
    private val oshiMonitor: OshiMonitor,
    private val redisTemplate: StringRedisTemplate
): SuperBaseController {

    /**
     * 获取服务器信息
     *
     * @return ApiResult<Boolean>
     */
    @ApiOperationSupport(order = 10)
    @Operation(summary = "服务器信息")
    @SysLog
    @GetMapping("/server")
    fun getServerInfo(): ApiResult<ServerInfoDTO> {
        return success(ServerInfoDTO().apply {
            // 系统信息
            this.sysInfo = ServerInfoDTO.SysInfo.build(oshiMonitor.sysInfo)
            // CPU使用率信息
            this.cupInfo = ServerInfoDTO.CpuInfo.build(oshiMonitor.cpuInfo)
            // 内存信息
            this.memoryInfo = ServerInfoDTO.MemoryInfo.build(oshiMonitor.memoryInfo)
            // Jvm 虚拟机信息
            this.jvmInfo = ServerInfoDTO.JvmInfo.build(oshiMonitor.jvmInfo)
            // CPU信息
            this.centralProcessor = ServerInfoDTO.CentralProcessor.build(
                oshiMonitor.centralProcessor.processorIdentifier
            )
            // 磁盘信息
            this.diskInfos = ServerInfoDTO.DiskInfo.build(oshiMonitor.diskInfos)
        })
    }

    /**
     * 获取Redis信息
     *
     * @return ApiResult<Boolean>
     */
    @ApiOperationSupport(order = 20)
    @Operation(summary = "Redis信息")
    @SysLog
    @GetMapping("/redis")
    fun getRedisInfo(): ApiResult<Properties?> {
        val properties: Properties? = redisTemplate.connectionFactory?.connection?.info()
        return success(data = properties)
    }

}
