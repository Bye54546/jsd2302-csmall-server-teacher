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
```























