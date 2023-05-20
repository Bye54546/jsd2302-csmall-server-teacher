package cn.tedu.csmall.passport.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("用户名：{}", s);
        // 假设正确的用户名是root，匹配的密码是1234
        if (!"root".equals(s)) {
            log.debug("此用户名没有匹配的用户数据，将返回null");
            return null;
        }

        log.debug("用户名匹配成功！准备返回此用户名匹配的UserDetails类型的对象");
        UserDetails userDetails = User.builder()
                .username(s)
                .password("1234")
                .disabled(false) // 账号状态是否禁用
                .accountLocked(false) // 账号状态是否锁定
                .accountExpired(false) // 账号状态是否过期
                .credentialsExpired(false) // 账号的凭证是否过期
                .authorities("这是一个临时使用的山寨的权限！！！") // 权限
                .build();
        log.debug("即将向Spring Security返回UserDetails类型的对象：{}", userDetails);
        return userDetails;
    }

}
