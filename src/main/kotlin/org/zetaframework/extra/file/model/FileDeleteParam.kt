package org.zetaframework.extra.file.model

/**
 * 文件删除参数
 *
 * @author gcc
 */
data class FileDeleteParam(

    /** 文件相对路径 */
    var path: String? = null,

    /** 将来其它的OSS要用到新的字段可以加在这里.. */
)
