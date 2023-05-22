package cn.tedu.csmall.product.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@MapperScan("cn.tedu.csmall.product.mapper")
public class MyBatisConfiguration {

    public MyBatisConfiguration() {
        log.debug("创建配置类对象：MyBatisConfiguration");
    }

}
