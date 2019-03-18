import com.springboot.Application;
import com.springboot.component.BlogProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTests {

    @Resource
    private BlogProperties blogProperties;


    @Test
    public void getHello() throws Exception {
//        Assert.assertEquals(blogProperties.getName(), "姓名-陈进");
//        Assert.assertEquals(blogProperties.getWork(), "职业-教师");
        System.out.println(blogProperties.getName());
//        System.out.println(blogProperties.getRadomInt());
    }

}