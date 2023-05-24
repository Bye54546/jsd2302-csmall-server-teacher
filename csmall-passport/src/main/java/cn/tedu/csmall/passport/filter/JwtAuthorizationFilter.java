package cn.tedu.csmall.passport.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        // 放行
        filterChain.doFilter(request, response);
    }

}
