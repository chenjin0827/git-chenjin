package ex3;
/**
 * @author  King老师
 * VM Args：-XX:+PrintGC
 * 判断对象存活
 */
public class Isalive {
    public Object instance =null;
    //占据内存，便于判断分析GC
    private byte[] bigSize = new byte[10*1024*1024];

    public static void main(String[] args) {
        Isalive objectA = new Isalive();//objectA 局部变量表 GCRoots
        Isalive objectB = new Isalive();//objectB 局部变量表
        //相互引用
        objectA.instance = objectB; //强引用
        objectB.instance = objectA;
        //切断可达 虽然objectA和objectB置为null了，但是new Isalive还是存在的，并且是在相互引用，使用引用计数法则无法完成回收
        objectA =null;//objectA置为null，到 new Isalive的引用没有了，变成了根不可达GCRoots
        objectB =null;
        //强制垃圾回收
        System.gc();
    }
}
