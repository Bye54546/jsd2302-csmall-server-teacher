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
    active: test
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





















