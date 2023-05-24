## 基于方法的权限检查

首先，需要保证“在SecurityContext中的认证信息中包含权限列表”，则在`UserDetailsService`实现类对象中：

![image-20230524093645979](assets/image-20230524093645979.png)

并且，需要在配置类中开启全局的基于方法的安全检查：

![image-20230524093726821](assets/image-20230524093726821.png)

接下来，就可以在任何你认为需要检查权限的方法上通过注解来配置权限，例如：

![image-20230524093812966](assets/image-20230524093812966.png)

根据以上配置，如果登录的用户在权限列表中包含 `/ams/admin/read` 这项权限，将允许访问，否则，将不允许访问！

当Spring Security因为“无此权限”而拒绝访问时，会抛出异常：

```
org.springframework.security.access.AccessDeniedException: 不允许访问
```

则在`ServiceCode`中添加新的业务状态码表示“无此权限”：

![image-20230524095102072](assets/image-20230524095102072.png)

然后，在全局异常处理器中添加处理以上异常的方法：

![image-20230524095136548](assets/image-20230524095136548.png)

提示：以上使用`@PreAuthorize`注解检查权限时，此注解可以添加在任何方法上！例如Controller中的方法，或Service中的方法等等，由于当前项目中，客户端的请求第一时间都是交给了Controller，所以，更适合在Controller方法上检查权限！

## 关于Session

HTTP协议是一种无状态协议，即：当某个客户端向服务器发起请求，服务器端进行处理，后续，此客户端再次发起请求，服务器端并不能直接知道它就是此前来访过的客户端。

从软件技术上，可以使用Session机制来识别客户端的身份，当某个客户端第1次向服务器端发起请求，服务器端会生成一个随机的Session ID（本质上是一个UUID值）并响应给客户端，后续，客户端每次请求时，都会携带这个Session ID来访问服务器端，而服务器端的内存中，使用K-V结构记录每个客户端对应的数据，使用Session ID作为Key，所以，每个客户端在服务器端都有一份属于自己的数据，这个数据就是Session。

由于Session是保存在服务器端的内存中的数据，所以：

- 不适合存储特别大的数据
  - 可以通过开发规范来解决
- 不便于应用到集群或分布式系统
  - 可以通过共享Session的做法缓解此问题
- 不适合长期存储数据
  - 无解

提示：Spring Security的`SecurityContext`默认情况下也是基于Session的。

## 关于Token

Token：票据，令牌

当某个客户端第1次向服务器端发起请求，服务器端会生成Token并响应给客户端，后续，客户端每次请求时，都会携带这个Token来访问服务器端，与Session ID不同，Token本身是有意义的数据，例如，可以包含用户的ID，或用户名等（完全由你来设计），对于服务器端而言，只需要具备“验证并解读Token”的机制即可，并不需要在服务器端保存相关的数据。

由于Token的机制决定了，它天生就适用于集群和分布式系统中，并且，可以非常长时间的保存用户的身份信息！

提示：虽然Token比Session能解决更多的问题，但是，Token的整个使用过程都需要自行编写代码来实现，而Session则简单很多，所以，如果开发特别小型的项目，对存储用户身份信息也没有“长时间”这样的要求，用Session其实更方便。

## 关于JWT

**JWT**：**J**SON **W**eb **T**oken

JWT的官网：https://jwt.io/

每个JWT数据都是由3大部分组成：

- Header（头部）：声明算法与Token类型
- Payload（载荷）：数据
- Verify Signature（验证签名）

可以使用`JJWT`这个工具包来生成、解析JWT，需要在项目中添加依赖项：

```xml
<jjwt.version>0.9.1</jjwt.version>
```

```xml
<!-- JJWT（Java JWT） -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>${jjwt.version}</version>
</dependency>
```

基本使用大致如下：

```java
public class JwtTests {

    // 不太简单的、难以预测的字符串
    String secretKey = "jhdSfkkjKJ3831HdsDkdfSA9jklJD749Fhsa34fdsKf08dfjFhkdfs";

    @Test
    void generate() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 9527);
        claims.put("name", "张三");

        String jwt = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000))
                // Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 生成
                .compact();
        System.out.println(jwt);
    }

    @Test
    void parse() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NDkwODUwMn0.tBo7YKRqQv6TG2cf5jeu7nNjUim5X8H6pKLF1LrYuKI";
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        Long id = claims.get("id", Long.class);
        String name = claims.get("name", String.class);
        System.out.println("id = " + id);
        System.out.println("name = " + name);
    }

}
```

如果尝试解析的JWT已经过期，会出现异常：

```
io.jsonwebtoken.ExpiredJwtException: JWT expired at 2023-05-24T12:02:38Z. Current time: 2023-05-24T14:04:35Z, a difference of 7317175 milliseconds.  Allowed clock skew: 0 milliseconds.
```

如果解析JWT时使用的secretKey有误，会出现异常：

```
io.jsonwebtoken.SignatureException: JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
```

如果解析JWT的数据格式错误，会出现异常：

```
io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters. Found: 1
```

