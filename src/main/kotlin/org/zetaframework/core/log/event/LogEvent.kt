package org.zetaframework.core.log.event

import org.springframework.context.ApplicationEvent
import org.zetaframework.core.log.model.LogDTO

/**
 * 系统日志 事件
 *
 * @author gcc
 */
class LogEvent(source: LogDTO): ApplicationEvent(source) {

}
