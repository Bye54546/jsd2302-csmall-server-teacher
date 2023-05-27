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

如果占位符中的名字与参数的变量名不同，需要在`@PathVariable`注解上指定参数，例如：

```java
@PostMapping("/{id}/delete")
public JsonResult delete(@PathVariable("id") Long albumId) {
    //                                ↑↑↑↑↑↑ 指定注解参数，与占位符名称一致，则方法参数的变量名已经不重要了
    // ...
}
```

在配置占位符名称时，可以在自定义名称的右侧添加冒号，然后，在冒号右侧添加正则表达式，以限制URL传入的值的格式，例如：

```java
@PostMapping("/{id:[0-9]+}/delete")
```

经过以上配置，只有占位符位置是纯数字的请求才可以匹配上，如果占位符位置不是纯数字的请求（例如`/abc/delete`）则会响应`404`。

注意：多不冲突的正则表式达的占位设计是允许共存的，例如：

```java
@PostMapping("/{id:[0-9]+}/delete")
public JsonResult delete1() {}
```

```java
@PostMapping("/{xx:[a-z]+}/delete")
public JsonResult delete2() {}
```

另外，如果还有某个配置没有使用占位符的设计，也是允许与以上设计共存的，例如：

```java
@PostMapping("/test/delete")
public JsonResult deleteTest() {}
```

关于RESTful风格的URL设计，如果你没有更好的选择，可以参考：

- 查询数据列表，格式为：`/数据类型的复数`
  - 例如：`/albums`
- 获取某类型的1个数据，格式为：`/数据类型的复数/{数据ID}`
  - 例如：`/albums/{id:[0-9]+}`

- 对某类型的1个数据执行某个数据管理操作，格式为：`/数据类型的复数/{数据ID}/命令`
  - 例如：`/albums/{id:[0-9]+}/delete`

## 关于`@RequestParam`注解

`@RequestParam`注解是添加在方法的参数上的，它的作用主要有：

- 配置请求参数的名称（用于请求参数的名称与方法参数不同时），例如：

  ```java
  // http://localhost:8080/login?username=root
  public JsonResult login(@RequestParam("username") String xxx) {
      // ...
  }
  ```

- 配置必须提交某个请求参数，因为此注解`required`属性默认值为`true`

  - 此功能可以通过Validation框架来实现

- 配置某个请求参数的默认值，例如：

  ```java
  // http://localhost:8080/list
  public JsonResult list(@RequestParam(defaultValue = "1") Integer page) {
      // ...
  }
  ```

## 其它

例如全局异常处理器等

# MyBatis框架

## 关于MyBatis框架

MyBatis框架的主要作用：实现并简化数据库编程。

## MyBatis框架的依赖项

MyBatis框架的依赖项是：`mybatis`，但，通常还应该再添加：`mybatis-spring`、`spring-jdbc`、`mysql`或其它数据库的依赖项、数据库连接池的依赖项（`commons-dbcp` / `commons-dbcp2` / Druid / Hikari等）、测试

在Spring Boot项目中，只需要添加`mybatis-spring-boot-starter`即可包含`mybatis`、`mybatis-spring`、`spring-jdbc`、默认的数据库连接池。

## 关于Mapper接口

使用MyBatis时，需要使用Mapper接口来封装访问数据的抽象方法，这些接口可以自行放在任何位置，但需要通过注解来指定，使得MyBatis框架能找到这些接口！可以：

- 【推荐】在配置类上使用`@MapperScan`注解，来配置Mapper接口所在的根包（其子孙级包中的Mapper接口也会被找到）
  - 注意：配置的包下，不能有Mapper接口以外的其它接口
- 【不推荐】在各Mapper接口上添加`@Mapper`注解

所有的数据访问功能都应该在接口中定义抽象方法，关于抽象方法：

- 返回值类型：如果需要执行的SQL语句是增、删、改类型的，应该使用`int`作为返回值类型，表示“受影响的行数”，其实也可以使用`void`，表示不关心受影响的行数，但不推荐；如果需要执行的SQL语句是查询类型的，只需要保证声明的返回值类型足以存放你需要的查询结果即可

- 方法名称：自定义，不建议重载

  ```
  《阿里巴巴Java开发手册》中的参考：
  1） 获取单个对象的方法用 get 做前缀。
  2） 获取多个对象的方法用 list 做前缀。
  3） 获取统计值的方法用 count 做前缀。
  4） 插入的方法用 save/insert 做前缀。
  5） 删除的方法用 remove/delete 做前缀。
  6） 修改的方法用 update 做前缀。
  ```

- 参数列表：根据需要执行的SQL语句中的参数来设计，并且，如果参数数量较多，可以封装成自定义数据类型，并使用自定义数据类型作为方法的参数，另外，对于未封装的简单数据类型的参数（例如基本数据类型及对应的包装类、`String`类），只要参数的数量超过1个，强烈建议在各参数上使用`@Param`注解配置参数名称，例如：

  ```java
  void getLoginInfoByUsernameAndPassword(
      @Param("username") String username, @Param("password") String password);
  ```

## 关于`@Param`注解

