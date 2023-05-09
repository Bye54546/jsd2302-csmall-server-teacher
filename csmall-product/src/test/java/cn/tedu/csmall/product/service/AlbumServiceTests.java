package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlbumServiceTests {

    @Autowired
    IAlbumService service;

    @Test
    void addNew() {
        AlbumAddNewParam albumAddNewParam = new AlbumAddNewParam();
        albumAddNewParam.setName("测试数据-00002");
        albumAddNewParam.setDescription("测试数据简介-00002");
        albumAddNewParam.setSort(99);

        try {
            service.addNew(albumAddNewParam);
            System.out.println("添加成功！");
        } catch (RuntimeException e) {
            System.out.println("添加失败！");
            e.printStackTrace();
        }
    }

}
