# Knife4j框架

Knife4j框架是一款国人开发的、基于Swagger 2的<u>在线API文档</u>框架。

Knife4j的简单使用只需要3步：

- 添加依赖：`knife4j-spring-boot-starter`，版本`2.0.9`
  - 注意：建议使用Spring Boot 2.5.x版本，如果使用更高版的Spring Boot，Knife4j的版本也需要提高
- 添加配置：在配置文件中添加`knife4j.enable`属性的配置，取值为`true`
- 添加配置类：类的代码相对固定

关于依赖：

```xml
<knife4j-spring-boot.version>2.0.9</knife4j-spring-boot.version>
```

```xml
<!-- Knife4j Spring Boot：在线API文档 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j-spring-boot.version}</version>
</dependency>
```

关于配置：在`application.yml`中添加配置：

![image-20230510094250450](assets/image-20230510094250450.png)

关于添加配置类，直接复制现有的配置类代码即可，但是，一定要检查配置Controller包的属性值是否与你的项目相符合：

```java
package cn.tedu.csmall.product.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    /**
     * 【重要】指定Controller包路径
     */
    private String basePackage = "cn.tedu.csmall.product.controller";
    /**
     * 分组名称
     */
    private String groupName = "product";
    /**
     * 主机名
     */
    private String host = "http://java.tedu.cn";
    /**
     * 标题
     */
    private String title = "酷鲨商城在线API文档--商品管理";
    /**
     * 简介
     */
    private String description = "酷鲨商城在线API文档--商品管理";
    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 联系人
     */
    private String contactName = "Java教学研发部";
    /**
     * 联系网址
     */
    private String contactUrl = "http://java.tedu.cn";
    /**
     * 联系邮箱
     */
    private String contactEmail = "java@tedu.cn";
    /**
     * 版本号
     */
    private String version = "1.0.0";

    @Autowired
    private OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jConfiguration() {
        log.debug("创建配置类对象：Knife4jConfiguration");
    }

    @Bean
    public Docket docket() {
        String groupName = "1.0.0";
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }
}
```

完成后，可以通过 `/doc.html` 来访问API文档，即在浏览器的地址栏中输入网址：http://localhost:8080/doc.html

# 关于Profile配置文件

在Spring系列框架中，关于配置文件，允许同时存在多个配置文件（例如同时存在`a.yml`、`b.yml`等），并且，你可以按需切换某个配置文件，这些默认不生效、需要被激活才生效的配置，称之为Profile配置。

在Spring Boot项目中，Profile配置的文件名必须是`application-自定义名称.properties`（或使用YAML的扩展名），例如：`application-a.yml`、`application-b.yml`，并且，这类配置文件默认就是没有激活的。

通常，关于“自定义名称”部分的惯用名称有：

- `dev`：表示开发环境
- `test`：表示测试环境
- `prod`：表示生产环境（项目上线）

当然，你也可以根据你所需要的环境或其它特征来处理“自定义名称”部分。

在Spring Boot项目中，`application.properties`（或使用YAML的扩展名）是始终加载的配置文件，当需要激活某个Profiel配置文件时，可以在`application.properties`中配置：

```properties
spring.profiles.active=自定义名称
```

例如：

![image-20230510103823707](assets/image-20230510103823707.png)

在开发实践中，需要学会区分哪些配置属性是固定的，哪些是可能调整的，然后，把不会因为环境等因素而发生变化的配置写在`application.properties`中去，把可能调整的配置写在Profile文件中。

例如，在`application.yml`中配置（以下配置中不包含连接数据库的URL、用户名、密码）：

```yaml
spring:
  profiles:
    active: dev
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 5
      max-active: 10
    
mybatis:
  mapper-locations: classpath:mapper/*.xml
  
knife4j:
  enable: true
```

并且，在其它Profile配置中补充可能调整的配置，例如在`application-dev.yml`中配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mall_pms?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

另外，通过`spring.profiles.active`激活配置时，此属性的值可以使用逗号分隔来激活多个配置，例如：

```properties
spring.profiles.active=dev,test
```

如果同时激活的配置中，有相同的属性，但属性值并不同，以偏后的Profile配置文件中的配置值为准，按照以上代码，则以`test`中的配置为准！

另外，所有Profile配置的优先级都高于`application.properties`。

# 关于Slf4j日志

在开发实践中，应该将数据的关键变化、关联的处理流程记录下来，以便于出现问题时可以辅助排查问题！

注意：在开发实践中，禁止使用`System.out.println()`的做法输出内容（测试代码除外），主要原因有：

