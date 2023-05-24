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













