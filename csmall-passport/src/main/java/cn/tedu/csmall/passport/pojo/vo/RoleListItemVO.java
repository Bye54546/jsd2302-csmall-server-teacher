package cn.tedu.csmall.passport.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RoleListItemVO implements Serializable {

    private Long id;
    private String name;

}
