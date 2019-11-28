package com.chenjin.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 三次握手：
 * 第一次握手：
 * 服务器：客户端可以通讯了吗？
 * 第二次握手：
 * 客户端响应，带上信息告诉服务器可以通讯了
 * 第三次握手：
 * 服务器告诉客户端：那我现在开始发送数据你了
 *
 * 四次分手：
 * 1、客户端发送一个标识fin到服务器，用来关闭数据传送
 * 2、服务器收到这个fin，发回一个ack，
 * 3、服务器关闭与客户端的连接
 * 4、客户端发回报文确认
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("socket服务器端启动....");
        ServerSocket serverSocket = new ServerSocket(8080);
        //等待客户端连接，阻塞状态
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] buf= new byte[1024];
        int len=inputStream.read(buf);
        String str =new String(buf,0,len);
        System.out.println("str:"+str);
        serverSocket.close();
    }

}
