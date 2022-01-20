package org.zetaframework.core.enums

/**
 * 异常枚举
 *
 * @author gcc
 */
enum class ErrorCodeEnum(val code: Int, val msg: String) {

    /***************************通用错误******************************/
    /** 通用操作成功 */
    OK(0, "操作成功"),
    /** 通用操作失败 */
    ERROR(1, "操作失败"),
    /** 通用操作成功 */
    SUCCESS(200, "操作成功"),
    /** 系统繁忙 */
    SYSTEM_BUSY(1001, "系统繁忙"),


    /***************************http错误******************************/
    /** 错误的请求 */
    BAD_REQUEST(400, "错误的请求"),
    /** 未授权 */
    UNAUTHORIZED(401, "未授权"),
    /** 禁止访问 */
    FORBIDDEN(403, "没有访问权限"),
    /** 没有获取到数据 */
    NOT_FOUND(404, "没有获取到数据"),
    /** 不支持当前请求类型 */
    METHOD_NOT_ALLOWED(405, "不支持当前请求类型"),
    /** 请求过于频繁 */
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    /** 服务器内部错误 */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    /** 网关错误 */
    BAD_GATEWAY(502, "网关错误"),


    /** 业务异常 */
    ERR_BUSINESS(10000, "业务异常"),
    /** 参数错误 */
    ERR_ARGUMENT(20000, "参数错误"),


    /***************************自定义错误******************************/
    /** 参数绑定失败 */
    ERR_BIND_EXCEPTION(1010, "参数绑定失败(检查参数名称)"),
    /** 方法参数类型不匹配 */
    ERR_ARGUMENT_TYPE_MISMATCH_EXCEPTION(1011, "方法参数类型不匹配"),
    /** 请求参数异常 */
    ERR_REQUEST_PARAM_EXCEPTION(1012, "请求参数异常"),
    ;
}
