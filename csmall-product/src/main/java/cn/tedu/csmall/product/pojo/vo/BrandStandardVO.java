package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class BrandStandardVO implements Serializable {

    /**
     * 数据id
     */
    private Long id;

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
     * 排序序号
     */
    private Integer sort;

    /**
     * 销量（冗余）
     */
    private Integer sales;

    /**
     * 商品种类数量总和（冗余）
     */
    private Integer productCount;

    /**
     * 买家评论数量总和（冗余）
     */
    private Integer commentCount;

    /**
     * 买家好评数量总和（冗余）
     */
    private Integer positiveCommentCount;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

}