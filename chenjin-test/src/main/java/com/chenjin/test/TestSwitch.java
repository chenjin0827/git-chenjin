package com.chenjin.test;

public class TestSwitch {

    public static void main(String[] args) {
        String nationalGPOPurchase ="1002";
        switch (nationalGPOPurchase) {
            case "1001": {
                System.out.println(1001);
                break;
            }
            case "1004": {
                System.out.println(1004);
                break;
            }
            case "1005": {
                System.out.println(1005);
                break;
            }
            case "1003":
            case "1002":
            default: {
                System.out.println("默认");
                break;
            }
        }

    }
}
