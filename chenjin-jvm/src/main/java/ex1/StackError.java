package ex1;
/**
 * 虚拟机栈这个内存也不是无限大， 它有大小限制， 默认情况下是 1M。
 * 如果我们不断的往虚拟机栈中入栈帧， 但是就是不出栈的话， 那么这个虚拟机栈就会爆掉。
 */
public class StackError {
    public static void main(String[] args) {
        A();
    }
    public static void A(){
        A();
    }
}
