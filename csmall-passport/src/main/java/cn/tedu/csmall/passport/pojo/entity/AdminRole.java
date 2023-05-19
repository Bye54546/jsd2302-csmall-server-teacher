package cn.tedu.csmall.passport.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("ams_admin_role")
public class AdminRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long adminId;
    private Long roleId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
