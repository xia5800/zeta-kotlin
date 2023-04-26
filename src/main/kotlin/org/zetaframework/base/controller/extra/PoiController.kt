package org.zetaframework.base.controller.extra

import org.zetaframework.base.entity.ImportPoi
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Excel导入导出 Controller
 *
 *
 * @param ImportBean  Excel导入实体  必须继承[ImportPoi]类
 * @param ExportBean  Excel导出实体
 * @param Entity      实体
 * @param QueryParam  查询参数
 *
 * @author gcc
 */
interface PoiController<ImportBean: ImportPoi, ExportBean, Entity, QueryParam>:
    ImportController<ImportBean, Entity>,
    ExportController<ExportBean, Entity, QueryParam>
{

    /**
     * 获取导入实体的类型
     */
    override fun getImportExcelClass(): Class<ImportBean>? {
        /**
         * 代码解释：
         * // 获取子类的所有接口。
         * `this.javaClass.genericInterfaces`
         * 例如：
         * class ChildController: SaveController<xxx,xxx>, UpdateController<xxxx,xxxx>, PoiController<xxx,xxx> {}
         * ChildController类就实现了3个接口。
         *
         * // 筛选出符合条件的第一个对象
         * first {}
         * 在['interface SaveController', 'interface UpdateController', 'interface PoiController']中筛选出符合条件的第一个对象
         *
         * // 筛选条件
         * (it as ParameterizedType).rawType.typeName == PoiController::class.java.typeName
         * 本质就是判断 `'interface PoiController' == 'interface PoiController'`
         */
        val type: Type? = this.javaClass.genericInterfaces.first {
            (it as ParameterizedType).rawType.typeName == PoiController::class.java.typeName
        }

        // 筛选不到返回null，否则获取PoiController接口的第一个泛型值，反射成具体对象
        // ?.let 如果不为空则进行xxx操作。
        return type?.let { (it as ParameterizedType).actualTypeArguments[0] as Class<ImportBean> }
    }

    /**
     * 获取导出实体的类型
     */
    override fun getExportExcelClass(): Class<ExportBean>? {
        val type: Type? = this.javaClass.genericInterfaces.first {
            (it as ParameterizedType).rawType.typeName == PoiController::class.java.typeName
        }
        return type?.let { (it as ParameterizedType).actualTypeArguments[1] as Class<ExportBean> }
    }

}
