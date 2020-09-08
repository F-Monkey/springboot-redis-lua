package redis;

import org.cn.monkey.redis.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = RedisApplication.class)
@RunWith(SpringRunner.class)
public class HttpTest {

    @Resource
    RedisApplication redisApplication;

    @Test
    public void testRedis() {
        String increase = redisApplication.increase(1, 2);
        System.out.println(increase);
    }
}
