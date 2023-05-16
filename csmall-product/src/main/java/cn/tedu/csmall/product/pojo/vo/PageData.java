package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
public class PageData<T> implements Serializable {

    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 最大页码
     */
    private Integer maxPage;
    /**
     * 每页记录数据
     */
    private Integer pageSize;
    /**
     * 记录总数
     */
    private Long total;
    /**
     * 列表数据
     */
    private List<T> list;

}
