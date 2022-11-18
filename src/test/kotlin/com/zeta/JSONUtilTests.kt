package com.zeta

import com.fasterxml.jackson.core.type.TypeReference
import com.zeta.system.model.entity.SysRole
import com.zeta.system.model.entity.SysUser
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.zetaframework.core.utils.JSONUtil

/**
 * JSONUtil工具类测试类
 *
 * @author gcc
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JSONUtilTests {

    private var userListJsonStr: String? = null

    @BeforeAll
    fun before() {
        val userList = mutableListOf(
            SysUser().apply { this.id = 1L; this.account = "test" },
            SysUser().apply { this.id = 2L; this.account = "test2" },
        )
        userListJsonStr = JSONUtil.toJsonStr(userList)
    }

    /**
     * 将userListJsonStr转换成List<SysUser>测试
     */
    @Test
    fun parseUserListTest() {
        // 转成List<SysUser>对象
        val userList = JSONUtil.parseObject(userListJsonStr, object: TypeReference<List<SysUser>>() {})
        userList?.forEach(::println)
        assert(userList?.size == 2)

        // 转成List<SysRole>对象
        val roleList = JSONUtil.parseObject(userListJsonStr, object: TypeReference<List<SysRole>>() {})
        assert(roleList == null)
    }

}
