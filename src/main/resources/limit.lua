local key_local = redis.call('setnx', KEYS[1], 0)
if tonumber(key_local) == 0 then
    if tonumber(redis.call('get', KEYS[1])) >= tonumber(ARGV[2]) then
        return false
    else
        redis.call('incr', KEYS[1])
        return true
    end
else
    redis.call('incr', KEYS[1])
    redis.call('expire', KEYS[1], ARGV[1])
    return true
end
