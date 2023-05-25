## 处理解析JWT时的异常

由于解析JWT是在过滤器组件中执行的，而过滤器是最早处理请求的组件，此时，控制器（Controller）还没有开始处理这次的请求，则全局异常处理器也无法处理解析JWT时出现的异常（全局异常处理器只能处理控制器抛出的异常）！

首先，在`ServiceCode`中补充新的状态码：

```java
ERR_JWT_EXPIRED(60000),
ERR_JWT_MALFORMED(60100),
ERR_JWT_SIGNATURE(60200),
```

然后，在`JwtAuthorizationFilter`中，使用`try...catch`包裹尝试解析JWT的代码：

```java
// 尝试解析JWT
response.setContentType("application/json; charset=utf-8");
Claims claims = null;
try {
    claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
} catch (MalformedJwtException e) {
    String message = "非法访问！";
    log.warn("程序运行过程中出现了MalformedJwtException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_MALFORMED, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (SignatureException e) {
    String message = "非法访问！";
    log.warn("程序运行过程中出现了SignatureException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (ExpiredJwtException e) {
    String message = "您的登录信息已经过期，请重新登录！";
    log.warn("程序运行过程中出现了ExpiredJwtException，将向客户端响应错误信息！");
    log.warn("错误信息：{}", message);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
} catch (Throwable e) {
    String message = "服务器忙，请稍后再试！【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在解析JWT的过滤器中补充处理对应异常的代码块】";
    log.warn("程序运行过程中出现了Throwable，将向客户端响应错误信息！");
    log.warn("异常：", e);
    JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNKNOWN, message);
    String jsonString = JSON.toJSONString(jsonResult);
    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonString);
    printWriter.close();
    return;
}
```

