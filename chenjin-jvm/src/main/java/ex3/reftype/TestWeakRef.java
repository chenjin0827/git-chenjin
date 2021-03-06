package ex3.reftype;

import java.lang.ref.WeakReference;

/**
 * @author King老师
 * 强引用  不会被会后
 * 软引用 发生OOM才会回收软引用的部分
 * 弱引用 只要发生GC，就会被回收
 * 另一个有一个虚引用 PhantomReference，随时都可能会被回收
 */
public class TestWeakRef {
	public static class User{
		public int id = 0;
		public String name = "";
		public User(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}

	}

	public static void main(String[] args) {
		User u = new User(1,"King");
		WeakReference<User> userWeak = new WeakReference<User>(u);
		u = null;//干掉强引用，确保这个实例只有userWeak的弱引用
		System.out.println(userWeak.get());
		System.gc();//进行一次GC垃圾回收,千万不要写在业务代码中。
		System.out.println("After gc");
		System.out.println(userWeak.get());
	}
}
