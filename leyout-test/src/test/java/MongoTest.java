import com.leyou.test.LyTestApplication;
import com.leyou.test.dao.SpitDao;
import com.leyou.test.pojo.Spit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-29 13:05
 * @Feature:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyTestApplication.class)
public class MongoTest {

    @Autowired
    private MongoRepository<Spit,String> spitDao;

    @Test
    public void test1(){
        List<Spit> list = spitDao.findAll();
        for (Spit spit : list){
            System.out.println(spit);
        }

    }
}
