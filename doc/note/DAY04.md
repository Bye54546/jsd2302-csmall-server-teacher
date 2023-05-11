# Spring MVC框架的统一处理异常机制

Spring MVC提供了统一处理异常的机制，使得在同一个项目中，每种类型的异常，只需要有1段处理此异常的代码即可！

在统一处理异常时，需要自定义处理异常的方法，关于此方法：

- 注解：必须添加`@ExceptionHandler`注解
- 访问权限：应该是`public`权限
- 返回值类型：参考处理请求的方法
- 方法名称：自定义，参考处理请求的方法
- 参数列表：必须包含1个异常类型的参数，表示此方法需要处理的异常，并且，不可以像处理请求的方法那样随意添加参数，但也可以按需添加特定的几种类型的参数，例如`HttpServletRequest`、`HttpServletResponse`等

例如：

```java
@ExceptionHandler
public String handleServiceException(ServiceException e) {
    log.warn("程序运行过程中出现了ServiceException，将统一处理！");
    log.warn("异常信息：{}", e.getMessage());
    return e.getMessage();
}
```











