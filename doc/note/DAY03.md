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











