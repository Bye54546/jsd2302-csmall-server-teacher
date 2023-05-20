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



