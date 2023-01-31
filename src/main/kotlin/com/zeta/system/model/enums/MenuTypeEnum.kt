package com.zeta.system.model.enums

import com.baomidou.mybatisplus.annotation.IEnum
import io.swagger.annotations.ApiModel

/**
 * 菜单类型
 * @author gcc
 */
@ApiModel(description = "菜单类型 枚举")
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
