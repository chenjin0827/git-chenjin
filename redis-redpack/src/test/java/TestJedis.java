import redis.clients.jedis.JedisPoolConfig;

public class TestJedis {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
    }
}