通常，如果抽象方法中声明了简单类型的参数，在配置SQL语句时，可以使用`#{}`格式的占位符来表示参数的值，例如：

```java
AlbumStandardVO getStandardById(Long id);
```

```xml
<select id="getStandardById" resultType="cn.tedu.csmall.product.pojo.vo.AlbumStandardVO">
    SELECT id, name, description, sort
    FROM pms_album
    WHERE id=#{xxx}
</select>
```

其实，以上配置SQL语句时，`#{}`中的名称是很随意的，并不一定需要是抽象方法中的参数的名称！之所以是这样，主要因为：

- 在Java语言中，默认情况下，所有局部的量（方法内部的局部变量、方法的参数）的名称都会在编译时丢失
- 此抽象方法只有1个参数，MyBatis会自动的去找那唯一的参数，所以，名称并不重要

由于局部的量的名称会丢失，且方法只有1个参数时，MyBatis就会使用唯一的那个参数值，但是，如果抽象方法有2个或更多个参数，MyBatis就不知道把哪个参数值传到哪个`#{}`占位符对应的位置！为了解决此问题，MyBatis使用了`@Param`注解，通过此注解来配置参数的名称，并且，在配置SQL时，应该使用注解中配置名字，例如：

```java
AdminStandardVO getLoginInfoByUsernameAndPassword(
    @Param("username") String var1, @Param("password") String var2);
```

```xml
<select id="getLoginInfoByUsernameAndPassword" 
        resultType="cn.tedu.csmall.product.pojo.vo.AdminStandardVO">
    SELECT *
    FROM ams_admin
    WHERE username=#{username} AND password=#{password}
</select>
```

另外，在整合了Spring Boot后，使用的`mybatis-spring-boot-starter`对编译期进行了干预，保留了抽象方法的参数名称，所以，在Spring Boot中，即使不使用`@Param`注解来配置参数名称，也能够正确的找到各参数！通常，为了避免出问题，仍建议使用`@Param`注解配置参数名称！

## 关于使用注解来配置SQL语句

在使用MyBatis时，可以在抽象方法上使用`@Insert` / `@Delete` / `@Update` / `@Select`注解来配置SQL语句，例如：

```java
public interface AlbumMapper {
    
    @Select("select count(*) from pms_album")
    int count();
    
}
```

但是，并不推荐使用这种做法，主要原因有：

- 不适合编写较长篇幅的SQL语句
- 不适合需要添加其它配置的数据访问
- 不适合与DBA协同工作

## 关于使用XML来配置SQL语句

在Spring Boot项目中，需要通过配置文件中的`mybatis.mapper-locations`属性来配置XML文件的位置，如果使用的是MyBatis-Plus，则需要通过`mybatis-plus.mapper-locations`属性来配置XML文件的位置。

> 提示：如果使用的不是Spring Boot项目，则需要配置`SqlSessionFactoryBean`类型的对象来指定XML文件的位置。

在XML文件内部的配置：

- 此文件的根标签必须是`<mapper>`，且必须配置`namespace`属性，以指定对应的Mapper接口的全限定名
- 在`<mapper>`标签的子级，使用`<insert>` / `<delete>` / `<update>` / `<select>`标签配置SQL语句，这些标签必须配置`id`属性，取值为抽象方法的名称，在这些标签内部，配置抽象方法对应的SQL语句
- 如果对应的数据表中的ID是自动编号的，在配置`<insert>`标签时，强烈建议配置`useGeneratedKeys="true"`和`keyProperty="id"`这2个属性，以获取成功插入的数据的自动编号ID值
- 在`<select>`标签上，必须配置`resultType`和`resultMap`这2个属性中的其中1个，使用`resultType`时，取值为抽象方法的返回值类型的全限定名，使用`resultMap`时，取值为对应的`<resultMap>`标签的`id`属性值
- 动态SQL之`<foreach>`标签：用于对参数进行遍历
  - `collection`属性：指定被遍历的参数，当抽象方法的参数只有1个，且没有添加`@Param`注解时，如果参数是`List`集合，则此属性取值为`list`，如果参数是数组或可变参数，则此属性取值为`array`，如果抽象方法的参数有多个或配置了`@Param`注解，则此属性取值为`@Param`注解中配置的名称
  - `item`属性：自定义此值，表示遍历过程中各元素的变量名
  - `separator`属性：遍历过程中生成各值时，各值之间的分隔符号

- 动态SQL之`<if>`标签：用于对参数进行判断，例如：

  ```xml
  <!-- int update(Album album); -->
  <update id="update">
      UPDATE 
      	pms_album
      <set>
      	<if test="name != null">
      		name=#{name},
          </if>
      	<if test="description != null">
      		description=#{description},
          </if>
          <if test="sort != null">
      		sort=#{sort},
          </if>
      </set>
      WHERE
      	id=#{id}
  </update>
  ```

- 动态SQL之`<choose>`系列标签，用于实现类似Java语言中`if...else`的效果，例如：

  ```xml
  <choose>
  	<when test="条件">
      	满足条件时的代码片段
      </when>
      <otherwise>
      	不满足条件时的代码片段
      </otherwise>
  </choose>
  ```

