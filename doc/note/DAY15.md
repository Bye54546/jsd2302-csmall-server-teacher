# Spring框架

## 关于为属性注入值的做法

当组件类的属性需要值时，可以有3种做法：

- **字段注入**：在属性上添加`@Autowired` / `@Resource`注解，例如：

  ```java
  @RestController
  public class AdminController {
      
      @Autowired
      private IAdminService adminService;
      
  }
  ```

- **Setter注入**：通过Setter方法为属性赋值，此Setter方法需要添加`@Autowired`注解，例如：

  ```java
  @RestController
  public class AdminController {
      
      private IAdminService adminService;
      
      @Autowired
      public void setAdminService(IAdminService adminService) {
          this.adminService = adminService;
      }
      
  }
  ```

- **构造方法注入**：通过构造方法为属性赋值，例如：

  ```java
  @RestController
  public class AdminController {
      
      private IAdminService adminService;
      
      public AdminController(IAdminService adminService) {
          this.adminService = adminService;
      }
      
  }
  ```

**从理论上分析**：通过构造方法注入是最安全的做法，而字段注入是最不推荐的做法，所以，在IntelliJ IDEA中，使用字段注入时会有相关提示！

但是，在开发实践中，类中需要自动装配的属性的数量可能随着需求而增减，而构造方法也需要跟随调整，甚至需要装配的属性的数量会非常多，而使用参数非常多的构造方法也是不太合理的设计。

关于Spring调用构造方法：

- 如果类中没有显式的添加构造方法，Spring会自动调用默认的构造方法
- 如果类中显式的声明了1个构造方法，无论是否有参数，Spring都会自动尝试调用它
- 如果类中有多个构造方法，在默认情况下，Spring会自动调用无参数的构造方法（如果存在的话），如果你希望Spring调用特定的构造方法，则需要在构造方法上添加`@Autowired`注解

## 关于IoC与DI

**IoC**（**I**nversion **o**f **C**ontrol）：控制反转，表现为“将对象的控制权交给了Spring框架”

**DI**（**D**ependency **I**njection）：依赖注入，表现为“为依赖项注入值”

Spring框架负责创建对象、管理对象都是Spring IoC的表现。

Spring框架通过DI完善了IoC，所以，DI是一种实现手段，IoC是需要实现的目标。

## 关于Spring AOP

AOP：面向切面的编程

注意：AOP源自AspectJ，并不是Spring特有的技术，只是Spring很好的支持了AOP

AOP主要解决了“横切关注”的问题，具体为“若干个不同的方法均需要执行相同的任务”的问题！

使用AOP的常用场景有：安全检查、异常处理、事务管理等。

假设存在需求：统计各个Service中的各方法的执行耗时。

在Spring Boot项目中，当需要使用AOP时，需要添加`spring-boot-starter-aop`依赖项：

```xml
<!-- Spring Boot支持Spring AOP的依赖项，主要解决横切关注问题 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
```

在项目的根包下创建`aop.TimerAspect`切面类，在类上添加`@Aspect`和`@Component`注解，在类中编写切面方法：

```java
package cn.tedu.csmall.product.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    // 连接点（JoinPoint）：程序执行过程中的某个节点，可能是某个方法的调用，或抛了某个异常
    // 切入点（PointCut）：选择1个或多个连接点的表达式
    // ------------------------------------------------------------------------
    // 通知（Advice）注解
    // @Around：环绕，包裹了连接点，你的代码可以在连接点之前和之后执行
    // @Before：只能在连接点之前执行
    // @After：只能在连接点之后执行
    // @AfterReturning：只能在成功的执行了连接点之后执行
    // @AfterThrowing：只能在连接点方法抛出异常之后执行
    // 以上各Advice执行情况大致是：
    // @Around开始
    // try {
    //     @Before
    //     执行连接点
    //     @AfterReturning
    // } catch (Throwable e) {
    //     @AfterThrowing
    // } finally {
    //     @After
    // }
    // @Around结束
    // ------------------------------------------------------------------------
    // execution配置的就是切入点表达式，用于匹配某些方法
    // 在切入点表达式中，可以使用通配符：
    // -- 1个星号：任意1次匹配
    // -- 2个连续的小数点：任意n次匹配，只能用于包或和参数列表
    //                 ↓ 返回值类型
    //                   ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 包名
    //                                                  ↓ 类名
    //                                                    ↓ 方法名
    //                                                      ↓↓ 参数列表
    // 另外，在表达式中，在方法的返回值的左侧还可以指定修饰符，修饰符是可选的
    // 注解是典型的修饰符之一
    // 其实，Spring MVC的统一异常处理就是通过AOP实现的，
    // Spring JDBC的事务管理也是如此
    // Spring Security的权限检查也是如此
    @Around("execution(* cn.tedu.csmall.product.service.*.*(..))")
    public Object xxx(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        // 获取匹配的方法的相关信息
        String targetClassName = pjp.getTarget().getClass().getName(); // 获取匹配的方法所在的类
        String signatureName = pjp.getSignature().getName(); // 获取匹配的方法的签名中的方法名称
        Object[] args = pjp.getArgs(); // 方法的参数列表
        System.out.println("类型：" + targetClassName);
        System.out.println("方法名：" + signatureName);
        System.out.println("参数列表：" + Arrays.toString(args));

        // 执行以上表达式匹配的方法，即某个Service的某个方法
        // 注意-1：必须获取调用proceed()方法返回的结果，作为当前切面方法的返回值
        // -- 如果没有获取，或没有作为当前切面方法的返回值，相当于执行了连接点方法，却没有获取返回值
        // 注意-2：调用proceed()时的异常必须抛出，否则，Controller将无法知晓Service抛出过异常，就无法向客户端响应错误信息
        // -- 前提：本例的切面是作用于Service的
        // -- 其实，你也可以使用try...catch捕获调用proceed()时的异常，但必须在catch中也抛出异常
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - start) + "毫秒");

        // 返回调用proceed()得到的结果
        return result;
    }

}
```































