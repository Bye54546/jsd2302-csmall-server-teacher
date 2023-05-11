package cn.tedu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AlbumAddNewParam implements Serializable {

    @NotNull(message = "必须提交相册名称")
    @ApiModelProperty(value = "相册名称", required = true, example = "可乐的相册")
    private String name;

    @NotNull(message = "必须提交相册简介")
    @ApiModelProperty(value = "相册简介", required = true, example = "可乐的相册的简介")
    private String description;

    @ApiModelProperty(value = "排序序号，必须是0~255之间的数字", required = true, example = "97")
    private Integer sort;

}