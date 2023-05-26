package com.zeta.system.model.enums

import com.baomidou.mybatisplus.annotation.IEnum

/**
 * 菜单类型
 *
 * @author gcc
 */
enum class MenuTypeEnum: IEnum<String> {
    /** 菜单 */
    MENU,
    /** 资源 */
    RESOURCE;

    /**
     * 枚举数据库存储值
     */
    override fun getValue(): String {
        return this.name
    }
}
