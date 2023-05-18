package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试数据0100");
        album.setDescription("测试数据简介0100");
        album.setSort(100);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + album);
        int rows = mapper.insert(album);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + album);
    }

    @Test
    void updateById() {
        Album album = new Album();
        album.setId(1L);
        album.setName("Redmi Note 11 5G的相册");
        // album.setDescription("这家伙很懒，啥也没写");
        // album.setSort(199);
        // album.setGmtModified(LocalDateTime.now());

        int rows = mapper.updateById(album);
        System.out.println("修改数据完成，受影响的行数：" + rows);
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        System.out.println("根据【ID=" + id + "】查询数据完成，结果：" + queryResult);
    }

    @Test
    void list() {
        List<?> list = mapper.list();
        System.out.println("查询列表完成，结果集中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

}
