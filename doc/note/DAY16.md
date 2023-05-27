# Spring MVC框架

## 关于Spring MVC框架

**MVC**：**M**odel + **V**iew + **C**ontroller

Spring MVC框架主要解决了接收请求、响应结果及相关问题（例如处理异常等），即主要关注C的问题，在不是前后端分离的项目，还关心V的问题，但是，并不关心M的问题。

## Spring MVC框架的依赖项

Spring MVC框架的基础依赖项是：`spring-webmvc`

在Spring Boot中，使用Spring MVC框架应该添加依赖项：`spring-boot-starter-web`

## 使用Spring MVC框架接收请求

应该创建控制器类，并在控制器类上添加`@Controller`注解，在Spring MVC框架中，只有添加此注解的类才是控制器类！

默认情况下，在Spring MVC框架中，控制器处理请求的结果（方法的返回值）将被视为“视图组件的名称”，当在处理请求的方法上添加`@ResponseBody`注解后，返回的结果才会被视为“响应到客户端的数据”，`@ResponseBody`注解还可以添加在控制器类上，表示此类中所有处理请求的方法的返回结果都是“响应到客户端的数据”，这种做法称之为“响应正文”，或者，也可以将控制器类上的注解改为`@RestController`，它是`@Controller`和`@ResponseBody`的组合注解！

包括处理异常的“全局异常处理器类”，需要添加的其实是`@ControllerAdvice`注解，添加此注解的类中的特定方法（例如处理异常的方法）将可以作用于每次处理请求的过程中，但是，默认情况下，仍不是“响应正文”的，所以，当处理异常需要响应正文时，需要在处理异常的方法上添加`@ResponseBody`注解，或在全局异常处理器的类上添加`@ResponseBody`注解，或将`@ControllerAdvice`改为`@RestControllerAdvice`。

在控制器类上，可以自定义方法用于处理请求，关于这些方法：

- 访问权限：应该是`public`
- 返回值类型：当响应正文时，可自行将需要响应的数据属性封装在自定义类中，使用自定义类作为返回值类型
  - 当使用自定义类型进行响应正文时，需要：
    - 需要添加`jackson-databind`依赖项，在`spring-boot-starter-web`中已经包含
    - 需要启用Spring MVC的注解增强，如果使用XML配置，需要添加`<annotation-driven/>`，如果使用配置类进行配置，需要在配置类上添加`@EnableWebMvc`注解，否则，响应时会出现`406`错误，在Spring Boot项目不需要手动配置
- 方法名称：自定义
- 参数列表：按需添加，且各参数不区分先后顺序，可以将各请求参数逐一作为参数列表中的参数，也可以将多个请求参数封装到自定义类型中，使用自定义类型作为方法参数列表中的参数，还可以按需添加`HttpServletRequest`、`HttpServletResponse`、`HttpSession`等，在使用其它技术框架后，还可以按需添加其它参数，例如结合`@AuthenticationPrincipal`注解添加Spring Security的当事人

- 抛出异常：理论上，处理请求的方法不应该处理异常，而应该抛出，进而交由全局异常处理器进行处理

所有处理请求的方法都必须添加`@RequestMapping`系列的某个注解，通过这些注解来配置请求路径。

## 关于`@RequestMapping`注解

`@RequestMapping`注解的主要作用是配置请求路径，通常，在类上应该配置此注解，例如：

```java
@RestController
@RequestMapping("/admin")
public class AdminController {}
```

在方法上，建议使用限制了请求方式的某个基于`@RequestMapping`的注解，例如：

```java
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @PostMapping("/login")
    public JsonResult login() {
        // ...
    }
    
    @GetMapping("/list")
    public JsonResult list() {
        // ...
    }
    
}
```

如果响应可能出现乱码，建议在类上的`@RequestMapping`上配置`produces`属性，以指定响应的文档类型，例如：

```java
@RestController
@RequestMapping(value = "/admin", produces = "application/json; charset=utf-8")
public class AdminController {}
```

## 关于RESTful

RESTful是一种软件设计的风格（并不是规则或规范）。

RESTful的典型特征包括：

- 将某些具有“唯一性”的参数值作为URL的一部分，例如：

  ```
  http://localhost:9080/album/9527/delete
  ```

  ```
  https://gitee.com/chengheng2022/jsd2302-csmall-server-teacher
  ```

- 是前后端分离的，即：是响应正文的

- 根据处理数据的方式来选择请求方式

  - 增加新的数据时，应该提交`POST`类型的请求
  - 修改数据时，应该提交`PUT`类型的请求
  - 删除数据时，应该提交`DELETE`类型的请求
  - 获取数据时，应该提交`GET`类型的请求
  - 通常不照做

Spring MVC框架很好的支持了RESTful风格，当设计URL时，如果URL中包含参数值，可以使用`{自定义名称}`进行占位，例如：

```java
// http://localhost:9180/album/9527/delete
@PostMapping("/{id}/delete")
//             ↑↑↑↑ 占位符
```

然后，在处理请求的方法上，在对应的参数上添加`@PathVariable`注解，表示此参数值来自URL中同名占位符对应的值，例如：

```java
@PostMapping("/{id}/delete")
public JsonResult delete(@PathVariable Long id) {
    // ...
}
```





















