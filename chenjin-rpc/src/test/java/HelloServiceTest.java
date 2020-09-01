import com.chenjin.rpc.HelloService;
import com.chenjin.rpc.client.RpcProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试时先手动创建好zk需要的节点
 * 利用zkCli   create /registry/data ""
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class HelloServiceTest {

    @Autowired
    private RpcProxy rpcProxy;

    @Test
    public void helloTest() {
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.sayHello("World");
        System.out.println("result======"+result);
        Assert.assertEquals("Hello! World", result);
    }
}

