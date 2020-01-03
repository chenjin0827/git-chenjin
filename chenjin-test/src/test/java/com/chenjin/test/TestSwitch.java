package com.chenjin.test;

import java.util.Scanner;

public class TestSwitch {
    public static void main(String[] args) {
        while(true){
            System.out.println("请输入数字：");
            Scanner scanner = new Scanner(System.in);
            String nationalGPOPurchase = scanner.next();
            if(nationalGPOPurchase.equals("88")) break;//检测到88 用于退出循环
            nationalGPOPurchase=nationalGPOPurchase==null?"0":nationalGPOPurchase.equals("1001")?"1":
                    nationalGPOPurchase.equals("1004")?"4":nationalGPOPurchase.equals("1005")?"5":"0";
            System.out.println("返回数字===="+nationalGPOPurchase);
        }
    }
}
