package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AlbumListItemVO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer sort;
}
