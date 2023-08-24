package com.zeta.system.service

import com.baomidou.mybatisplus.extension.service.IService
import com.zeta.system.model.dto.sysDictItem.SysDictItemDTO
import com.zeta.system.model.entity.SysDictItem

/**
 * 字典项 服务类
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:10
 */
interface ISysDictItemService: IService<SysDictItem> {

    /**
     * 根据字典编码查询字典项
     *
     * @param codes 字典编码列表
     */
    fun listByCodes(codes: List<String>): Map<String, List<SysDictItemDTO>>


    /**
     * 根据字典id查询字典项
     *
     * @param dictIds 字典id
     */
    fun listByDictIds(dictIds: List<Long>): Map<Long, List<SysDictItem>>

}
