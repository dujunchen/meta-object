# meta-object
最近在深入学习Mybatis过程中，无意中发现其中内置的MetaObject挺好用的，所以将其抽离了出来，大家有兴趣也可以玩一玩。这玩意就是通过将Java对象封装，然后可以通过类似OGNL表达式操作属性的API，在Mybatis主要是用来解析属性表达式的。支持普通的JavaBean、Collection和Map。我在src/test下面也放了一些简单的单元测试。

- 安装使用方法

  很简单，将该工程git clone到本地，然后执行maven的打包命令打成jar就可以使用了。

  ```shell
  mvn clean install
  ```
  
  然后引入自己工程后就可以使用了。
  
- 操作

  假设A类中有一个`Map<String, B>`类型的属性m，m中有一个键值对，键为K，值为B类型，B类型中有一个C类型的属性c，C类型中有一个`List<String>`的属性langs，那么我们可以使用`SystemMetaObject.forObject(A的实例a)`包装该实例，然后通过`metaObject.getValue("m[K].c.langs[2]")`获取该langs中索引为2的值，通过`mo.setValue("m[K].c.langs[2]", "xxx")`将该langs中索引为2的值修改为xxx。

  示例代码如下：

  ```java
  public class A {
      Map<String, B> m;
  }
  
  class B {
      C c;
  }
  
  class C {
      List<String> langs;
  }
  
  C c = new C();
  List<String> langs = new ArrayList<>();
  langs.add("java");
  langs.add("python");
  langs.add("javascript");
  c.langs = langs;
  B b = new B();
  b.c = c;
  Map<String, B> m = new HashMap<>();
  m.put("K", b);
  A a = new A();
  a.m = m;
  System.out.println(a);
  MetaObject mo = SystemMetaObject.forObject(a);
  System.out.println(mo.getValue("m[K].c.langs[2]"));
  mo.setValue("m[K].c.langs[2]", "c++");
  System.out.println(a);
  
  //输出结果：
  A(m={K=B(c=C(langs=[java, python, javascript]))})
  javascript
  A(m={K=B(c=C(langs=[java, python, c++]))})
  ```

  

- 注意事项
  - 目前Mybatis中的MetaObject貌似List和Map不能相互嵌套。后续可以考虑功能进行一定的扩展。
  - 未进行过性能测试。