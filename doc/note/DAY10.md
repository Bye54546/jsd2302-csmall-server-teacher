# 基于Spring JDBC框架的事务管理

事务（Transaction）：是数据库中的一种能够保证多个写操作要么全部成功，要么全部失败的机制！

例如以下操作就应该是全部成功或全部失败的，只成功一半，是不可接受的：

```mysql
update 账户表 set 余额=余额-1000 where 帐户名='国斌';
```

```mysql
update 账户表 set 余额=余额+1000 where 帐户名='传奇';
```

在Spring Boot项目中，添加了数据库编程的依赖项后，大多都依赖了`spring-jdbc`框架，例如：

![image-20230519111931728](assets/image-20230519111931728.png)

在添加了`spring-jdbc`依赖的项目中，当需要使得某个方法是“事务性”的，只需要使用`@Transactional`注解即可！







