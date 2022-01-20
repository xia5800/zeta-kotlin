package com.zeta

import com.zeta.system.model.entity.*
import com.zeta.system.model.enumeration.MenuTypeEnum
import com.zeta.system.model.enumeration.SexEnum
import com.zeta.system.model.enumeration.UserStateEnum
import com.zeta.system.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    /**
     * 初始化数据库
     */
    // @Test 注释掉，防止maven打包的时候没有跳过测试
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
    }


    /**
     * 初始化系统菜单、权限
     */
    fun initMenu(): List<Long> {
        val batchList: MutableList<SysMenu> = mutableListOf()
        var menuSort = 1;

        // 系统管理
        var systemSort = 1;
        val systemId = uidGenerator.getUid()
        batchList.add(SysMenu().apply { id = systemId; parentId = 0L; sortValue = menuSort++; label = "系统管理"; type = MenuTypeEnum.MENU.name; authority = "" })
        // 系统管理-用户管理
        val userId = uidGenerator.getUid()
        val userIdR = uidGenerator.getUid()
        val userIdC = uidGenerator.getUid()
        val userIdU = uidGenerator.getUid()
        val userIdD = uidGenerator.getUid()
        batchList.add(SysMenu().apply { id = userId; parentId = systemId; sortValue = systemSort++; label = "用户管理"; type = MenuTypeEnum.MENU.name; authority = "" })
        batchList.add(SysMenu().apply { id = userIdR; parentId = userId; sortValue = 1; label = "查看用户"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:user:view" })
        batchList.add(SysMenu().apply { id = userIdC; parentId = userId; sortValue = 2; label = "新增用户"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:user:save" })
        batchList.add(SysMenu().apply { id = userIdU; parentId = userId; sortValue = 3; label = "修改用户"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:user:update" })
        batchList.add(SysMenu().apply { id = userIdD; parentId = userId; sortValue = 4; label = "删除用户"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:user:delete" })
        // 系统管理-角色管理
        val roleId = uidGenerator.getUid()
        val roleIdR = uidGenerator.getUid()
        val roleIdC = uidGenerator.getUid()
        val roleIdU = uidGenerator.getUid()
        val roleIdD = uidGenerator.getUid()
        batchList.add(SysMenu().apply { id = roleId; parentId = systemId; sortValue = systemSort++; label = "角色管理"; type = MenuTypeEnum.MENU.name; authority = "" })
        batchList.add(SysMenu().apply { id = roleIdR; parentId = roleId; sortValue = 1; label = "查看角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:role:view" })
        batchList.add(SysMenu().apply { id = roleIdC; parentId = roleId; sortValue = 2; label = "新增角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:role:save" })
        batchList.add(SysMenu().apply { id = roleIdU; parentId = roleId; sortValue = 3; label = "修改角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:role:update" })
        batchList.add(SysMenu().apply { id = roleIdD; parentId = roleId; sortValue = 4; label = "删除角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:role:delete" })
        // 系统管理-菜单管理
        val menuId = uidGenerator.getUid()
        val menuIdR = uidGenerator.getUid()
        val menuIdC = uidGenerator.getUid()
        val menuIdU = uidGenerator.getUid()
        val menuIdD = uidGenerator.getUid()
        batchList.add(SysMenu().apply { id = menuId; parentId = systemId; sortValue = systemSort++; label = "菜单管理"; type = MenuTypeEnum.MENU.name; authority = "" })
        batchList.add(SysMenu().apply { id = menuIdR; parentId = menuId; sortValue = 1; label = "查看角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:menu:view" })
        batchList.add(SysMenu().apply { id = menuIdC; parentId = menuId; sortValue = 2; label = "新增角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:menu:save" })
        batchList.add(SysMenu().apply { id = menuIdU; parentId = menuId; sortValue = 3; label = "修改角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:menu:update" })
        batchList.add(SysMenu().apply { id = menuIdD; parentId = menuId; sortValue = 4; label = "删除角色"; type = MenuTypeEnum.RESOURCE.name; authority = "sys:menu:delete" })

        menuService.saveBatch(batchList)

        return mutableListOf(
            systemId,
            userId, userIdR ,userIdC, userIdU, userIdD,
            roleId, roleIdR, roleIdC, roleIdU, roleIdD,
            menuId, menuIdR, menuIdC, menuIdU, menuIdD
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
        val passwordEncoder = passwordEncoder.encode("admin")
        userService.save(SysUser().apply {
            id = userId
            username = "zeta管理员"
            account = "zetaAdmin"
            password = passwordEncoder
            sex = SexEnum.MALE.code
            state = UserStateEnum.NORMAL.code
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

}
