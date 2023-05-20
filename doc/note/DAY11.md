# 基于Spring JDBC框架的事务管理（续）

基于Spring JDBC的事务管理中，默认将根据`RuntimeException`来判断执行过程是否出错，从而回滚，即：

```
开启事务（BEGIN）
try {
	执行业务
	提交（COMMIT）
} catch (RuntimeException e) {
	回滚（ROLLBACK）
}
```

在`@Transactional`注解，还可以配置几个属性，来控制回滚：

- `rollbackFor`：指定根据哪些异常回滚，取值为异常类型的数组，例如：`@Transactional(rollbackFor = {NullPointerException.class, NumberFormatException.class})`
- `rollbackForClassName`：指定根据哪些异常回滚，取值为异常类型的全限定名的字符串数组，例如：`@Transactional(rollbackForClassName = {"java.lang.NullPointerException", "java.lang.NumberFormatException"})`
- `noRollbackFor`：指定对于哪些异常不执行回滚，取值为异常类型的数组
- `noRollbackForClassName`：指定对于哪些异常不执行回滚，取值为异常类型的全限定名的字符串数组

注意：无论怎么配置，`@Transactional`只会对`RuntimeException`或其子孙类异常进行回滚！

**小结：**

- 当某个业务涉及多次“写”操作（例如2次INSERT操作，或1次INSERT操作加1次UPDATE操作，等等）时，必须保证此业务方法是事务性的，理论上，应该按需在业务的抽象方法上添加`@Transactional`注解，在初学时，更建议将此注解添加在接口上
  - 另外，对于同一个业务中的多次查询，使用`@Transactional`使其是事务性的，还可以配置使得此业务的多个查询共用同一个数据库连接，则查询效率可以提升

- 在执行增、删、改这类“写”操作后，应该及时获取“受影响的行数”，并且，判断此值是否符合预期，如果不符合，就应该抛出`RuntimeException`或其子孙类异常，使得事务回滚

# Spring Security框架

## 关于Spring Security框架

Spring Security框架主要解决了认证与授权相关的问题。

认证信息（Authentication）：表示用户的身份信息

认证（Authenticate）：识别用户的身份信息的行为，例如：登录

授权（Authorize）：授予用户权限，使之可以进行某些访问，反之，如果用户没有得到必要的授权，将无法进行访问

## Spring Security框架的依赖项

在Spring Boot中使用Spring Security时需要添加`spring-boot-starter-security`依赖。

## Spring Security框架的典型特征

当添加了`spring-boot-starter-security`依赖后，在启动项目时执行一些自动配置，具体表现有：

- 所有请求（包括根本不存在的）都是必须要登录才允许访问的，如果未登录，会自动跳转到框架自带的登录页面
- 默认的用户名是`user`，密码是在启动项目是控制台提示的一段UUID值，每次启动项目时都不同
  - UUID是通过**128位算法**（运算结果是128个bit）运算得到的，是一个随机数，在同一时空是唯一的，通常使用32个十六进制数来表示，每种平台生成UUID的API和表现可能不同，UUID值的种类有2的128次方个，即：3.4028237e+38，也就是340282366920938463463374607431768211456

- 当登录成功后，会自动跳转到此前尝试访问的URL
- 当登录成功后，可以通过 `/logout` 退出登录
- 默认不接受普通`POST`请求，如果提交`POST`请求，将响应`403（Forbidden）`
  - 具体原因参考后续的`CSRF`相关内容