- 不易于编写代码
  - 如果输出的结果中存在较多的字符串与变量的拼写，代码的可读性非常大

- 执行效率非常低下
  - 字符串的拼接效率非常低

- 无论什么情况下都会显示
  - 也许某些输出的数据是敏感的，在开发过程中输出这些数据是为了便于调试，但是，在生产过程中不应该被看到

应该使用日志框架，通过输出日志的方式，来记录相关信息！

在Spring Boot框架的基础依赖项（`spring-boot-starter`）中已经包含日志框架的依赖项， 所以，并不需要专门的去添加日志框架的依赖项，是可以直接使用的！

在添加了Lombok依赖项后，可以在任何类上添加`@Slf4j`注解，则Lombok会在编译期自动在此类中声明名为`log`的变量，通过此变量调用方法即可输出日志。

日志是有**显示级别**的，根据日志内容的重要程度，从可以不关注，到必须关注，依次为：

- `trace`：跟踪信息，例如记录程序执行到了哪个环节，或哪个类、哪个方法等
- `debug`：调试信息，通常输出了一些数据值，用于观察数据的走向、变化过程
- `info`：一般信息，通常不涉及隐私或机密，即使被他人看到也不要紧的
- `warn`：警告信息，通常是可能存在某种问题，但却不影响程序的执行
- `error`：错误信息

在使用`log`变量输出日志时，可以调用以上5个级别对应的方法，即可输出对应级别的日志！例如调用`log.info()`方法输出的就是`info`级别的日志，调用`log.error()`方法输出的就是`error`级别的日志。

在日志框架中，输出每个级别的方法的参数列表都有相同的版本，例如存在：

```java
void info(String message);
void info(String message, Object... args);
```

就还有：

```java
void trace(String message);
void trace(String message, Object... args);

void debug(String message);
void debug(String message, Object... args);

void warn(String message);
void warn(String message, Object... args);

void error(String message);
void error(String message, Object... args);
```

在普通项目中，默认显示`debug`级别的日志，则`debug`级别及更加重要级别的日志都会显示出来，即`trace`级别不会被显示！

在Spring Boot项目中，默认显示`info`级别的日志！

在Spring Boot项目中，可以通过配置文件中的`logging.level.根包名[.类名]`属性来调整日志的显示级别！

> 提示：以上`logging.level.根包名[.类名]`中的中括号部分是可选的！

例如配置为：

```yaml
logging:
  level:
    cn.tedu.csmall.product: trace
```

则`cn.tedu.csmall.product`包及其子孙包下所有的类中输出的日志的显示级别都将是`trace`！

在输出日志时，如果需要输出一些变量的值，应该优先使用参数列表为`(String message, Object... args)`的方法，例如：

```java
int x = 1;
int y = 2;
log.info("x = {}, y = {}, x + y = {}", x, y, x + y);
```

需要注意：SLF4j日志框架只是一个日志标准，并没有具体的实现日志功能，而具体的功能是由`logback`、`log4j`等日志框架实现的。

# 关于GET与POST类型的请求

**GET**：请求参数只能体现在URL中，所以，不适合传输敏感数据，也无法传输大量数据（受到URL长度限制），而GET的优点在于可以保存、分享，并且访问效率略高

**POST**：请求参数可以放在请求体中，所以，适合传输敏感数据，并且，不受到长度限制

在开发实践中，对请求方式的选取，比较通用的原则是：如果客户端提交的请求主要目的是为了获取数据，则使用`GET`，否则，使用`POST`。

在控制器中，在处理请求的方法上，应该限制请求方式，例如配置为：

```java
@RequestMapping(value = "/add-new", method = RequestMethod.POST)
```

以上配置会将`/add-new`的请求方式限制为`POST`这1种！

或者，你也可以使用基于`@RequestMapping`的组合注解，例如：

```java
@PostMapping("/add-new")
```

与之类似的注解还有：`@GetMapping`、`@PutMapping`、`@DeleteMapping`、`@PatchMapping`。

# 关于Knife4j的显示内容的配置

- `@Api`：添加在控制器类上，通过此注解的`tags`属性，可以配置模块名称（显示在API文档左侧目录中的名称），提示：当存在多个控制器时，显示的顺序是根据配置的模块名称来决定的，如果需要自行指定顺序，建议在各模块名称前添加数字编号，例如：

  ```java
  @RestController
  @RequestMapping("/album")
  @Api(tags = "04. 相册管理模块")
  public class AlbumController {
  }
  ```

- `@ApiOperation`：添加在处理请求的方法上，通过此注解的`value`属性，可以配置业务功能名称

