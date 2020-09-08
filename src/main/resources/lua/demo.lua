local key = 'test_' .. KEYS[1]

local total = ARGV[1]
local remainder = ARGV[2]
local origin_total = redis.call("hget", key, "total")
local origin_remainder = redis.call("hget", key, "remainder")
if origin_total then
    total = origin_total + total
end
if origin_remainder then
    remainder = remainder + origin_remainder
end
redis.call("hset", key, "total", total)
redis.call("hset", key, "remainder", remainder)
return 0