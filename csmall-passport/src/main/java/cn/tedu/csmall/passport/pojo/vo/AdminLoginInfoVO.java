package cn.tedu.csmall.passport.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员的登录信息的VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AdminLoginInfoVO implements Serializable {

    /**
     * 管理员ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（密文）
     */
    private String password;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

    /**
     * 权限列表
     */
    private List<String> permissions;

}