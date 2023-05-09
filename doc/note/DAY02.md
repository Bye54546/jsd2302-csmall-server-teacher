# 关于数据库连接池

数据库连接池的具体表现是`javax.sql.DataSource`类型的对象，它用于管理若干个数据库连接（`Connection`对象），当需要连接到数据库时，从`DataSource`中取出`Connection`对象即可！

`DataSource`是一个接口，有许多第三方的实现，例如：

- `commons-dbcp2`（是早期的`commons-dbcp`的升级款）
- `Hikari`
- `Druid`
- 其它

在绝大部分情况下，Spring Boot项目默认使用的是`Hikari`。

如果要替换为其它数据库连接池，首先，需要添加对应的依赖，然后，在配置文件中显式的指定对应的`DataSource`。

以替换为`Durid`为例，首先，需要添加`druid-spring-boot-starter`依赖项！先在`csmall-server`中添加：

![image-20230509093734536](assets/image-20230509093734536.png)

![image-20230509093751729](assets/image-20230509093751729.png)

并在`csmall-product`中添加依赖项：

![image-20230509093849530](assets/image-20230509093849530.png)

然后，在`application.properties` / `application.yml`中添加配置，以`.properties`文件的配置为例：

```properties
spring.datasource.type=数据库连接池的类的全限定名
```

通常，还会配置：

```properties
spring.datasource.type.druid.initial-size=初始化连接数
spring.datasource.type.druid.max-active=最大激活数
```

以`.yml`文件配置为例：

![image-20230509094708191](assets/image-20230509094708191.png)

# 数据库中的数据类型与Java数据类型的对应

| 数据库中的类型                  | Java中的类型    |
| ------------------------------- | --------------- |
| `tinyint` / `smallint` / `int`  | `Integer`       |
| `bigint`                        | `Long`          |
| `char` / `varchar` / `text`系列 | `String`        |
| `datetime`                      | `LocalDateTime` |
| `decimal`                       | `BigDecimal`    |

# POJO的设计规范

所有用于声明属性的类，都应该遵循以下规范：

- 存在无参数构造方法
- 所有属性都是私有权限（`private`）的
- 添加每个属性对应的Setters & Getters
- 添加基于所有属性的`hashCode()`与`equals()`
  - 必须保证：如果2个对象的所有属性值完全相同，则`equals()`对比结果为`true`，否则，返回`false`，如果2个对象的`equals()`为`true`，则这2个对应的`hashCode()`返回相同的值，否则，返回不同的值
  - 通常，所有专门的开发软件生成的这2个方法都符合以上特征
- 实现`Serializable`接口

另外，为了便于观察各属性的值，建议添加`toString()`方法。

# 关于Lombok框架

此框架的依赖项代码：

```xml
<lombok.version>1.18.20</lombok.version>
```

```xml
<!-- Lombok的依赖项，主要用于简化POJO类的编写 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
</dependency>
```













