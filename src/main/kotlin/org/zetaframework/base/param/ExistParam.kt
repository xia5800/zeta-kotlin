package org.zetaframework.base.param

import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.extension.service.IService
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotEmpty

/**
 * 验证存在参数
 *
 * @author gcc
 */
@ApiModel(description = "验证存在")
class ExistParam<Entity, Id>(
    /** 检查的字段名 */
    @ApiModelProperty("检查的字段")
    @get:NotEmpty(message = "检查的字段不能为空")
    var field: String? = null,

    /** 检查的字段值 */
    @ApiModelProperty("检查字段的值")
    @get:NotEmpty(message = "检查的字段值不能为空")
    var value: String? = null,

    /** 主键字段的值  修改时用到 */
    @ApiModelProperty("主键字段的值，修改时用到")
    var id: Id? = null
) {

    /**
     * 验证是否存在
     *
     * 说明：
     * 这个方法本质上是构造查询条件并利用 service.count()方法来进行查询，判断是否有返回值。
     * 有返回值说明存在，没有返回值说明不存在。
     *
     * 使用方式：
     * <pre>
     *     // 语法：
     *     ExistParam<实体类,主键字段类型>(检查的字段名, 检查的字段值, 主键字段的值).isExist(service, 主键字段名)
     *     ExistParam<SysUser, Long>("account", "admin", "1").isExist(userService, "id")
     *
     *     // 新增用户的时候，判断账号是否已存在
     *     val param = ExistParam<SysUser, Long>("account", "admin")
     *     if(param.isExist(userService)) { return fail("账号已存在") }
     *
     *     // 修改用户的时候，判断账号是否已存在
     *     val param = ExistParam<SysUser, Long>("account", "admin", "1")
     *     if(param.isExist(userService, "id")) { return fail("账号已存在") }
     *     等价于
     *     if(param.isExist(userService, SysUser::id)) { return fail("账号已存在") }
     *     若主键字段名是id，还可以省略掉idField参数
     *     if(param.isExist(userService)) { return fail("账号已存在") }
     * </pre>
     *
     * @param service IService<Entity>   service
     * @param idField String?            主键字段, 例如：id, userId, user_id
     * @param isToUnderlineCase Boolean  检查的字段的字段是否驼峰转下划线
     * @return Boolean
     */
    fun isExist(service: IService<Entity>, idField: SFunction<Entity, *>): Boolean {
        return isExist(service, idField.javaClass.name, false)
    }

    fun isExist(service: IService<Entity>, idField: String? = "id", isToUnderlineCase: Boolean = true): Boolean {
        if(StrUtil.hasBlank(this.field, this.value)) {
            return false
        }

        // 处理字段名，是否驼峰转下划线
        val fieldName: String = if(isToUnderlineCase) { StrUtil.toUnderlineCase(field) } else { field!! }

        // 构造查询条件
        val queryWrapper: QueryWrapper<Entity> = QueryWrapper<Entity>()
        queryWrapper.eq(fieldName, value)
        if(id != null) {
            // 如果是修改
            queryWrapper.ne(idField, id)
        }

        // 验证字段是否存在
        return service.count(queryWrapper) > 0
    }

}
