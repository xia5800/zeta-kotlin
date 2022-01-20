package com.zeta.system.model.enumeration

import io.swagger.annotations.ApiModel

/**
 * 菜单类型
 * @author gcc
 */
@ApiModel(description = "菜单类型 枚举")
enum class MenuTypeEnum {
    /** 菜单 */
    MENU,
    /** 资源 */
    RESOURCE;
}
