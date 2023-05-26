package com.zeta.system.controller

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import com.zeta.system.model.dto.sysMenu.SysMenuSaveDTO
import com.zeta.system.model.dto.sysMenu.SysMenuUpdateDTO
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.param.SysMenuQueryParam
import com.zeta.system.service.ISysMenuService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperController
import org.zetaframework.base.result.ApiResult
import org.zetaframework.core.saToken.annotation.PreAuth
import org.zetaframework.core.utils.TreeUtil

/**
 * 菜单 前端控制器
 *
 * @author AutoGenerator
 * @date 2021-12-30 15:24:03
 */
@Tag(name = "菜单管理", description = "菜单管理")
@PreAuth(replace = "sys:menu")
@RestController
@RequestMapping("/api/system/menu")
class SysMenuController: SuperController<ISysMenuService, Long, SysMenu, SysMenuQueryParam, SysMenuSaveDTO, SysMenuUpdateDTO>() {

    /**
     * 自定义批量查询
     *
     * @param param QueryParam
     * @return MutableList<Entity>
     */
    override fun handlerBatchQuery(param: SysMenuQueryParam): MutableList<SysMenu> {
        val entity = BeanUtil.toBean(param, getEntityClass())
        // 批量查询
        val list = service.list(
            KtQueryWrapper<SysMenu>(entity)
                .orderByAsc(SysMenu::sortValue, SysMenu::id)
        )

        // 处理批量查询数据
        super.handlerBatchData(list)
        return list
    }

    /**
     * 查询菜单树
     *
     * @param param 查询参数
     * @return ApiResult<List<[SysMenu]?>>
     */
    @ApiOperationSupport(ignoreParameters = ["children"])
    @Operation(summary = "查询菜单树")
    @PostMapping("/tree")
    fun tree(@RequestBody param: SysMenuQueryParam): ApiResult<List<SysMenu?>> {
        // 查询所有菜单
        val menuList = handlerBatchQuery(param)
        if(menuList.isEmpty()) return fail("未查询到菜单")

        // 转换成树形结构
        return success(TreeUtil.buildTree(menuList, false))
    }

}
