package cn.tedu.csmall.passport.pojo.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 添加管理员的DTO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AdminAddNewParam implements Serializable {

    /**
     * 用户名
     */
    @NotEmpty(message = "添加管理员失败，请填写用户名！")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{3,15}$", message = "添加管理员失败，用户名必须是4~16个由字母、数字、下划线、减号组成的字符，且第1个字符必须是字母！")
    private String username;

    /**
     * 密码（原文）
     */
    @NotEmpty(message = "添加管理员失败，请填写密码！")
    @Pattern(regexp = "^.{4,16}$", message = "添加管理员失败，密码必须是4~16字符！")
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用，1=启用，0=未启用
     */
    @NotNull(message = "添加管理员失败，请选择管理员的启用状态！")
    @Min(value = 0, message = "添加管理员失败，启用状态的值必须是0或1！")
    @Max(value = 1, message = "添加管理员失败，启用状态的值必须是0或1！")
    private Integer enable;

    /**
     * 管理员的角色ID的数组
     */
    @NotNull(message = "添加管理员失败，请至少选择1种角色！")
    @Size(min = 1, message = "添加管理员失败，请至少选择1种角色！")
    private Long[] roleIds;

}
