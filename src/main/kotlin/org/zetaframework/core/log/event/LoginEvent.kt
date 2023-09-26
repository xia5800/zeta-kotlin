package org.zetaframework.core.log.event

import org.springframework.context.ApplicationEvent
import org.zetaframework.core.log.model.LoginLogDTO

/**
 * 登录日志 事件
 *
 * @author gcc
 */
class LoginEvent(source: LoginLogDTO): ApplicationEvent(source) {

}