- `<sql>`标签与`<include>`标签，用于封装SQL语句片段（通常是字段列表）并引用封装的SQL语句片段，例如：

  ```xml
  <!-- List<RoleListItemVO> list(); -->
  <select id="list" resultType="cn.tedu.csmall.passport.pojo.vo.RoleListItemVO">
      SELECT
          <include refid="ListItemQueryFields"/>
      FROM
          ams_role
      ORDER BY
          sort DESC, id
  </select>
  
  <sql id="ListItemQueryFields">
      id, name
  </sql>
  ```

  提示：在IntelliJ IDEA中，在`<sql>`标签中直接写字段列表会提示错误，但这并不影响运行，如果不希望看到报错的红色波浪线，可以改为：

  ```xml
  <sql id="ListItemQueryFields">
      <if test="true">
          id, name
      </if>
  </sql>
  ```

- 关于`<resultMap>`标签：详见项目中的`AdminMapper.xml`代码

## MyBatis的缓存机制

MyBatis框架存在缓存机制，表现为：当执行查询后，可以将查询结果暂时保存到应用程序服务器中（即使返回了查询结果，也不会销毁这个数据），后续再次查询时，就可以将此前的查询结果直接返回，以此提高“查询”效率。

MyBatis框架有2种缓存机制，分别称之为一级缓存和二级缓存。

一级缓存也称之为“会话缓存”，它是基于`SqlSession`的，默认是开启的，且无法关闭，当使用同一个Mapper、执行同样的查询、传递的是同样的参数时，后续的查询将直接返回前序的查询结果。

一级缓存的结果会因为手动清除（调用`SqlSession`对象的`clearCache()`方法）而消失，也会因为此Mapper执行了任何写操作（增、删、改）而自动清除，无论这个写操作是否改变了任何数据！

关于一级缓存的示例：

```java
@Autowired
SqlSessionFactory sqlSessionFactory;

@Test
void l1Cache() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    AlbumMapper albumMapper = sqlSession.getMapper(AlbumMapper.class);

    System.out.println("准备执行第1次查询……");
    Object queryResult1 = albumMapper.getStandardById(1L);
    System.out.println("第1次查询结束！");
    System.out.println(queryResult1);

    System.out.println("准备执行第2次查询……");
    Object queryResult2 = albumMapper.getStandardById(1L);
    System.out.println("第2次查询结束！");
    System.out.println(queryResult2);

    // 清除此前的缓存结果
    // sqlSession.clearCache();
    albumMapper.deleteById(13000000L);

    System.out.println("准备执行第3次查询……");
    Object queryResult3 = albumMapper.getStandardById(1L);
    System.out.println("第3次查询结束！");
    System.out.println(queryResult3);

    System.out.println("准备执行第4次查询……");
    Object queryResult4 = albumMapper.getStandardById(2L);
    System.out.println("第4次查询结束！");
    System.out.println(queryResult4);

    System.out.println("准备执行第5次查询……");
    Object queryResult5 = albumMapper.getStandardById(1L);
    System.out.println("第5次查询结束！");
    System.out.println(queryResult5);
}
```

二级缓存也称之为“namespace缓存”，是基于namespace的（其实就是配置SQL的XML对应的Mapper），在Spring Boot中，默认全局开启，但各namespace默认并未开启，可以在XML文件中添加`<cache/>`标签以开启当前XML对应的Mapper的二级缓存，只要是同样的查询，且传入的参数相同，后续的查询就可以直接使用前序的查询结果。

二级缓存也会因为调用了此XML中的任何写数据的操作而清除！

注意：二级缓存还可以通过在`<select>`标签上配置`useCache`属性来开启或关闭，默认取值为`true`。

注意：当使用二级缓存后，查询结果的类型必须实现`Serializable`接口，如果没有实现，在查询时会抛出`NotSerializableException`异常！

二级缓存的示例代码：

```java
@Autowired
AlbumMapper albumMapper;

@Test
void l2Cache() {
    System.out.println("准备执行第1次查询……");
    Object queryResult1 = albumMapper.getStandardById(1L);
    System.out.println("第1次查询结束！");
    System.out.println(queryResult1);

    System.out.println("准备执行第2次查询……");
    Object queryResult2 = albumMapper.getStandardById(1L);
    System.out.println("第2次查询结束！");
    System.out.println(queryResult2);

    // albumMapper.deleteById(12L);

    System.out.println("准备执行第3次查询……");
    Object queryResult3 = albumMapper.getStandardById(1L);
    System.out.println("第3次查询结束！");
    System.out.println(queryResult3);

    System.out.println("准备执行第4次查询……");
    Object queryResult4 = albumMapper.getStandardById(2L);
    System.out.println("第4次查询结束！");
    System.out.println(queryResult4);

    System.out.println("准备执行第5次查询……");
    Object queryResult5 = albumMapper.getStandardById(1L);
    System.out.println("第5次查询结束！");
    System.out.println(queryResult5);
}
```

MyBatis在每次查询时，都会优先检查二级缓存，如果命中，将直接返回缓存结果，如果未命中，则会检查一级缓存，如果仍未命中，才会执行真正的查询。





