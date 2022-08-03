package com.zeta

import cn.dev33.satoken.secure.BCrypt
import com.zeta.system.model.entity.*
import com.zeta.system.model.enumeration.MenuTypeEnum
import com.zeta.system.model.enumeration.SexEnum
import com.zeta.system.model.enumeration.UserStateEnum
import com.zeta.system.service.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.zetaframework.core.mybatisplus.generator.UidGenerator

/**
 *
 * @author gcc
 */
@SpringBootTest
class KtZetaApplicationTests {

    @Autowired
    private lateinit var uidGenerator: UidGenerator
    @Autowired
    private lateinit var menuService: ISysMenuService
    @Autowired
    private lateinit var roleService: ISysRoleService
    @Autowired
    private lateinit var userService: ISysUserService
    @Autowired
    private lateinit var userRoleService: ISysUserRoleService
    @Autowired
    private lateinit var roleMenuService: ISysRoleMenuService
    @Autowired
    private lateinit var sysDictService: ISysDictService
    @Autowired
    private lateinit var sysDictItemService: ISysDictItemService

    /**
     * 初始化数据库
     */
    // @Test // 注释掉，防止maven打包的时候没有跳过测试
    fun initDatabase() {
        // 初始化系统菜单、权限
        val menuIds = initMenu()
        // 初始化角色
        val superAdminId = initRole()
        // 初始化超级管理员菜单权限
        initRoleMenu(superAdminId, menuIds)
        // 初始化超级管理员用户
        val userId = initAdminUser()
        // 初始化用户角色
        initUserRole(userId, superAdminId)
        // 初始化数据字典
        initSysDict()
    }


