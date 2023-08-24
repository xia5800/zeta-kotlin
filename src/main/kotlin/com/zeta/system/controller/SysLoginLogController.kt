package com.zeta.system.controller

import com.zeta.system.model.entity.SysLoginLog
import com.zeta.system.model.param.SysLoginLogQueryParam
import com.zeta.system.service.ISysLoginLogService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zetaframework.base.controller.SuperSimpleController
import org.zetaframework.base.controller.curd.QueryController
import org.zetaframework.core.saToken.annotation.PreAuth

/**
 * 登录日志 前端控制器
 *
 * @author AutoGenerator
 * @date 2022-03-21 16:33:13
 */
@Api(tags = ["登录日志"])
@PreAuth(replace = "sys:loginLog")
@RestController
@RequestMapping("/api/system/loginLog")
class SysLoginLogController : SuperSimpleController<ISysLoginLogService, SysLoginLog>(),
    QueryController<SysLoginLog, Long, SysLoginLogQueryParam>
{

}
