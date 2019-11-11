Java动态代理分析
代理是一种常用的设计模式，其目的就是为其他对象提供一个代理以控制对某个对象的访问。
代理类负责为委托类预处理消息、过滤消息并转发消息，以及进行消息被委托类执行后的后续处理。

1. java.lang.reflect.Proxy：这是 Java 动态代理机制的主类，它提供了一组静态方法来为一组接口动态地生成代理类及其对象
2. java.lang.reflect.InvocationHandler：这是调用处理器接口，它自定义了一个invoke方法，用于几种处理在动态代理类对象上的方法调用。通常在该方法中实现对委托类的代理访问。
3. java.lang.ClassLoader：Proxy 静态方法生成动态代理类同样需要通过类装载器来进行装载才能使用，它与普通类的唯一区别就是其字节码是由 JVM 在运行时动态生成的而非预存在于任何一个.class 文件中。


首先让我们来了解一下如何使用 Java 动态代理。具体有如下四步骤：
1. 通过实现 InvocationHandler 接口创建自己的调用处理器；
2. 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
3. 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
4. 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。


// InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
// 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
InvocationHandler handler = new InvocationHandlerImpl(..); 

// 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
Class clazz = Proxy.getProxyClass(classLoader, new Class[] { Interface.class, ... }); 

// 通过反射从生成的类对象获得构造函数对象
Constructor constructor = clazz.getConstructor(new Class[] { InvocationHandler.class }); 

// 通过构造函数对象创建动态代理类实例
Interface Proxy = (Interface)constructor.newInstance(new Object[] { handler });

实际使用过程更加简单，因为 Proxy 的静态方法 newProxyInstance 已经为我们封装了步骤 2 到步骤 4 的过程，所以简化后的过程如下

// InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
InvocationHandler handler = new InvocationHandlerImpl(..); 

// 通过 Proxy 直接创建动态代理类实例
Interface proxy = (Interface)Proxy.newProxyInstance( classLoader, 
     new Class[] { Interface.class }, 
     handler );
     
     