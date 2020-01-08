package com.james.cache.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 使用jedis向本地伪redis服务发送请求，用于抓包分析
 */
public class JamesClientPackTest {
	public static int arraylength = 10000;
	public static String ip = "127.0.0.1";
	public static int port = 8888;
	
	 public static void main(String[] args) throws Exception { 
		 
		 
	      Jedis jedis = new Jedis(ip,port);
	      jedis.set("lison", "james");
	      jedis.close();
	 }
	 
}
