package cn.tedu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
@TableName("pms_attribute")
public class Attribute implements Serializable {

    /**
     * 数据id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属属性模板id
     */
    private Long templateId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 简介（某些属性名称可能相同，通过简介补充描述）
     */
    private String description;

    /**
     * 属性类型，1=销售属性，0=非销售属性
     */
    private Integer type;

    /**
     * 输入类型，0=手动录入，1=单选，2=多选， 3=单选（下拉列表），4=多选（下拉列表）
     */
    private Integer inputType;

    /**
     * 备选值列表
     */
    private String valueList;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 排序序号
     */
    private Integer sort;

    /**
     * 是否允许自定义，1=允许，0=禁止
     */
    private Integer isAllowCustomize;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}