package cn.tedu.csmall.product.pojo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlbumAddNewParam implements Serializable {

    private String name;
    private String description;
    private Integer sort;

}