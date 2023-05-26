package cn.tedu.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CsmallPassportApplicationTests {

    @Autowired(required = false)
    IUserService userService;

    @Test
    void contextLoads() {
        if (userService != null) {
            userService.toString();
        }
    }

    @Autowired
    DataSource dataSource;

    @Test
    void getConnection() throws Throwable {
        dataSource.getConnection();
    }

}
