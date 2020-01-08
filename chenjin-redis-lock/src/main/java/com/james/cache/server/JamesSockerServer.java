package com.james.cache.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟Redis伪服务端，用来抓包
 * 
 */
public class JamesSockerServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			Socket receive = serverSocket.accept();
			byte[] result = new byte[1024];
			receive.getInputStream().read(result);
			System.out.println(new String(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
