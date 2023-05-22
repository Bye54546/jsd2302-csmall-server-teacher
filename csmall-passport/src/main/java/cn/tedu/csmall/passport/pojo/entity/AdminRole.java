package cn.tedu.csmall.passport.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员与角色的关联
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
@TableName("ams_admin_role")
public class AdminRole implements Serializable {

    /**
     * 数据id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 管理员id
     */
    private Long adminId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}
