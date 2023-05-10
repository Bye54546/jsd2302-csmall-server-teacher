package cn.tedu.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Slf4jTests {

    @Test
    void test() {
        // 【输出目标】x = 1, y = 2, x + y = 3
        int x = 1;
        int y = 2;
        System.out.println("x = " + x + ", y = " + y + ", x + y = " + (x + y));

        log.trace("这是一条【TRACE】级别的日志");
        log.debug("这是一条【DEBUG】级别的日志");
        log.info("这是一条【INFO】级别的日志");
        log.warn("这是一条【WARN】级别的日志");
        log.error("这是一条【ERROR】级别的日志");

        log.info("x = {}, y = {}, x + y = {}", x, y, x + y);
    }

}
