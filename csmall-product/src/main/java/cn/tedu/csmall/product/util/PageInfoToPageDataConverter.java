package cn.tedu.csmall.product.util;

import cn.tedu.csmall.product.pojo.vo.PageData;
import com.github.pagehelper.PageInfo;

/**
 * 分页信息的转换器，因为PageHelper框架的PageInfo中的部分属性不需要使用，则使用此类将PageInfo类型转换成自定义的PageData类型
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public class PageInfoToPageDataConverter {

    /**
     * 将PageInfo类型转换成自定义的PageData类型
     *
     * @param pageInfo PageInfo对象
     * @param <T>      数据列表中的元素类型
     * @return 自定义的PageData类型的对象
     */
    public static <T> PageData<T> convert(PageInfo<T> pageInfo) {
        PageData<T> pageData = new PageData<T>()
                .setCurrentPage(pageInfo.getPageNum())
                .setMaxPage(pageInfo.getPages())
                .setPageSize(pageInfo.getPageSize())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return pageData;
    }

}
