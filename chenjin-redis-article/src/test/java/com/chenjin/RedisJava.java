package com.chenjin;

import redis.clients.jedis.Jedis;

public class RedisJava {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.100.166", 6379);
        System.out.println("服务正在运行: "+jedis.ping());
    }
}