**注意：在不知晓secretKey的情况下，也可以解析出JWT中的数据（例如将JWT数据粘贴到官网），只不过验证签名是失败的，所以，不要在JWT中存放敏感信息！**

## 在项目中使用JWT识别用户的身份

### 核心流程

在项目中使用JWT识别用户的身份，至少需要：

- 当验证登录成功时，生成JWT数据，并响应到客户端去，是“卖票”的过程
  - 当验证登录成功后，不再需要（没有必要）将认证结果存入到`SecurityContext`中
- 当客户端提交请求时，需要获取客户端携带的JWT数据，并尝试解析，解析成功后，再将相关信息存入到`SecurityContext`中去，是“检票”的过程
  - 可以调整Spring Security使用Session的策略，改为不使用Session，则不会将`SecurityContext`存入到Session中

### 验证登录成功时响应JWT

需要调整的代码大致包括：

- 在`IAdminService`中，将`login()`方法的返回值类型改为`String`类型，重写的方法作同样的修改
- 在`AdminServiceImpl`中，验证登录成功后，生成此管理员的信息对应的JWT，并返回
- 在`AdminController`中，处理登录时，调用Service方法时获取返回的JWT，并响应到客户端去

关于`AdminServiceImpl`中的调整：

![image-20230524143453145](assets/image-20230524143453145.png)

### 解析客户端携带的JWT

客户端提交若干种不同的请求时，可能都会携带JWT，对应的，在服务器，处理若干种不同的请求时，也都需要尝试接收并解析JWT，则应该使用**过滤器（Filter）**组件进行处理！

其实，Spring Security框架也使用了许多不同的过滤器来解决各种问题，为了保证解析JWT是有效的，解析JWT的代码**必须运行在Spring Security的某些过滤器之前**，则接收、解析JWT的代码也**必须**定义在过滤器中！

> 提示：过滤器（Filter）是Java服务器端应用程序的核心组件之一，它是最早接收到请求的组件！过滤器可以对请求选择“阻止”或“放行”！同一个项目中，允许存在若干个过滤器，形成“过滤器链（Filter Chain）”，任何请求必须被所有过滤器都“放行”，才会被控制器或其它组件所处理！

在项目的根包下创建`filter.JwtAuthorizationFilter`类，继承自`OncePerRequestFilter`类，并添加`@Component`注解：

```java
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter开始执行……");

        // 放行
        filterChain.doFilter(request, response);
    }

}
```

然后，需要在Spring Security的配置类中，将其添加在Spring Security的某个过滤器之前：

![image-20230524153133103](assets/image-20230524153133103.png)

关于携带JWT，根据业内惯用的做法，客户端会将JWT放在请求头（Request Header）中的Authorization属性中，在Knife4j的API文档中，可以：

![image-20230524154130395](assets/image-20230524154130395.png)

![image-20230524154211821](assets/image-20230524154211821.png)

关于过滤器的初步实现：

```java
/**
 * JWT过滤器，解决的问题：接收JWT，解析JWT，将解析得到的数据创建为认证信息并存入到SecurityContext
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter开始执行……");
        // 根据业内惯用的做法，客户端会将JWT放在请求头（Request Header）中的Authorization属性中
        String jwt = request.getHeader("Authorization");
        log.debug("客户端携带的JWT：{}", jwt);

        // 判断客户端是否携带了有效的JWT
        if (!StringUtils.hasText(jwt)) {
            // 如果JWT无效，则放行
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 当前类和AdminServiceImpl中都声明了同样的secretKey变量，是不合理的
        // TODO 解析JWT过程中可能出现异常，需要处理
        // 尝试解析JWT
        String secretKey = "jhdSfkkjKJ3831HdsDkdfSA9jklJD749Fhsa34fdsKf08dfjFhkdfs";
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        System.out.println("id = " + id);
        System.out.println("username = " + username);

        // TODO 需要考虑使用什么数据作为当事人
        // TODO 需要使用真实的权限
        // 创建认证信息
        Object principal = username; // 可以是任何类型，暂时使用用户名
        Object credentials = null; // 本次不需要
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("山寨权限"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, credentials, authorities);

        // 将认证信息存入到SecurityContext中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // 放行
        filterChain.doFilter(request, response);
    }

}
```

需要注意：由于Spring Security的`SecurityContext`默认是基于Session的，所以，当携带JWT成功访问过后，在`SecurityContext`中就已经有了认证信息，并且，在Session的有效期内，即使后续不携带JWT，Spring Security也能基于Session找到`SecurityContext`并读取到认证信息，这可能与设计初衷并不相符！

可以将Spring Security使用（创建）Session的策略改为“完全不使用Session”，需要在Spring Security的配置类中添加配置：

![image-20230524164412472](assets/image-20230524164412472.png)

## 关于认证信息中的当事人

Spring Security框架并不介意你使用什么类型作为认证信息（`Authentication`）中的当事人（`principal`）！

在项目中，到底使用什么类型作为当事人，可以自行考虑，主要考虑的因素就是：当你需要注入当事人数据的时候，你希望能够得到哪些数据！

















