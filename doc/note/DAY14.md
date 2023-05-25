## 处理解析JWT时的异常

由于解析JWT是在过滤器组件中执行的，而过滤器是最早处理请求的组件，此时，控制器（Controller）还没有开始处理这次的请求，则全局异常处理器也无法处理解析JWT时出现的异常（全局异常处理器只能处理控制器抛出的异常）！

首先，在`ServiceCode`中补充新的状态码：

```java
ERR_JWT_EXPIRED(60000),
ERR_JWT_MALFORMED(60100),
ERR_JWT_SIGNATURE(60200),
```

然后，在`JwtAuthorizationFilter`中，使用`try...catch`包裹尝试解析JWT的代码：

```java
// 尝试解析JWT
response.setContentType("application/json; charset=utf-8");
Claims claims = null;
try {
    claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
} catch (MalformedJwtException e) {
    String message = "非法访问！";
    log.warn("程序运行过程中出现了MalformedJwtException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_MALFORMED, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (SignatureException e) {
    String message = "非法访问！";
    log.warn("程序运行过程中出现了SignatureException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (ExpiredJwtException e) {
    String message = "您的登录信息已经过期，请重新登录！";
    log.warn("程序运行过程中出现了ExpiredJwtException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (Throwable e) {
    String message = "服务器忙，请稍后再试！【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在解析JWT的过滤器中补充处理对应异常的代码块】";
    log.warn("程序运行过程中出现了Throwable，将向客户端响应错误信息！");
    log.warn("异常：", e);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNKNOWN, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
}
```

# 处理复杂请求的跨域问题

当客户端提交请求时，在请求头中配置了特定的属性（例如`Authorization`），则这个请求会被视为“复杂请求”。

对于复杂请求，浏览器会先对服务器端发送`OPTIONS`类型的请求，以执行预检（PreFlight），如果预检通过，才会执行本应该发送的请求。

在Spring Security的配置类中，可以在配置对请求授权时，将所有`OPTIONS`类型的请求全部直接许可，例如：

![image-20230525115407759](assets/image-20230525115407759.png)

或者，调用参数对象的`cors()`方法也可以，例如：

![image-20230525115515416](assets/image-20230525115515416.png)

提示：对于复杂请求的预检，是浏览器的行为，并且，当某个请求通过预检后，浏览器会缓存此结果，后续再次发出此请求时，不会再次执行预检。

# 单点登录

**SSO**（**S**ingle **S**ign **O**n）：单点登录，表示在集群或分布式系统中，客户端只需要在某1个服务器上完成登录的验证，后续，无论访问哪个服务器，都不需要再次重新登录！常见的实现手段主要有：共享Session，使用Token。

目前，如果希望客户端在`csmall-passport`中登录后，在`csmall-product`中也能够被识别身份、权限，需要：

- 复制依赖项：`spring-boot-starter-security`、`jjwt`、`fastjson`
- 复制`LoginPrincipal`
- 复制`ServiceCode`，覆盖此前的文件
- 复制`application-dev.yml`中的自定义的配置
- 复制`JwtAuthorizationFilter`
- 复制`SecurityConfiguration`
  - 删除`PasswordEncoder`的`@Bean`方法
  - 删除`AuthenticationManager`的`@Bean`方法
  - 删除“白名单”中管理员登录的URL地址

完成后，在`csmall-product`项目中，也可以通过`@AuthenticationPrincipal`来注入当事人数据，也可以使用`@PreAuthorize`来配置访问权限。

# Spring框架

## 关于Spring框架

Spring框架主要解决了创建对象、管理对象的相关问题。

Spring框架的核心有：Spring IOC、Spring AOP。

## Spring框架创建对象

所有被Spring创建出来的对象都可以称之为：Spring Bean。

所有Spring Bean都会存在于Spring的`ApplicationContext`中，由于Spring框架会维护这个`ApplicationContext`容器，所以，Spring框架也通常被称之为“Spring容器”。

所有Spring Bean都可以被自动装配。

## Spring框架创建对象的方式之一--组件扫描

需要在配置类上添加`@ComponentScan`注解，以开启组件扫描，则Spring框架会自动扫描添加此注解的类所在的包及其子孙包，检查其中是否包含“组件类”，如果存在，则自动创建出这些“组件类”的对象！

在Spring Boot项目中，默认就开启了组件扫描，因为Spring Boot项目中的启动类上添加了`@SpringBootApplication`注解，此注解包含了`@ComponentScan`。

提示：可以在`@ComponentScan`注解上配置参数，以显式的指定扫描哪些包，例如：`@ComponentScan({"cn.tedu.csmall.product.controller", "cn.tedu.csmall.product.service"})`

所有被添加了`@Component`注解的类，都是“组件类”，反之，则不是！

在Spring框架中，还有一些基于`@Component`注解的组合注解，添加这些注解，也能将类标记为“组件类”，包括：

- `@Repository`：添加在存储库类（用于访问数据的类）上
- `@Service`：添加在业务类上
- `@Controller`：添加在控制器类上
- `@Configuration`

以上注解，除了`@Configuration`以外，各组件注解的用法、功能是完全相同的，只是语义不同！

