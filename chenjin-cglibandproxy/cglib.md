CGLIB是一个强大的、高性能的代码生成库。
其被广泛应用于AOP框架（Spring、dynaop）中，用以提供方法拦截操作。
Hibernate作为一个比较受欢迎的ORM框架，同样使用CGLIB来代理单端（多对一和一对一）关联
（延迟提取集合使用的另一种机制）。CGLIB作为一个开源项目，其代码托管在github，地址为：
https://github.com/cglib/cglib


JDK动态代理更加强大，JDK动态代理虽然简单易用，但是其有一个致命缺陷是，只能对接口进行代理