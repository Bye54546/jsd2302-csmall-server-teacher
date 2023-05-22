package cn.tedu.csmall.product.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 相册数据的列表项VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AlbumListItemVO implements Serializable {

    /**
     * 数据id
     */
    @ApiModelProperty("数据ID")
    private Long id;

    /**
     * 相册名称
     */
    @ApiModelProperty("相册名称")
    private String name;

    /**
     * 相册简介
     */
    @ApiModelProperty("相册简介")
    private String description;

    /**
     * 排序序号
     */
    @ApiModelProperty("排序序号")
    private Integer sort;

}