package com.chenjin.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("udp 发送数据");
        DatagramSocket ds = new DatagramSocket();
        String str = "客户端发送数据....";
        byte[] strByte = str.getBytes();
        DatagramPacket dp = new DatagramPacket(strByte, strByte.length, InetAddress.getByName("192.168.1.3"), 8080);
        ds.send(dp);
        ds.close();
    }

}
