package cn.tedu.csmall.passport.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AdminLoginInfoVO implements Serializable {
    private String username;
    private String password;
    private Integer enable;
}