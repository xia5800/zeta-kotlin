package org.zetaframework.core.log.event

import org.springframework.context.ApplicationEvent
import org.zetaframework.core.log.model.SysLogDTO

/**
 * 系统日志 事件
 *
 * @author gcc
 */
class SysLogEvent(source: SysLogDTO): ApplicationEvent(source) {

}