在Spring MVC框架中，也有一些基于`@Component`的组件注解，包括：

- `@RestController`
- `@ControllerAdvice`
- `@RestControllerAdvice`

## Spring框架创建对象的方式之二--@Bean方法

在任何配置类中，可以自定义方法，并在方法上添加`@Bean`注解，则Spring框架会自动调用此方法，并获取此方法返回的对象，将此方法放在Spring容器中。

## Spring框架创建对象的方式的选取

对于自定义的类，建议优先使用组件扫描的做法；对于非定义的类，**只能**使用`@Bean`方法的做法！

## Spring Bean的名称

当使用组件扫描的方式来创建Spring Bean时，默认情况下，如果类名的第1个字母是大写且第2个字母是小写的，Spring Bean的名称就是将类名的首字母改为小写的名称，如果不满足此条件，则Spring Bean的名称就是类名，例如：`AdminController`类的Spring Bean的名称默认是`adminController`，`ABController`类的Spring Bean的名称默认是`ABController`。

可以通过组件注解的参数来指定Spring Bean的名称，例如：

```java
@Controller("controller")
public class AdminController {}
```

所有的组件注解都可以使用以上方式来指定Spring Bean的名称。

当使用`@Bean`方法的方式来创建Spring Bean时，默认情况下，Spring Bean的名称就是方法的名称，也可以配置`@Bean`注解的`value`属性来指定名称。

## Spring Bean的作用域

默认情况下，Spring Bean都是“单例”的，可以通过`@Scope("prototype")`将其配置为“非单例”的。

> 如果使用组件扫描的方式来创建对象，则在类上添加以上注解；
>
> 如果使用`@Bean`方法的方式来创建对象，则在方法上添加以上注解。

默认情况下，单例的Spring Bean是“预加载”的（相当于单例模式中的饿汉式模式），可以通过`@Lazy`将其配置为“懒加载”的（相当于单例模式中的懒汉式模式）。

> 如果使用组件扫描的方式来创建对象，则在类上添加以上注解；
>
> 如果使用`@Bean`方法的方式来创建对象，则在方法上添加以上注解。

**注意：**Spring管理的对象的表现可能是单例的（根据你是否修改了默认的配置），但并没有使用单例模式来实现，所以，不要将其与单例模式划等号。

**错误的说法：**Spring使用单例模式来管理对象的作用域

**错误的说法：**Spring就是单例模式的

## Spring Bean的生命周期

Spring框架允许你在组件类中自定义初始化方法和销毁方法，这2个方法应该是：

- 公有的访问权限
- 使用`void`作为返回值类型
- 方法的名称是自定义的
- 参数列表为空

在初始化方法上添加`@PostConstruct`注解，则此方法会在构造方法之后自动被调用；在销毁方法上添加`@PreDestroy`注解，则此方法会在对象被销毁之前的一刻自动被调用。

如果使用`@Bean`方法的方式来创建对象，则在`@Bean`注解中通过`initMethod`属性来配置初始化方法的名称，通过`destroyMethod`属性来配置销毁方法的名称。

## Spring的自动装配

Spring的自动装配表现为：当某个属性或被Spring自动调用的方法的参数需要值时，Spring框架可以自动的从容器中找到合适的值。

典型表现为：

```java
@RestController
public class AdminController {
    @Autowired // 自动装配
    private IAdminService adminService; // AdminServiceImpl类的对象
}
```

关于合适的值：类型匹配的Spring Bean，或当存在多个类型匹配的Spring Bean时，也要考虑Spring Bean的名称。

例如：需要装配`private IAdminService adminService;`属性，并且，`IAdminService`有2个实现类都是被Spring管理对象的，默认的Spring Bean名称可能是`adminServiceImpl1`和`adminServiceImpl2`，则名称也无法匹配，在加载Spring时，就会直接报`NoUniqueBeanDefinitionException`错误，此时，可以选择：

- 修改属性名，使之与某个Spring Bean名称相同，例如`private IAdminService adminServiceImpl1;`
- 修改某个Spring Bean名称，使之与属性名相同，例如`@Serivce("adminService")`
- 在属性上补充`@Qualifier`注解，指定Spring Bean的名称，例如`@Qualifier("adminSesrviceImpl1")`













---------------------------------------------------------------------------------

```
jhdSfkkjKJ3831HdsDkdfSA9KSIjklJD749Fhsa34fMOdsKf08dfjFhkdfs
```

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NSwiYXV0aG9yaXRpZXNKc29uU3RyaW5nIjoiW3tcImF1dGhvcml0eVwiOlwiL3Btcy9hbGJ1bS9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9hbGJ1bS9kZWxldGVcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2FsYnVtL3JlYWRcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2FsYnVtL3VwZGF0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcGljdHVyZS9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9waWN0dXJlL2RlbGV0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcGljdHVyZS9yZWFkXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9waWN0dXJlL3VwZGF0ZVwifV0iLCJleHAiOjE2ODc1ODcwNTQsInVzZXJuYW1lIjoiZmFuY2h1YW5xaSJ9.AJN1gGhtdmMsnXhWlx0cXSf_VuVS3L4OUgDsbF54vOU
```















