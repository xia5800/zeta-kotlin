package com.zeta.system.controller

import com.zeta.system.model.dto.sysDict.SysDictSaveDTO
import com.zeta.system.model.dto.sysDict.SysDictUpdateDTO
import com.zeta.system.model.entity.SysDict
import com.zeta.system.model.param.SysDictQueryParam
import com.zeta.system.service.ISysDictService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperController
import org.zetaframework.core.saToken.annotation.PreAuth

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author AutoGenerator
 * @date 2022-04-15 10:12:09
 */
@Api(tags = ["字典"])
@PreAuth(replace = "sys:dict")
@RestController
@RequestMapping("/api/system/dict")
class SysDictController: SuperController<ISysDictService, Long, SysDict, SysDictQueryParam, SysDictSaveDTO, SysDictUpdateDTO>() {

}
