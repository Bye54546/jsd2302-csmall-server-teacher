package cn.tedu.csmall.passport.pojo.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AdminLoginInfoParam implements Serializable {

    private String username;
    private String password;

}
