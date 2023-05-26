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









