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

如果将以上方法定义在某个Controller中，它将只能作用于那个Controller中处理请求的方法抛出的异常，不能处理别的Controller中的异常！可以将以上方法定义在专门的类中，并在类上添加`@RestControllerAdvice`注解，添加此注解后，此类中所有特定的方法（例如添加`@ExceptionHandler`的方法）可以作用于当前项目中每次处理请求的过程中！

例如，创建`GlobalExceptionHandler`类，在类上添加`@RestControllerAdvice`注解，并将以上处理异常的方法添加在此类中：

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleServiceException(ServiceException e) {
        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        return e.getMessage();
    }

}
```

在以上类中，可以存在多个处理异常的方法，用于处理不同种类的异常！

允许同时存在处理了父子级异常的方法，例如某个方法处理`NullPointerException`，另一个方法处理`RuntimeException`，后续，当出现`NullPointerException`时，虽然匹配此异常的处理方法有2个，但是，会执行类型最接近此异常的方法，也就是由处理`NullPointerException`异常的方法进行处理，而不会由处理`RuntimeException`异常的方法进行处理！

建议在每个项目的全局异常处理器中，都添加处理`Throwable`异常的方法，避免此项目向客户端反馈`500`错误，例如：

```java
// 【注意】在项目正式上线时，禁止使用 e.printStackTrace();
@ExceptionHandler
public String handleThrowable(Throwable e) {
    log.warn("程序运行过程中出现了Throwable，将统一处理！");
    log.warn("异常：", e); // 取代 e.printStackTrace();，效果相同，注意，第1个参数中不要使用 {} 进行占位
    String message = "服务器忙，请稍后再试！【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在全局异常处理器中补充处理对应异常的方法】";
    return message;
}
```

# Spring Validation框架

## 关于Spring Validation

Spring Validation框架的主要作用是检查方法的参数的基本有效性。

## 添加依赖

此框架的依赖项的`artifactId`为：`spring-boot-starter-validation`。

```xml
<!-- Spring Boot支持Spring Validation用于检查方法参数的基本有效性的依赖项 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
```

## 检查封装的请求参数

需要先在方法的参数（封装的类型）前添加`@Valid`或`@Validated`注解，以表示“将检查此参数的基本有效性”，例如：

```java
@PostMapping("/add-new")
public String addNew(@Valid AlbumAddNewParam albumAddNewParam) {
    albumService.addNew(albumAddNewParam);
    return "添加成功！";
}
```

然后，需要在封装的类型的属性上添加检查注解，以配置对应的检查规则，例如：

```java
@Data
public class AlbumAddNewParam implements Serializable {

    @NotNull
    private String name;
    
}
```

经过以上配置，参数`name`将不允许为`null`值，如果客户端提交的请求中没有`name`的值，将直接响应`400`错误！

## 处理BindException

当检查参数的基本有效性不通过时，在服务器端的控制台会提示错误详情，例如：

```
2023-05-11 14:25:21.058  WARN 45924 --- [nio-8080-exec-1] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors

Field error in object 'albumAddNewParam' on field 'name': rejected value [null]; codes [NotNull.albumAddNewParam.name,NotNull.name,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [albumAddNewParam.name,name]; arguments []; default message [name]]; default message [不能为null]]
```

检查注解还可以配置`message`属性，用于指定检查不通过时的文本信息，例如：

```java
@NotNull(message = "添加相册失败，必须提交相册名称！")
private String name;
```

经过以上配置后，如果检查失败，错误信息大致如下：

```
Field error in object 'albumAddNewParam' on field 'name': rejected value [null]; codes [NotNull.albumAddNewParam.name,NotNull.name,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [albumAddNewParam.name,name]; arguments []; default message [name]]; default message [添加相册失败，必须提交相册名称！]
```

然后，需要在全局异常处理器中添加处理`BindException`请求的方法：

```java
@ExceptionHandler
public String handleBindException(BindException e) {
    log.warn("程序运行过程中出现了BindException，将统一处理！");
    log.warn("异常信息：{}", e.getMessage());
    String message = e.getFieldError().getDefaultMessage();
    return message;
}
```







## 检查注解











