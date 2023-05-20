package cn.tedu.csmall.passport.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 白名单
        // 使用1个星号，可以通配此层级的任何资源，例如：/admin/*，可以匹配：/admin/add-new、/admin/list，但不可以匹配：/admin/password/change
        // 使用2个连续的星可以，可以通配若干层级的资源，例如：/admin/**，可以匹配：/admin/add-new、/admin/password/change
        String[] urls = {
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
        };

        // 配置授权访问
        // 注意：以下授权访问的配置，是遵循“第一匹配原则”的，即“以最先匹配到的规则为准”
        // 例如：anyRequest()是匹配任何请求，通常，应该配置在最后，表示“除了以上配置过的以外的所有请求”
        // 所以，在开发实践中，应该将更具体的请求配置在靠前的位置，将更笼统的请求配置在靠后的位置
        http.authorizeRequests() // 开始对请求进行授权
                .mvcMatchers(urls) // 匹配某些请求
                .permitAll() // 许可，即不需要通过认证就可以访问
                .anyRequest() // 任何请求
                .authenticated() // 要求已经完成认证的
        ;

        // super.configure(http); // 不要保留调用父级同名方法的代码，不要保留！不要保留！不要保留！

        // 如果调用以下方法，当Security认为需要通过认证，但实际未通过认证时，就会跳转到登录页面
        // 如果未调用以下方法，将会响应403错误
        http.formLogin();
    }

}