    /**
     * 初始化系统菜单、权限
     *
     * 说明：
     * 1.确定了前端，使用[soybean-admin](https://github.com/honghuangdc/soybean-admin)框架
     * 2.菜单[图标地址](https://icones.js.org/)
     */
    fun initMenu(): List<Long> {
        val batchList: MutableList<SysMenu> = mutableListOf()
        var menuSort = 1

        // dashboard
        var dashboardSort = 1
        val dashboardId = uidGenerator.getUid()
        batchList.add(buildMenu(dashboardId, 0L, menuSort++, "dashboard", "/dashboard", "carbon:dashboard"))
        // dashboard-分析页
        val dashboardAnalysisId = uidGenerator.getUid()
        batchList.add(buildMenu(dashboardAnalysisId, dashboardId, dashboardSort++, "分析页", "/dashboard/analysis", "icon-park-outline:analysis"))
        // dashboard-工作台
        val dashboardWorkbenchId = uidGenerator.getUid()
        batchList.add(buildMenu(dashboardWorkbenchId, dashboardId, dashboardSort++, "工作台", "/dashboard/workbench", "icon-park-outline:workbench"))

        // 系统管理
        var systemSort = 1
        val systemId = uidGenerator.getUid()
        batchList.add(buildMenu(systemId, 0L, menuSort++, "系统管理", "/system", "system-uicons:grid"))
        // 系统管理-用户管理
        val userId = uidGenerator.getUid()
        val userIdR = uidGenerator.getUid()
        val userIdC = uidGenerator.getUid()
        val userIdU = uidGenerator.getUid()
        val userIdD = uidGenerator.getUid()
        batchList.add(buildMenu(userId, systemId, systemSort++, "用户管理", "/system/user", "ph:user-duotone"))
        batchList.add(buildButton(userIdR, userId, 1, "查看用户", "sys:user:view"))
        batchList.add(buildButton(userIdC, userId, 2, "新增用户", "sys:user:save"))
        batchList.add(buildButton(userIdU, userId, 3, "修改用户", "sys:user:update"))
        batchList.add(buildButton(userIdD, userId, 4, "删除用户", "sys:user:delete"))
        // 系统管理-角色管理
        val roleId = uidGenerator.getUid()
        val roleIdR = uidGenerator.getUid()
        val roleIdC = uidGenerator.getUid()
        val roleIdU = uidGenerator.getUid()
        val roleIdD = uidGenerator.getUid()
        batchList.add(buildMenu(roleId, systemId, systemSort++, "角色管理", "/system/role", "carbon:user-role"))
        batchList.add(buildButton(roleIdR, roleId, 1, "查看角色", "sys:role:view"))
        batchList.add(buildButton(roleIdC, roleId, 2, "新增角色", "sys:role:save"))
        batchList.add(buildButton(roleIdU, roleId, 3, "修改角色", "sys:role:update"))
        batchList.add(buildButton(roleIdD, roleId, 4, "删除角色", "sys:role:delete"))
        // 系统管理-菜单管理
        val menuId = uidGenerator.getUid()
        val menuIdR = uidGenerator.getUid()
        val menuIdC = uidGenerator.getUid()
        val menuIdU = uidGenerator.getUid()
        val menuIdD = uidGenerator.getUid()
        batchList.add(buildMenu(menuId, systemId, systemSort++, "菜单管理", "/system/menu", "ic:sharp-menu"))
        batchList.add(buildButton(menuIdR, menuId, 1, "查看菜单", "sys:menu:view"))
        batchList.add(buildButton(menuIdC, menuId, 2, "新增菜单", "sys:menu:save"))
        batchList.add(buildButton(menuIdU, menuId, 3, "修改菜单", "sys:menu:update"))
        batchList.add(buildButton(menuIdD, menuId, 4, "删除菜单", "sys:menu:delete"))
        // 系统管理-操作日志
        val optId = uidGenerator.getUid()
        val optIdR = uidGenerator.getUid()
        batchList.add(buildMenu(optId, systemId, systemSort++, "操作日志", "/system/optLog", "carbon:flow-logs-vpc"))
        batchList.add(buildButton(optIdR, optId, 1, "查看操作日志", "sys:optLog:view"))
        // 系统管理-登录日志
        val loginLogId = uidGenerator.getUid()
        val loginLogIdR = uidGenerator.getUid()
        batchList.add(buildMenu(loginLogId, systemId, systemSort++, "登录日志", "/system/loginLog", "carbon:flow-logs-vpc"))
        batchList.add(buildButton(loginLogIdR, loginLogId, 1, "查看登录日志", "sys:loginLog:view"))
        // 系统管理-文件管理
        val fileId = uidGenerator.getUid()
        val fileIdR = uidGenerator.getUid()
        val fileIdC = uidGenerator.getUid()
        val fileIdE = uidGenerator.getUid()
        val fileIdD = uidGenerator.getUid()
        batchList.add(buildMenu(fileId, systemId, systemSort++, "文件管理", "/system/file", "ic:baseline-file-copy"))
        batchList.add(buildButton(fileIdR, fileId, 1, "查看文件", "sys:file:view"))
        batchList.add(buildButton(fileIdC, fileId, 2, "上传文件", "sys:file:save"))
        batchList.add(buildButton(fileIdE, fileId, 3, "下载文件", "sys:file:export"))
        batchList.add(buildButton(fileIdD, fileId, 4, "删除文件", "sys:file:delete"))
        // 系统管理-数据字典
        val dictId = uidGenerator.getUid()
        val dictIdR = uidGenerator.getUid()
        val dictIdC = uidGenerator.getUid()
        val dictIdU = uidGenerator.getUid()
        val dictIdD = uidGenerator.getUid()
        batchList.add(buildMenu(dictId, systemId, systemSort++, "数据字典", "/system/dict", "arcticons:colordict"))
        batchList.add(buildButton(dictIdR, dictId, 1, "查看字典", "sys:dict:view"))
        batchList.add(buildButton(dictIdC, dictId, 2, "新增字典", "sys:dict:save"))
        batchList.add(buildButton(dictIdU, dictId, 3, "修改字典", "sys:dict:update"))
        batchList.add(buildButton(dictIdD, dictId, 4, "删除字典", "sys:dict:delete"))
        val dictItemR = uidGenerator.getUid()
        val dictItemC = uidGenerator.getUid()
        val dictItemU = uidGenerator.getUid()
        val dictItemD = uidGenerator.getUid()
        batchList.add(buildButton(dictItemR, dictId, 5, "查看字典项", "sys:dictItem:view"))
        batchList.add(buildButton(dictItemC, dictId, 6, "新增字典项", "sys:dictItem:save"))
        batchList.add(buildButton(dictItemU, dictId, 7, "修改字典项", "sys:dictItem:update"))
        batchList.add(buildButton(dictItemD, dictId, 8, "删除字典项", "sys:dictItem:delete"))

        menuService.saveBatch(batchList)

        return mutableListOf(
            dashboardId,
            dashboardAnalysisId, dashboardWorkbenchId,
            systemId,
            userId, userIdR ,userIdC, userIdU, userIdD,
            roleId, roleIdR, roleIdC, roleIdU, roleIdD,
            menuId, menuIdR, menuIdC, menuIdU, menuIdD,
            optId, optIdR,
            loginLogId, loginLogIdR,
            fileId, fileIdR, fileIdC, fileIdE, fileIdD,
            dictId, dictIdR, dictIdC, dictIdU, dictIdD,
            dictItemR, dictItemC, dictItemU, dictItemD,
        )
    }


