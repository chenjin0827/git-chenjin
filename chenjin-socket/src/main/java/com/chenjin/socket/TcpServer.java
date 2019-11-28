package com.chenjin.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("socket服务器端启动....");
        ServerSocket serverSocket = new ServerSocket(8080);
        //获取客户端对象
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] buf= new byte[1024];
        int len=inputStream.read(buf);
        String str =new String(buf,0,len);
        System.out.println("str:"+str);
        serverSocket.close();
    }

}
