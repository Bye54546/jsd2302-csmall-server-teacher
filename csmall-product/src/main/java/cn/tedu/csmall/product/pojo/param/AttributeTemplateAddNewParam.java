package cn.tedu.csmall.product.pojo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeTemplateAddNewParam implements Serializable {

    private String name;
    private String pinyin;
    private String keywords;
    private Integer sort;

}