- `@ApiOperationSupport`：添加在处理请求的方法上，通过此注解的`order`属性（`int`类型），可以配置业务功能的排序序号，将升序排列，例如：

  ```java
  @PostMapping("/delete")
  @ApiOperation("根据ID删除相册")
  @ApiOperationSupport(order = 200)
  public String delete() {
      // ...
  }
  ```

- `@ApiModelProperty`：添加在封装的请求参数的属性上，通过此注解的`value`属性，可以配置请求参数的描述信息，通过此注解的`required`属性，可以配置是否必须提交此参数（此配置只是一种显示效果，并不具备真正的检查功能），通过此注解的`example`属性，可以配置示例值，例如：

  ```java
  @Data
  public class AlbumAddNewParam implements Serializable {
  
      @ApiModelProperty(value = "相册名称", required = true, example = "可乐的相册")
      private String name;
  
      @ApiModelProperty(value = "相册简介", required = true, example = "可乐的相册的简介")
      private String description;
  
      @ApiModelProperty(value = "排序序号，必须是1~255之间的数字", required = true, example = "97")
      private Integer sort;
  
  }
  ```

- `@ApiIgnore`：添加在请求参数上，表示API文档将忽略此请求参数

  ```java
  @PostMapping("/add-new")
  @ApiOperation("添加相册")
  @ApiOperationSupport(order = 100)
  public String addNew(AlbumAddNewParam albumAddNewParam, 
                       @ApiIgnore HttpSession session) {
      // ...
  }
  ```

- `@ApiImplicitParam`：添加在处理请求的方法上，用于对未封装的请求参数进行描述，注意，此注解必须配置`name`属性，取值为方法的参数名，然后，结合此注解的`value`属性对参数进行描述，此注解还有与`@ApiModelProperty`相同的一些属性，例如`required`、`example`等，还可以通过`dataType`指定数据类型

- `@ApiImplicitParams`：添加在处理请求的方法上，当有多个`@ApiImplictParam`需要被配置时，应该将它们作为当前`@ApiImplicitParams`的属性值，例如：

  ```java
  @PostMapping("/delete")
  @ApiOperation("根据ID删除相册")
  @ApiOperationSupport(order = 200)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "albumId", value = "相册ID", required = true, dataType = "long"),
          @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long")
  })
  public String delete(Long albumId, Long userId) {
      // ...
  }
  ```

# 添加属性模板

开发步骤大致是：

- Mapper层：

  - 创建属性模板的实体类，在类中声明与数据表对应的所有属性，此类需要实现`Seriliazable`接口，添加`@Data`注解，在类上添加`@TableName`注解以配置数据表名称，在主键对应的`id`属性上添加`@TableId`注解以配置主键值是自动编号的
  - 创建处理属性模板数据的Mapper接口，继承自`BaseMapper`，泛型为属性模板的实体类，在接口上添加`@Repository`注解
    - 暂时不需要对应的XML文件
  - 在`src/test/java`的根包下创建对应的测试类，在测试类中编写测试方法，以检验是否可以成功的插入数据

- Service层：

  - 创建`pojo.param.AttributeTemplateAddNewParam`类，在类中声明相关属性（4个），此类需要实现`Serializable`接口，并在类上添加`@Data`注解

  - 创建`service.IAttributeTemplateService`接口，并在接口声明抽象方法：

    ```java
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);
    ```

  - 创建`service.impl.AttributeTemplateServiceImpl`类，实现以上接口，在类上添加`@Service`注解和`@Slf4j`注解，在类中自动装配对应的Mapper对象，然后重写方法：

    ```java
    // 先检查名称是否被占用，如果被占用，则抛出异常
    
    // 向数据库中插入属性模板数据
    ```

  - 在`src/test/java`的根包下创建对应的测试类，在测试类中编写测试方法，以检验是否可以成功的插入数据

- Controller层：

  - 创建`cn.tedu.csmall.product.controller.AttributeTemplateController`类，在类上添加`@RestController`注解、`@RequestMapping("/attribute-template")`注解在类中自动装配对应的Service接口类型的对象，在类中声明处理请求的方法，方法上需要添加`@PostMapping("/add-new")`注解，在方法体中调用Service实现功能，并且，在调用时使用`try...catch`捕获并处理异常
  - 完成后，重启项目，通过API文档测试访问
  - 在控制器类上添加`@Slf4j`注解、`@Api`注解并配置，在处理请求的方法上添加`@ApiOperation`注解、`@ApiOperationSupport`注解并配置
  - 在`AttributeTemplateAddNewParam`的各属性上添加`@ApiModelProperty`注解并配置























