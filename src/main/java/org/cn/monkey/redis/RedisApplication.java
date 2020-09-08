package org.cn.monkey.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    private final RedisTemplate<String, String> redisTemplate;

    public RedisApplication(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public DefaultRedisScript<String> defaultRedisScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/demo.lua")));
        return defaultRedisScript;
    }

    @RequestMapping("/test")
    public String increase(int total, int reminder) {
        String key = "key";
        String totalArg = String.valueOf(total);
        String reminderArg = String.valueOf(reminder);
        Long result = this.redisTemplate.execute((RedisConnection connection) -> connection.eval(
                defaultRedisScript().getScriptAsString().getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                totalArg.getBytes(),
                reminderArg.getBytes()));
        if (result != null && result == 0) {
            return "ok";
        }
        return "fail";
    }
}
