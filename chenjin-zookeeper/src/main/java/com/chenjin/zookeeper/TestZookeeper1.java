package com.chenjin.zookeeper;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

/**
 * onepay:onepay 加密后 onepay:T+17ezPAW0kDvN6elPD5Tdzdm00=
 * zookeeper:zookeeper 加密后  zookeeper:4lvlzsipXVaEhXMd+2qMrLc0at8=
 * 密码使用样例
 * setAcl /dubbo digest:onepay:T+17ezPAW0kDvN6elPD5Tdzdm00=:cdrwa
 *
 * setAcl /dubbo digest:zookeeper:4lvlzsipXVaEhXMd+2qMrLc0at8=:cdrwa
 */
public class TestZookeeper1 {
    public static void main(String[] args) {
        try {
            /**
             * 注意是对username：password这样的传入进行加密，不要只传过去一个密码，这样是不行的
             */
            String s = DigestAuthenticationProvider.generateDigest("onepay:onepay");
            System.out.println(s);
            String s1 = DigestAuthenticationProvider.generateDigest("zookeeper:zookeeper");
            System.out.println(s1);
        } catch (NoSuchAlgorithmException e) {

        }
    }

}
