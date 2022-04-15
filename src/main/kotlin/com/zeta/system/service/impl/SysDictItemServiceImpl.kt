package com.zeta.system.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zeta.system.dao.SysDictItemMapper
import com.zeta.system.model.dto.sysDictItem.SysDictItemDTO
import com.zeta.system.model.entity.SysDictItem
import com.zeta.system.service.ISysDictItemService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * <p>
 * 字典项 服务实现类
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:10
 */
@Service
class SysDictItemServiceImpl: ISysDictItemService, ServiceImpl<SysDictItemMapper, SysDictItem>() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 根据字典编码查询字典项
     *
     * @param codes
     */
    override fun listByCodes(codes: List<String>): Map<String, List<SysDictItemDTO>> {
        if(codes.isEmpty()) { return emptyMap() }

        // 根据字典编码查询字典项
        val dictItemList = baseMapper.selectByDictCodes(codes)

        // 按照字典编码分组
        return dictItemList.groupBy { it.dictCode!! }
    }


    /**
     * 根据字典id查询字典项
     *
     * @param dictIds 字典id
     */
    override fun listByDictIds(dictIds: List<Long>): Map<Long, List<SysDictItem>> {
        if(dictIds.isEmpty()) { return emptyMap() }

        // 通过字典id查询字典项
        val dictItemList = this.list(
            KtQueryWrapper(SysDictItem())
                .`in`(SysDictItem::dictId, dictIds)
        ) ?: return emptyMap()

        // 按照字典id分组
        return dictItemList.groupBy { it.dictId!! }
    }
}
