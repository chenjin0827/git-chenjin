package com.chenjin.rpc.demo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 消费端在网络请求的时候肯定会将类、方法、参数类型、
 * 参数值这些传过来的，在服务提供端可以通过反射机制直接执行实现类，然后返回结果。
 */
public class Provider {
    public static void main(String[] args) {
        try {
            //1.Socket绑定本地端口
            ServerSocket serverSocket = new ServerSocket(8888);
            //2.监听端口
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("监听到参数");
                //1.接收所有的参数
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String apiClassName = inputStream.readUTF();
                System.out.println("apiClassName====="+apiClassName);
                String methodName = inputStream.readUTF();
                System.out.println("methodName======="+methodName);
                Class[] paramTypes = (Class[]) inputStream.readObject();
                System.out.println("paramTypes======="+paramTypes);
                Object[] args4Method = (Object[]) inputStream.readObject();
                System.out.println("args4Method======="+args4Method);
                Class clazz = null;
                //2.服务注册，找到具体的实现类
                if (apiClassName.equals(IUserService.class.getName())){
                    clazz = UserServiceImpl.class;
                }
                //3.执行UserServiceImpl的方法
                Method method = clazz.getMethod(methodName,paramTypes);
                Object invoke = method.invoke(clazz.newInstance(),args4Method);
                System.out.println("invoke===="+invoke);
                //4.返回结果给客户端
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(invoke);
                outputStream.flush();

                //5.关闭连接
                outputStream.close();
                inputStream.close();

                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
