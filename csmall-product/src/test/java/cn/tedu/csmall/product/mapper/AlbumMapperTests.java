package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试数据0014");
        album.setDescription("测试数据简介0014");
        album.setSort(255);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());

        mapper.insert(album);
        System.out.println("插入数据完成");
    }

}
