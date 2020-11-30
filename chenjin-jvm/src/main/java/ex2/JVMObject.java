package ex2;

/**
 * @author King老师
 * VM参数
 * -Xms30m -Xmx30m  -XX:MaxMetaspaceSize=30m  -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 *
 *
 */
public class JVMObject {
    public final static String MAN_TYPE = "man"; // 常量
    public static String WOMAN_TYPE = "woman";  // 静态变量
    public static void  main(String[] args)throws Exception {
        Teacher T1 = new Teacher();
        T1.setName("TOM");
        T1.setSexType(MAN_TYPE);
        T1.setAge(36);
        for(int i =0 ;i<15 ;i++){
            System.gc();//主动触发GC 垃圾回收 15次--- T1存活
        }
        Teacher T2 = new Teacher();
        T2.setName("TOM");
        T2.setSexType(MAN_TYPE);
        T2.setAge(18);
        Teacher tom = new Teacher("TOM", MAN_TYPE, 18);
        System.out.println(tom.getAge());
        Teacher tom2 = new Teacher("TOM", MAN_TYPE, 22);
        System.out.println(tom2.getAge());
        Thread.sleep(Integer.MAX_VALUE);//线程休眠
    }
}

class Teacher{
    String name;
    String sexType;
    int age;

    public Teacher() {
    }

    public Teacher(String name, String sexType, int age) {
        this.name = name;
        this.sexType = sexType;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
