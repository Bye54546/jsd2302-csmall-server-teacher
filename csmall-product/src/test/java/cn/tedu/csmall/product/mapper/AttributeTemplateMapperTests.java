package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AttributeTemplateMapperTests {

    @Autowired
    AttributeTemplateMapper mapper;

    @Test
    void insert() {
        AttributeTemplate newData = new AttributeTemplate();
        newData.setName("测试数据0100");
        newData.setPinyin("ceshishuju0100");
        newData.setKeywords("关键词1,关键词2,关键词3");
        newData.setSort(100);
        newData.setGmtCreate(LocalDateTime.now());
        newData.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + newData);
        int rows = mapper.insert(newData);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + newData);
    }

}
