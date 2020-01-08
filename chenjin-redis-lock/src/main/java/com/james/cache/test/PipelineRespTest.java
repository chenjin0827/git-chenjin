package com.james.cache.test;

import java.io.IOException;
import java.net.Socket;

import com.james.cache.jedis.JamesPipeline;

import redis.clients.jedis.Jedis;
/**
 * 测试手写的JamesPipeline，实现批量删除
 */
public class PipelineRespTest {
	public static int arraylength = 100;
	public static String ip = "127.0.0.1";
	public static int port = 6379;
	/**
	 * 初始化数据，批量设值
	 * redis提供的批量设值mset  批量取值 mget，但没有批量删除mdel指令
	 */
	//String[]{"key:0","v0","key:1","v1","key:2","v2","key:3","v3","key:4","v4"})
	public static String[] mset(int length) {
		Jedis jedis = new Jedis(ip, port);
		String[] str = new String[length];
		int j = 0;
		for (int i = 0; i < length / 2; i++) {
			str[j] = "key:" + i;
			str[j + 1] = "v" + i;
			j = j + 2;
		}
		try{
			jedis.mset(str);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}

	/**
	 * 测试手写的JamesPipeline，实现批量删除
	 * redis提供的批量设值mset  批量取值 mget，但没有批量删除mdel指令
	 */
	public static void mdel(Socket socket,String... keys) throws IOException {	 
		JamesPipeline pipe = new JamesPipeline(socket);
		try {
			for(String key:keys){
			    pipe.mdel(key);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			socket.close();
		}
	}

	 public static void main(String[] args) throws Exception {
		//批量设值，组装KEYS
		String[] keys = new String[arraylength / 2];
		for (int i = 0; i < arraylength / 2; i++) {
			keys[i] = "key:" + i;
		}
		//重新设值
        mset(arraylength);
		//定义socket连接
		Socket socket = new Socket(ip, port);	
		//使用自定义pipeline批量删除
		mdel(socket,keys);
	 } 
}
