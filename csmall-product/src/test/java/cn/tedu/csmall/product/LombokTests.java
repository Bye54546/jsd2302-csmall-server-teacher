package cn.tedu.csmall.product;

import cn.tedu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;

public class LombokTests {

    @Test
    void testData() {
        Album album = new Album();
        album.setId(1L);
        album.setName("测试相册-001");

        System.out.println(album.getId());
        System.out.println(album.getName());

        System.out.println(album);
    }

}
