package cn.tedu.csmall.passport.pojo.pram;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AdminAddNewParam implements Serializable {

    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String description;
    private Integer enable;
    private Long[] roleIds;

}