    /**
     * 初始化角色
     */
    fun initRole(): Long {
        val batchList: MutableList<SysRole> = mutableListOf()

        val superAdminId = uidGenerator.getUid()
        val adminId = uidGenerator.getUid()
        val userId = uidGenerator.getUid()
        batchList.add(SysRole().apply { id = superAdminId; name = "超级管理员"; code = "SUPER_ADMIN"; describe = "超级管理员，拥有至高无上的权利"  })
        batchList.add(SysRole().apply { id = adminId; name = "管理员"; code = "ADMIN"; describe = "管理员，拥有99%的权利"  })
        batchList.add(SysRole().apply { id = userId; name = "普通用户"; code = "USER"; describe = "普通用户，拥有管理员赋予的权利"  })
        roleService.saveBatch(batchList)

        return superAdminId
    }

    /**
     * 初始化超级管理员菜单权限
     * @param superAdminId Long     超级管理员id
     * @param menuIds List<Long>    菜单id
     */
    fun initRoleMenu(superAdminId: Long, menuIds: List<Long>) {
        val batchList: MutableList<SysRoleMenu> = mutableListOf()
        menuIds.forEach {
            batchList.add(SysRoleMenu().apply { roleId = superAdminId; menuId = it })
        }

        roleMenuService.saveBatch(batchList)
    }

    /**
     * 初始化超级管理员用户
     */
    fun initAdminUser(): Long {
        val userId = uidGenerator.getUid()
        val passwordEncoder = BCrypt.hashpw("admin")
        userService.save(SysUser().apply {
            id = userId
            username = "zeta管理员"
            account = "zetaAdmin"
            password = passwordEncoder
            sex = SexEnum.MALE.code
            state = UserStateEnum.NORMAL.code
            readonly = true
        })

        return userId
    }

    /**
     * 初始化用户角色
     * @param userId Long           用户id
     * @param superAdminId Long     角色（超级管理员）id
     */
    fun initUserRole(userId: Long, superAdminId: Long) {
        userRoleService.save(SysUserRole(userId, superAdminId))
    }

    /**
     * 初始化数据字典
     */
    fun initSysDict() {
        // 初始化字典
        val dictList = mutableListOf<SysDict>()
        val dictId = uidGenerator.getUid()
        dictList.add(SysDict().apply { id = dictId; name = "设备状态"; code = "device_status"; describe = "设备运行状态"; sortValue = 0 })
        sysDictService.saveBatch(dictList)

        // 初始化字典项
        val dictItemList = mutableListOf<SysDictItem>()
        dictItemList.add(SysDictItem().apply { this.id = uidGenerator.getUid(); this.dictId = dictId; name = "运行"; value = "RUNNING";  describe = "设备正在运行"; sortValue = 1 })
        dictItemList.add(SysDictItem().apply { this.id = uidGenerator.getUid(); this.dictId = dictId; name = "停止"; value = "WAITING";  describe = "设备已停止"; sortValue = 2 })
        sysDictItemService.saveBatch(dictItemList)
    }

    /**
     * 构造菜单
     *
     * @param id
     * @param parentId
     * @param sortValue
     * @param label
     * @param path
     * @param icon
     */
    private fun buildMenu(id: Long, parentId: Long, sortValue: Int, label: String, path: String, icon: String): SysMenu {
        // 将"/system" => "system";  "/system/user" => "system_user";  "/system/user/123" => "system_user_123"
        val name = path.split("/").filterNot { it.isBlank() }.joinToString("_")
        // 判断组件
        val component = if (parentId == 0L) { "basic" } else { "self" }
        return SysMenu().apply {
            this.id = id
            this.parentId = parentId
            this.sortValue = sortValue
            this.label = label
            this.path = path
            this.name = name
            this.component = component
            this.icon = icon
            this.type = MenuTypeEnum.MENU.name
            this.authority = ""
        }
    }

    /**
     * 构造按钮
     *
     * @param id
     * @param parentId
     * @param sortValue
     * @param label
     * @param authority
     */
    private fun buildButton(id: Long, parentId: Long, sortValue: Int, label: String, authority: String): SysMenu {
        return SysMenu().apply {
            this.id = id
            this.parentId = parentId
            this.sortValue = sortValue
            this.label = label
            this.type = MenuTypeEnum.RESOURCE.name
            this.authority = authority
        }
    }

}
