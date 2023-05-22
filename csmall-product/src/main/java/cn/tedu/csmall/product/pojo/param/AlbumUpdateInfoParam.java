package cn.tedu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改相册数据的参数类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
public class AlbumUpdateInfoParam implements Serializable {

    @NotNull(message = "修改相册详情失败，必须提交相册名称！")
    @ApiModelProperty(value = "相册名称", required = true, example = "可乐的相册")
    private String name;

    @NotNull(message = "修改相册详情失败，必须提交相册简介！")
    @ApiModelProperty(value = "相册简介", required = true, example = "可乐的相册的简介")
    private String description;

    @NotNull(message = "修改相册详情失败，必须提交排序序号！")
    @Range(max = 99, message = "修改相册详情失败，排序序号值必须0~99之间！")
    @ApiModelProperty(value = "排序序号，必须是0~255之间的数字", required = true, example = "97")
    private Integer sort;

}