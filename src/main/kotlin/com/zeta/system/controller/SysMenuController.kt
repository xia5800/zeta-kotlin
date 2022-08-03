package com.zeta.system.controller

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import com.zeta.system.model.dto.sysMenu.SysMenuSaveDTO
import com.zeta.system.model.dto.sysMenu.SysMenuUpdateDTO
import com.zeta.system.model.entity.SysMenu
import com.zeta.system.model.param.SysMenuQueryParam
import com.zeta.system.service.ISysMenuService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@Api(tags = ["菜单管理"])
@PreAuth(replace = "sys:menu")
@RestController
@RequestMapping("/api/system/menu")
class SysMenuController: SuperController<ISysMenuService, Long, SysMenu, SysMenuQueryParam, SysMenuSaveDTO, SysMenuUpdateDTO>() {

    /**
     * 查询菜单树
     *
     * @param param SysMenuQueryParam
     * @return List<Menu>
     */
    @ApiOperationSupport(ignoreParameters = ["children"])
    @ApiOperation(value = "查询菜单树")
    @PostMapping("/tree")
    fun tree(@RequestBody param: SysMenuQueryParam): ApiResult<List<SysMenu?>> {
        // 查询所有菜单
        val menuList = handlerBatchQuery(param)
        if(menuList.isEmpty()) return fail("未查询到菜单")

        // 转换成树形结构
        return success(TreeUtil.buildTree(menuList))
    }

}
