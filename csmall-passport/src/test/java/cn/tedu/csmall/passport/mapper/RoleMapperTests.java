package cn.tedu.csmall.passport.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoleMapperTests {

    @Autowired
    RoleMapper mapper;

    @Test
    void list() {
        List<?> list = mapper.list();
        System.out.println("查询列表完成，结果集中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

}
