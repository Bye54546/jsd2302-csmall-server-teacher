package cn.tedu.csmall.passport.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AdminDetails extends User {

    @Getter
    private Long id;

    public AdminDetails(Long id, String username, String password,
                        boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,
                true, true, true,
                authorities);
        this.id = id;
    }

}
