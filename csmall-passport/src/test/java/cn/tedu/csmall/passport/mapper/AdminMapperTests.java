package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AdminMapperTests {

    @Autowired
    AdminMapper mapper;

    @Test
    void insert() {
        Admin admin = new Admin();
        admin.setUsername("测试用户名001");
        admin.setPassword("测试密码001");
        admin.setDescription("测试简介001");
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + admin);
        int rows = mapper.insert(admin);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + admin);
    }

}
