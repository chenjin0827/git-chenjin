package com.james.cache.test;

import java.net.Socket;
/**
 * 手写Jedis常用get set指令
 */
public class RespSetGetTest {
	public static String ip = "127.0.0.1";
	public static int port = 6379;
	/**
	 * 使用RESP协议实现从redis取值
	 */
	public static String get(Socket socket,String key) throws Exception {

		StringBuffer str = new StringBuffer();
		str.append("*2").append("\r\n");
		str.append("$3").append("\r\n");
		str.append("get").append("\r\n");
		str.append("$").append(key.getBytes().length).append("\r\n");
		str.append(key).append("\r\n");
		
		socket.getOutputStream().write(str.toString().getBytes());
		byte[] response = new byte[2048];
		socket.getInputStream().read(response);
		return new String(response);
	}
	/**
	 * 使用RESP协议实现从redis设值
	 * set key value
	 */
	public static String set(Socket socket,String key, String value) throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("*3").append("\r\n");
		str.append("$3").append("\r\n");
		str.append("set").append("\r\n");
		str.append("$").append(key.getBytes().length).append("\r\n");
		str.append(key).append("\r\n");
		str.append("$").append(value.getBytes().length).append("\r\n");
		str.append(value).append("\r\n");
		socket.getOutputStream().write(str.toString().getBytes());
		byte[] response = new byte[2048];
		socket.getInputStream().read(response);
		return new String(response);
	}
	public static void main(String args[]) throws Exception{
		Socket socket = new Socket(ip,port);
		set(socket, "name", "james");
		String res = get(socket,"name");
		System.out.println("the return is = "+res);
	}
}
