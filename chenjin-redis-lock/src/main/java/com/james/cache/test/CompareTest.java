package com.james.cache.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
/**
 * 未使用pipeline和使用pipeline后，批量删除的性能对比
 * 
 */
public class CompareTest {
	//5000组KEY-VALUE键值对，数组长度为10000
	//String[]{"key:0","v0","key:1","v1","key:2","v2","key:3","v3","key:4","v4"......."key:4999","v:4999"})
	public static int arraylength = 10000;
	public static String ip = "127.0.0.1";
	public static int port = 6379;
	//测试主函数
	public static void main(String[] args) throws Exception {
		//初始化数据
	    mset(arraylength);
	    //生成数组KEY键
	    String[] keys = new String[arraylength/2];
	    for(int i = 0 ; i < arraylength/2 ; i ++){
	    	keys[i] = "key:"+i;
	    }
	    long t = System.currentTimeMillis();
	    del(keys);
	    System.out.println(System.currentTimeMillis()-t);
	    
	    
	    //初始化数据
	    mset(arraylength);

	    long tt = System.currentTimeMillis();
	    mdel(keys);
	    System.out.println(System.currentTimeMillis()-tt);
	    
	    
	    
	}
	/**
	 * 初始化数据，批量设值
	 * redis提供的批量设值mset  批量取值 mget，但没有批量删除mdel指令
	 */
	//String[]{"key:0","v0","key:1","v1","key:2","v2","key:3","v3","key:4","v4"......."key:4999","v:4999"})
	public static String[] mset(int length) {
		Jedis jedis = new Jedis(ip, port);
		String[] str = new String[length]; //10000
		int j = 0;
		for(int i = 0 ; i < str.length/2; i ++ ){
			str[j] = "key:"+i; //str[0]=key:0   str[2]=key:1...
			str[j+1] = "v"+i; //str[1] = v0     str[3]=v1....
			j = j+2;  //j=2
		}
		jedis.mset(str);
		jedis.close();
		
		
		return str;
	}

	/**
	 * 使用pipeline进行批量删除
	 * redis没有批量删除mdel指令，手写一个使用pipeline进行批量删除
	 */
	public static boolean mdel(String... keys) {
		Jedis jedis = new Jedis(ip, port);
		boolean flag = false;
		Pipeline pipe = jedis.pipelined();//提供一个管道
		for(String key:keys){
			pipe.del(key);  //组装指令
		}
		pipe.sync();//统一把所有组装提交到REDIS执行
		jedis.close();
		return flag;
	}

	/**
	 * 使用redis提供的del进行远程多次删除，以达到批量删除的效果
	 */
	public static boolean del(String... keys) {

		Jedis jedis = new Jedis(ip, port);
		boolean flag = false;
		for(String key:keys){
			jedis.del(key);
		}
		
		jedis.close();
		
		
		return flag;
	}
}
