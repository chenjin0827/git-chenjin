package com.james.cache.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 使用jedis向本地伪redis服务发送请求，用于抓包分析
 */
public class JamesClientPackTest {
	public static int arraylength = 10000;
	public static String ip = "127.0.0.1";
	public static int port = 6379;
	
	 public static void main(String[] args) throws Exception {
	 	try{
			Jedis jedis = new Jedis(ip,port);
			jedis.set("lison", "james");
			jedis.close();
		}catch (Exception e){
			System.out.println("出现异常，用于测试屏蔽掉");
		}

	 }
	 
}
