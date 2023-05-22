package cn.tedu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SPU（Standard Product Unit）
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
@TableName("pms_spu")
public class Spu implements Serializable {

    /**
     * 数据id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * SPU名称
     */
    private String name;

    /**
     * SPU编号
     */
    private String typeNumber;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 价格（显示在列表中）
     */
    private BigDecimal listPrice;

    /**
     * 当前库存（冗余）
     */
    private Integer stock;

    /**
     * 库存预警阈值（冗余）
     */
    private Integer stockThreshold;

    /**
     * 计件单位
     */
    private String unit;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名称（冗余）
     */
    private String brandName;

    /**
     * 类别id
     */
    private Long categoryId;

    /**
     * 类别名称（冗余）
     */
    private String categoryName;

    /**
     * 属性模板id
     */
    private Long attributeTemplateId;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 组图URLs，使⽤JSON格式表示
     */
    private String pictures;

    /**
     * 关键词列表，各关键词使⽤英⽂的逗号分隔
     */
    private String keywords;

    /**
     * 标签列表，各标签使⽤英⽂的逗号分隔，原则上最多3个
     */
    private String tags;

    /**
     * 销量（冗余）
     */
    private Integer sales;

    /**
     * 买家评论数量总和（冗余）
     */
    private Integer commentCount;

    /**
     * 买家好评数量总和（冗余）
     */
    private Integer positiveCommentCount;

    /**
     * 排序序号
     */
    private Integer sort;

    /**
     * 是否标记为删除，1=已删除，0=未删除
     */
    private Integer isDeleted;

    /**
     * 是否上架（发布），1=已上架，0=未上架 （下架）
     */
    private Integer isPublished;

    /**
     * 是否新品，1=新品，0=非新品
     */
    private Integer isNewArrival;

    /**
     * 是否推荐，1=推荐，0=不推荐
     */
    private Integer isRecommend;

    /**
     * 是否已审核，1=已审核，0=未审核
     */
    private Integer isChecked;

    /**
     * 审核⼈（冗余）
     */
    private String checkUser;

    /**
     * 审核通过时间（冗余）
     */
    private LocalDateTime gmtCheck;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}