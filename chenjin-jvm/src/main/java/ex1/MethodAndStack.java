package ex1;

/**
 * 起一个 main 方法， 在 main 方法运行中调用 A 方法， A 方法中调用 B 方法， B 方法中运行 C 方法。
 我们把代码跑起来， 线程 1 来运行这段代码， 线程 1 跑起来， 就会有一个对应 的虚拟机栈， 同时在执行每个方法的时候都会打包成一个栈帧。
 比如 main 开始运行， 打包一个栈帧送入到虚拟机栈
 C 方法运行完了， C 方法出栈， 接着 B 方法运行完了， B 方法出栈、 接着 A 方法运行完了， A 方法出栈， 最后 main 方法运行完了， main 方法这个栈帧就
 出栈了。
 一个方法对应一个栈帧
 */
public class MethodAndStack {
    public static void main(String[] args) {
        A();
    }
    public static void A(){
        B();
    }
    public static void B(){
        C();
    }
    public static void C(){

    }
}
