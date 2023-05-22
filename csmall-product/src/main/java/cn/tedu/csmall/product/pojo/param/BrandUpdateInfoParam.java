package cn.tedu.csmall.product.pojo.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 更新品牌基本信息的参数类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
public class BrandUpdateInfoParam implements Serializable {

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌名称的拼音
     */
    private String pinyin;

    /**
     * 品牌logo的URL
     */
    private String logo;

    /**
     * 品牌简介
     */
    private String description;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 自定义排序序号
     */
    private Integer sort;

}
