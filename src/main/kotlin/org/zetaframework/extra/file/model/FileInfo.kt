package org.zetaframework.extra.file.model

/**
 * 上传文件的信息
 *
 * @author gcc
 */
data class FileInfo(

    /** 原始文件名 */
    var originalFileName: String? = null,

    /** 唯一文件名 */
    var uniqueFileName: String? = null,

    /** 内容类型 */
    var contentType: String? = null,

    /** 文件大小 */
    var size: Long? = null,

    /** 后缀 */
    var suffix: String? = null,

    /** 文件类型 */
    var fileType: String? = null,

    /** 文件访问地址 */
    var url: String? = null,

    /** 文件相对地址 */
    var path: String? = null,

    /** 存储类型 */
    var storageType: String? = null,

    /**
     * 桶
     * 说明：
     * 存储类型为本地的时候，对应的是文件夹的名称
     * 存储类型为oss的时候，对应的是oss的bucket名称
     */
    var bucket: String? = null,

    /**
     * 业务类型
     *
     * 说明：
     * 1.影响文件相对路径和文件的访问地址
     * 2.值尽量用英文吧
     *
     * 举例：
     * 你上传的是用户头像，那么bizType就可以是user_avatar、avatar等
     * 上传的是订单模块的商品图片，那么bizType就可以是order、order_goods
     */
    var bizType: String? = null

)
