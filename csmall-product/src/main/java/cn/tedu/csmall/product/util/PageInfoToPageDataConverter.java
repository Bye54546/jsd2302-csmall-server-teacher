package cn.tedu.csmall.product.util;

import cn.tedu.csmall.product.pojo.vo.PageData;
import com.github.pagehelper.PageInfo;

public class PageInfoToPageDataConverter {

    public static <T> PageData<T> convert(PageInfo<T> pageInfo) {
        PageData<T> pageData = new PageData<>();
        pageData.setPageSize(pageInfo.getPageSize());
        pageData.setMaxPage(pageInfo.getPages());
        pageData.setCurrentPage(pageInfo.getPageNum());
        pageData.setTotal(pageInfo.getTotal());
        pageData.setList(pageInfo.getList());
        return pageData;
    }

}
