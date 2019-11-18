    1、Subject： 代表当前正在执行操作的用户，但Subject代表的可以是人，也可以是任何第三方系统帐号。
当然每个subject实例都会被绑定到SercurityManger上。
    2、SecurityManger:SecurityManager是Shiro核心，主要协调Shiro内部的各种安全组件，
这个我们不需要太关注，只需要知道可以设置自定的Realm。
    3、Realm:用户数据和Shiro数据交互的桥梁。比如需要用户身份认证、权限认证。
都是需要通过Realm来读取数据。