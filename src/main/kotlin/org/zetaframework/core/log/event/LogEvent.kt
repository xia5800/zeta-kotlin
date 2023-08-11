package org.zetaframework.core.log.event

import org.springframework.context.ApplicationEvent
import org.zetaframework.core.log.model.LogDTO

/**
 * 系统日志 事件
 *
 * 说明：
 * 在[LogListener]中处理本事件
 * @author gcc
 */
class LogEvent(source: LogDTO): ApplicationEvent(source) {

}
