package org.zetaframework.core.redis.util

import org.springframework.data.redis.connection.DataType
import org.springframework.data.redis.core.*
import org.springframework.data.redis.core.ZSetOperations.TypedTuple
import org.springframework.lang.NonNull
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Redis工具类
 *
 * @author WangFan
 * @author gcc
 * @version 1.1 (GitHub文档: https://github.com/whvcse/RedisUtil )
 */
@Suppress("UNCHECKED_CAST", "DEPRECATION", "unused")
class RedisUtil(private val redisTemplate: RedisTemplate<String, Any>) {

    val valueOps: ValueOperations<String, Any> = redisTemplate.opsForValue()
    val hashOps: HashOperations<String, Any, Any> = redisTemplate.opsForHash()
    val listOps: ListOperations<String, Any> = redisTemplate.opsForList()
    val setOps: SetOperations<String, Any> = redisTemplate.opsForSet()
    val zSetOps: ZSetOperations<String, Any> = redisTemplate.opsForZSet()


    /** -------------------key相关操作--------------------- */

    /**
     * 修改key的名称
     * @param oldKey String
     * @param newKey String
     */
    fun rename(oldKey: String, newKey: String) {
        redisTemplate.rename(oldKey, newKey)
    }

    /**
     * 仅当key不存在时才修改key的名称
     * @param oldKey String
     * @param newKey String
     * @return Boolean
     */
    fun renameIfAbsent(oldKey: String, newKey: String): Boolean {
        return redisTemplate.renameIfAbsent(oldKey, newKey)
    }

    /**
     * 删除key
     * @param key String
     */
    fun delete(key: String) {
        redisTemplate.delete(key)
    }

    /**
     * 批量删除key
     * @param keys Collection<String>
     */
    fun delete(keys: Collection<String>) {
        redisTemplate.delete(keys)
    }

    /**
     * 是否存在key
     * @param key String
     * @return Boolean
     */
    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    /**
     * 获取key的剩余时间
     * @param key String
     * @return Long
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key)
    }

    /**
     * 获取key的剩余时间
     * @param key String
     * @return Long
     */
    fun getExpire(key: String, unit: TimeUnit): Long {
        return redisTemplate.getExpire(key, unit)
    }

    /**
     * 设置key的过期时间
     * @param key String
     * @param timeout Long
     * @param unit TimeUnit
     * @return Boolean
     */
    fun expire(key: String, timeout: Long, unit: TimeUnit): Boolean {
        return redisTemplate.expire(key, timeout, unit)
    }

    /**
     * 设置key的过期时间
     * @param key String
     * @param date Date
     * @return Boolean
     */
    fun expireAt(key: String, date: Date): Boolean {
        return redisTemplate.expireAt(key, date)
    }

    /**
     * 移除key的过期时间
     * @param key String
     * @return Boolean
     */
    fun persist(key: String): Boolean {
        return redisTemplate.persist(key)
    }

    /**
     * 查找匹配的key
     * @param pattern String
     * @return Set<String>
     */
    fun keys(pattern: String): MutableSet<String> {
        return redisTemplate.keys(pattern)
    }

    /**
     * 从当前db中随机获取一个key
     * @return String
     */
    fun randomKey(): String {
        return redisTemplate.randomKey()
    }

    /**
     * 获取key值的类型
     * @param key String
     * @return DataType
     */
    fun type(key: String): DataType {
        return redisTemplate.type(key)
    }

    /**
     * 将当前数据库的key移动到指定的数据库db中
     * @param key String
     * @param dbIndex Int
     * @return Boolean
     */
    fun move(key: String, dbIndex: Int): Boolean {
        return redisTemplate.move(key, dbIndex)
    }

    /**
     * 序列化key
     * @param key String
     * @return ByteArray?
     */
    fun dump(key: String): ByteArray {
        return redisTemplate.dump(key)
    }

    /**
     * 判断缓存值是否为空对象
     * @param value T
     * @return Boolean
     */
    fun <T> isNullVal(value: T): Boolean {
        val isNull = value == null || NullVal::class.java == value.javaClass
        return isNull || value is Map<*, *> && (value as Map<*, *>).isEmpty()
    }

    /**
     * 创建一个空对象
     * @return NullVal
     */
    fun createNullVal(): NullVal = NullVal()

    /**
     * 返回正常值或null值
     * @param value T
     * @return T?
     */
    fun <T> returnVal(value: T): T? = if (isNullVal(value)) null else value

    /** -------------------string相关操作--------------------- */

    /**
     * 获取指定key的值
     * @param key String
     * @return T?
     */
    fun <T> get(key: String): T? {
        return returnVal(valueOps[key] as T?)
    }

    /**
     * 设置指定key的值
     * @param key String
     * @param value Any?
     */
    operator fun set(key: String, value: Any?) {
        valueOps[key] = value ?: createNullVal()
    }

    /**
     * 若key存在时，为key设置指定的值和过期时间
     * @param key String
     * @param value Any?
     * @param timeout Long
     * @param unit TimeUnit
     */
    fun setEx(key: String, value: Any?, timeout: Long, unit: TimeUnit) {
        valueOps.set(key, value ?: createNullVal(), timeout, unit)
    }

    /**
     * 若key不存在时，为key设置指定的值和过期时间
     * @param key String
     * @param value Any?
     * @return Boolean?
     */
    fun setIfAbsent(key: String, value: Any?): Boolean? {
        return valueOps.setIfAbsent(key, value ?: createNullVal())
    }

    /**
     * 将给定key的值设为value, 并返回key的旧值old value
     * @param key String
     * @param value Any?
     * @return T?
     */
    fun <T> getAndSet(key: String, value: Any?): T? {
        return returnVal(valueOps.getAndSet(key, value ?: createNullVal()) as T?)
    }

    /**
     * 获取字符串的长度
     * @param key String
     * @return Long?
     */
    fun size(key: String): Long? = valueOps.size(key)

    /**
     * 批量获取
     * @param keys
     * @return
     */
    fun multiGet(keys: Collection<String>): MutableList<Any>? {
        return valueOps.multiGet(keys)
    }

    /**
     * 批量添加
     * @param maps Map<String, Any?>
     */
    fun multiSet(maps: Map<String, Any?>) {
        valueOps.multiSet(maps)
    }

    /**
     * 同时设置一个或多个key-value对，当且仅当所有给定key都不存在
     * @param maps Map<String, Any?>
     * @return Boolean?
     */
    fun multiSetIfAbsent(maps: Map<String, Any?>): Boolean? {
        return valueOps.multiSetIfAbsent(maps)
    }

    /**
     * 增加(自增长), 负数则为自减
     * @param key
     * @param increment
     * @return
     */
    fun incrBy(key: String, increment: Long): Long? {
        return valueOps.increment(key, increment)
    }

    /**
     * 增加(自增长), 负数则为自减
     * @param key
     * @param increment
     * @return
     */
    fun incrByFloat(key: String, increment: Double): Double? {
        return valueOps.increment(key, increment)
    }

    /**
     * 追加到末尾
     * @param key
     * @param value
     * @return
     */
    fun append(key: String, value: String?): Int? {
        return valueOps.append(key, value ?: "")
    }


    /** -------------------hash相关操作-------------------------  */

    /**
     * 获取存储在哈希表中指定字段的值
     * @param key
     * @param field
     * @return
     */
    fun <T> hGet(key: String, field: Any): T? {
        return returnVal(hashOps[key, field] as T?)
    }

    /**
     * 获取所有给定字段的值
     * @param key
     * @return
     */
    fun hGetAll(key: String): MutableMap<Any, Any> {
        return hashOps.entries(key)
    }

    /**
     * 获取所有给定字段的值
     * @param key
     * @param fields
     * @return
     */
    fun hMultiGet(key: String, fields: Collection<Any>): MutableList<Any> {
        return hashOps.multiGet(key, fields)
    }

    /**
     * 将哈希表key中的字段hashKey的值设为value
     * @param key String
     * @param hashKey String
     * @param value Any?
     */
    fun hPut(key: String, hashKey: String, value: Any?) {
        hashOps.put(key, hashKey, value ?: createNullVal())
    }

    /**
     * 将多个 field-value (字段-值对)设置到哈希表中
     * @param key String
     * @param maps Map<String, Any?>
     */
    fun hPutAll(key: String, maps: Map<String, Any?>) {
        hashOps.putAll(key, maps)
    }

    /**
     * 仅当hashKey不存在时才设置
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    fun hPutIfAbsent(key: String, hashKey: String, value: Any?): Boolean {
        return hashOps.putIfAbsent(key, hashKey, value ?: createNullVal())
    }

    /**
     * 删除一个或多个哈希表字段
     * @param key
     * @param fields
     * @return
     */
    fun hDelete(key: String, vararg fields: Any?): Long {
        return hashOps.delete(key, *fields)
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * @param key
     * @param field
     * @return
     */
    fun hExists(key: String, field: String): Boolean {
        return hashOps.hasKey(key, field)
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     * @param key
     * @param field
     * @param increment
     * @return
     */
    fun hIncrBy(key: String, field: Any, increment: Long): Long {
        return hashOps.increment(key, field, increment)
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     * @param key
     * @param field
     * @param delta
     * @return
     */
    fun hIncrByFloat(key: String, field: Any, delta: Double): Double {
        return hashOps.increment(key, field, delta)
    }

    /**
     * 获取所有哈希表中的字段
     * @param key
     * @return
     */
    fun hKeys(key: String): MutableSet<Any> {
        return hashOps.keys(key)
    }

    /**
     * 获取哈希表中字段的数量
     * @param key
     * @return
     */
    fun hSize(key: String): Long = hashOps.size(key)

    /**
     * 获取哈希表中所有值
     * @param key
     * @return
     */
    fun hValues(key: String): MutableList<Any> {
        return hashOps.values(key)
    }

    /**
     * 迭代哈希表中的键值对
     * @param key
     * @param options
     * @return
     */
    fun hScan(key: String, options: ScanOptions): Cursor<MutableMap.MutableEntry<Any, Any>> {
        return hashOps.scan(key, options)
    }

    /** ------------------------list相关操作----------------------------  */

    /**
     * 通过索引获取列表中的元素
     * @param key
     * @param index
     * @return
     */
    fun <T> lIndex(key: String, index: Long): T? {
        return returnVal(listOps.index(key, index) as T?)
    }

    /**
     * 获取列表指定范围内的元素
     * @param key
     * @param start 开始位置, 0是开始位置
     * @param end 结束位置, -1返回所有
     * @return
     */
    fun lRange(key: String, start: Long, end: Long): MutableList<Any>? {
        return listOps.range(key, start, end)
    }

    /**
     * 存储在list头部
     * @param key
     * @param value
     * @return
     */
    fun lLeftPush(key: String, value: Any): Long? {
        return listOps.leftPush(key, value)
    }

    /**
     * 存储多个值在list头部
     * @param key
     * @param value
     * @return
     */
    fun lLeftPushAll(key: String, vararg value: Any?): Long? {
        return listOps.leftPushAll(key, *value)
    }

    /**
     * 当list存在的时候才加入
     * @param key
     * @param value
     * @return
     */
    fun lLeftPushIfPresent(key: String, value: Any): Long? {
        return listOps.leftPushIfPresent(key, value)
    }

    /**
     * 如果pivot存在,再pivot前面添加
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    fun lLeftPush(key: String, pivot: Any, value: Any): Long? {
        return listOps.leftPush(key, pivot, value)
    }

    /**
     * 存储多个值在list尾部
     * @param key
     * @param value
     * @return
     */
    fun lRightPush(key: String, value: Any): Long? {
        return listOps.rightPush(key, value)
    }

    /**
     * 存储多个值在list尾部
     * @param key
     * @param value
     * @return
     */
    fun lRightPushAll(key: String, vararg value: Any?): Long? {
        return listOps.rightPushAll(key, *value)
    }

    /**
     * 为已存在的列表添加值
     * @param key
     * @param value
     * @return
     */
    fun lRightPushIfPresent(key: String, value: Any): Long? {
        return listOps.rightPushIfPresent(key, value)
    }

    /**
     * 在pivot元素的右边添加值
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    fun lRightPush(key: String, pivot: Any, value: Any): Long? {
        return listOps.rightPush(key, pivot, value)
    }

    /**
     * 通过索引设置列表元素的值
     * @param key
     * @param index
     * @param value
     */
    fun lSet(key: String, index: Long, value: Any) {
        listOps[key, index] = value
    }

    /**
     * 移出并获取列表的第一个元素
     * @param key
     * @return 删除的元素
     */
    fun <T> lLeftPop(key: String): T? {
        return listOps.leftPop(key) as T?
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    fun <T> lBLeftPop(key: String, timeout: Long, unit: TimeUnit?): T? {
        return listOps.leftPop(key, timeout, unit!!) as T?
    }

    /**
     * 移除并获取列表最后一个元素
     * @param key
     * @return 删除的元素
     */
    fun <T> lRightPop(key: String): T? {
        return listOps.rightPop(key) as T?
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    fun <T> lBRightPop(key: String, timeout: Long, unit: TimeUnit?): T? {
        return listOps.rightPop(key, timeout, unit!!) as T?
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    fun <T> lRightPopAndLeftPush(@NonNull sourceKey: String?, destinationKey: String?): T? {
        return listOps.rightPopAndLeftPush(sourceKey!!, destinationKey!!) as T?
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    fun <T> lBRightPopAndLeftPush(sourceKey: String, destinationKey: String, timeout: Long, unit: TimeUnit): T? {
        return listOps.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit) as T?
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index
     *          index=0, 删除所有值等于value的元素;
     *          index>0, 从头部开始删除第一个值等于value的元素;
     *          index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    fun lRemove(key: String, index: Long, value: Any): Long? {
        return listOps.remove(key, index, value)
    }

    /**
     * 裁剪list
     * @param key
     * @param start
     * @param end
     */
    fun lTrim(key: String, start: Long, end: Long) {
        listOps.trim(key, start, end)
    }

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    fun lLen(key: String): Long? {
        return listOps.size(key)
    }

    /** --------------------set相关操作--------------------------  */
    /**
     * set添加元素
     * @param key
     * @param values
     * @return
     */
    fun sAdd(key: String, vararg values: Any?): Long? {
        return setOps.add(key, *values)
    }

    /**
     * set移除元素
     * @param key
     * @param values
     * @return
     */
    fun sRemove(key: String, vararg values: Any?): Long? {
        return setOps.remove(key, *values)
    }

    /**
     * 移除并返回集合的一个随机元素
     * @param key
     * @return
     */
    fun <T> sPop(key: String): T? {
        return setOps.pop(key) as T?
    }

    /**
     * 将元素value从一个集合移到另一个集合
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    fun sMove(key: String, value: Any?, destKey: String?): Boolean? {
        return setOps.move(key, value!!, destKey!!)
    }

    /**
     * 获取集合的大小
     * @param key
     * @return
     */
    fun sSize(key: String): Long? {
        return setOps.size(key)
    }

    /**
     * 判断集合是否包含value
     * @param key
     * @param value
     * @return
     */
    fun sIsMember(key: String, value: Any): Boolean? {
        return setOps.isMember(key, value)
    }

    /**
     * 获取两个集合的交集
     * @param key
     * @param otherKey
     * @return
     */
    fun <V> sIntersect(key: String, otherKey: String): MutableSet<Any>? {
        return setOps.intersect(key, otherKey)
    }

    /**
     * 获取key集合与多个集合的交集
     * @param key
     * @param otherKeys
     * @return
     */
    fun sIntersect(key: String, otherKeys: Collection<String?>): MutableSet<Any>? {
        return setOps.intersect(key, otherKeys)
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    fun sIntersectAndStore(key: String, otherKey: String, destKey: String): Long? {
        return setOps.intersectAndStore(key, otherKey, destKey)
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    fun sIntersectAndStore(key: String, otherKeys: Collection<String?>, destKey: String): Long? {
        return setOps.intersectAndStore(key, otherKeys, destKey)
    }

    /**
     * 获取两个集合的并集
     * @param key
     * @param otherKeys
     * @return
     */
    fun <V> sUnion(key: String, otherKeys: String): MutableSet<Any>? {
        return setOps.union(key, otherKeys)
    }

    /**
     * 获取key集合与多个集合的并集
     * @param key
     * @param otherKeys
     * @return
     */
    fun <V> sUnion(key: String, otherKeys: Collection<String?>): MutableSet<Any>? {
        return setOps.union(key, otherKeys)
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    fun sUnionAndStore(key: String, otherKey: String, destKey: String): Long? {
        return setOps.unionAndStore(key, otherKey, destKey)
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    fun sUnionAndStore(key: String, otherKeys: Collection<String?>, destKey: String): Long? {
        return setOps.unionAndStore(key, otherKeys, destKey)
    }

    /**
     * 获取两个集合的差集
     * @param key
     * @param otherKey
     * @return
     */
    fun <V> sDifference(key: String, otherKey: String): MutableSet<Any>? {
        return setOps.difference(key, otherKey)
    }

    /**
     * 获取key集合与多个集合的差集
     * @param key
     * @param otherKeys
     * @return
     */
    fun <V> sDifference(key: String, otherKeys: Collection<String?>): MutableSet<Any>? {
        return setOps.difference(key, otherKeys)
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    fun sDifference(key: String, otherKey: String, destKey: String): Long? {
        return setOps.differenceAndStore(key, otherKey, destKey)
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    fun sDifference(key: String, otherKeys: Collection<String?>, destKey: String): Long? {
        return setOps.differenceAndStore(key, otherKeys, destKey)
    }

    /**
     * 返回集合 key 中的所有成员。
     * @param key
     * @return
     */
    fun <V> setMembers(key: String): MutableSet<Any>? {
        return setOps.members(key)
    }

    /**
     * 随机获取集合中的一个元素
     * @param key
     * @return
     */
    fun <T> sRandomMember(key: String): T {
        return setOps.randomMember(key) as T
    }

    /**
     * 随机获取集合中count个元素
     * @param key
     * @param count
     * @return
     */
    fun <V> sRandomMembers(key: String, count: Long): MutableList<Any>? {
        return setOps.randomMembers(key, count)
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     * @param key
     * @param count
     * @return
     */
    fun <V> sDistinctRandomMembers(key: String, count: Long): MutableSet<Any>? {
        return setOps.distinctRandomMembers(key, count)
    }


    /**------------------zSet相关操作-------------------------------- */

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     * @param key
     * @param value
     * @param score
     * @return
     */
    fun zAdd(key: String, value: Any, score: Double): Boolean? {
        return zSetOps.add(key, value, score)
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * @param key
     * @param scoreMembers
     * @return
     */
    fun zAdd(key: String, scoreMembers: Map<Any, Double>): Long? {
        val tuples: MutableSet<TypedTuple<Any>> = HashSet()
        scoreMembers.forEach { (score: Any, member: Double) ->
            tuples.add(
                DefaultTypedTuple(score, member)
            )
        }
        return zSetOps.add(key, tuples)
    }

    /**
     * 从有序集中删除values。返回已移除元素的数量
     * @param key
     * @param values
     * @return
     */
    fun zRemove(key: String, vararg values: Any?): Long? {
        return zSetOps.remove(key, *values)
    }

    /**
     * 增加元素的score值，并返回增加后的值
     * @param key
     * @param value
     * @param delta
     * @return
     */
    fun zIncrementScore(key: String, value: Any, delta: Double): Double? {
        return zSetOps.incrementScore(key, value, delta)
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     * @param key
     * @param value
     * @return 0表示第一位
     */
    fun zRank(key: String, value: Any): Long? {
        return zSetOps.rank(key, value)
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     * @param key
     * @param value
     * @return
     */
    fun zReverseRank(key: String, value: Any): Long? {
        return zSetOps.reverseRank(key, value)
    }

    /**
     * 获取集合的元素, 从小到大排序
     * @param key
     * @param start 开始位置
     * @param end 结束位置, -1查询所有
     * @return
     */
    fun zRange(key: String, start: Long, end: Long): MutableSet<Any>? {
        return zSetOps.range(key, start, end)
    }

    /**
     * 获取集合元素, 并且把score值也获取
     * @param key
     * @param start
     * @param end
     * @return
     */
    fun zRangeWithScores(key: String, start: Long, end: Long): MutableSet<TypedTuple<Any>>? {
        return zSetOps.rangeWithScores(key, start, end)
    }

    /**
     * 根据Score值查询集合元素
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    fun zRangeByScore(key: String, min: Double, max: Double): MutableSet<Any>? {
        return zSetOps.rangeByScore(key, min, max)
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    fun zRangeByScoreWithScores(key: String, min: Double, max: Double): MutableSet<TypedTuple<Any>>? {
        return zSetOps.rangeByScoreWithScores(key, min, max)
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    fun zRangeByScoreWithScores(key: String, min: Double, max: Double, start: Long, end: Long): MutableSet<TypedTuple<Any>>? {
        return zSetOps.rangeByScoreWithScores(key, min, max, start, end)
    }

    /**
     * 获取集合的元素, 从大到小排序
     * @param key
     * @param start
     * @param end
     * @return
     */
    fun zReverseRange(key: String, start: Long, end: Long): MutableSet<Any>? {
        return zSetOps.reverseRange(key, start, end)
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     * @param key
     * @param start
     * @param end
     * @return
     */
    fun zReverseRangeWithScores(key: String, start: Long, end: Long): MutableSet<TypedTuple<Any>>? {
        return zSetOps.reverseRangeWithScores(key, start, end)
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     * @param key
     * @param min
     * @param max
     * @return
     */
    fun zReverseRangeByScore(key: String, min: Double, max: Double): MutableSet<Any>? {
        return zSetOps.reverseRangeByScore(key, min, max)
    }



    /**
     * 根据Score值查询集合元素
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    fun zReverseRangeByScore(key: String, min: Double, max: Double, start: Long, end: Long): MutableSet<Any>? {
        return zSetOps.reverseRangeByScore(key, min, max, start, end)
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     * @param key
     * @param min
     * @param max
     * @return
     */
    fun zReverseRangeByScoreWithScores(key: String, min: Double, max: Double): MutableSet<TypedTuple<Any>>? {
        return zSetOps.reverseRangeByScoreWithScores(key, min, max)
    }

    /**
     * 根据score值获取集合元素数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    fun zCount(key: String, min: Double, max: Double): Long? {
        return zSetOps.count(key, min, max)
    }

    /**
     * 获取集合大小
     * @param key
     * @return
     */
    fun zSize(key: String): Long? {
        return zSetOps.size(key)
    }

    /**
     * 获取集合大小
     * @param key
     * @return
     */
    fun zZCard(key: String): Long? {
        return zSetOps.zCard(key)
    }

    /**
     * 获取集合中value元素的score值
     * @param key
     * @param value
     * @return
     */
    fun zScore(key: String, value: Any): Double? {
        return zSetOps.score(key, value)
    }

    /**
     * 移除指定索引位置的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    fun zRemoveRange(key: String, start: Long, end: Long): Long? {
        return zSetOps.removeRange(key, start, end)
    }

    /**
     * 根据指定的score值的范围来移除成员
     * @param key
     * @param min
     * @param max
     * @return
     */
    fun zRemoveRangeByScore(key: String, min: Double, max: Double): Long? {
        return zSetOps.removeRangeByScore(key, min, max)
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    fun zUnionAndStore(key: String, otherKey: String, destKey: String): Long? {
        return zSetOps.unionAndStore(key, otherKey, destKey)
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    fun zUnionAndStore(key: String, otherKeys: Collection<String?>, destKey: String): Long? {
        return zSetOps.unionAndStore(key, otherKeys, destKey)
    }

    /**
     * 交集
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    fun zIntersectAndStore(key: String, otherKey: String, destKey: String): Long? {
        return zSetOps.intersectAndStore(key, otherKey, destKey)
    }

    /**
     * 交集
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    fun zIntersectAndStore(key: String, otherKeys: Collection<String?>, destKey: String): Long? {
        return zSetOps.intersectAndStore(key, otherKeys, destKey)
    }

    /**
     * 在key处迭代zset中的元素。
     * @param key
     * @param options
     * @return
     */
    fun zScan(key: String, options: ScanOptions): Cursor<TypedTuple<Any>> {
        return zSetOps.scan(key, options)
    }

}
