package ex2;

import org.junit.Test;

public class TestString {
    class Student {
        private String name;
        private String city;

        public Student() {

        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }


        public String getCity() {
            return city;
        }


        public Student(String name, String city) {
            this.name = name;
            this.city = city;
        }

    }

    @Test
    public void test01() {
        Student student2 = new Student();
        student2.setName("张三");
        student2.setCity("深圳");

        Student student = new Student("张三", "深圳");
        String name = "张三";
        String city = "深圳";
        String innerName = "张三".intern();
        String innerCity = "深圳".intern();

        System.out.println("name:" + (name == student.getName()));
        System.out.println("city:" + (city == student.getCity()));
        System.out.println("innerName:" + (name == innerName));
        System.out.println("innerCity:" + (city == innerCity));
        System.out.println("------------------------------");
        System.out.println(name==student2.getName());
        System.out.println(city==student2.getCity());

    }
}
