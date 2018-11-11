import com.leyou.LyGoodsWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 98050
 * @Time: 2018-10-25 22:58
 * @Feature:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyGoodsWebApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps("leyou:goods:detail:1");
        hashOperations.put("1","22222222");
        hashOperations.expire(10, TimeUnit.SECONDS);
    }
}
