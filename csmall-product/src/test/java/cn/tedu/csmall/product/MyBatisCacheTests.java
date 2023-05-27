package cn.tedu.csmall.product;

import cn.tedu.csmall.product.mapper.AlbumMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisCacheTests {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    void l1Cache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AlbumMapper albumMapper = sqlSession.getMapper(AlbumMapper.class);

        System.out.println("准备执行第1次查询……");
        Object queryResult1 = albumMapper.getStandardById(1L);
        System.out.println("第1次查询结束！");
        System.out.println(queryResult1);

        System.out.println("准备执行第2次查询……");
        Object queryResult2 = albumMapper.getStandardById(1L);
        System.out.println("第2次查询结束！");
        System.out.println(queryResult2);

        // 清除此前的缓存结果
        // sqlSession.clearCache();
        albumMapper.deleteById(13000000L);

        System.out.println("准备执行第3次查询……");
        Object queryResult3 = albumMapper.getStandardById(1L);
        System.out.println("第3次查询结束！");
        System.out.println(queryResult3);

        System.out.println("准备执行第4次查询……");
        Object queryResult4 = albumMapper.getStandardById(2L);
        System.out.println("第4次查询结束！");
        System.out.println(queryResult4);

        System.out.println("准备执行第5次查询……");
        Object queryResult5 = albumMapper.getStandardById(1L);
        System.out.println("第5次查询结束！");
        System.out.println(queryResult5);
    }

    @Autowired
    AlbumMapper albumMapper;

    @Test
    void l2Cache() {
        System.out.println("准备执行第1次查询……");
        Object queryResult1 = albumMapper.getStandardById(1L);
        System.out.println("第1次查询结束！");
        System.out.println(queryResult1);

        albumMapper.deleteById(12L);

        System.out.println("准备执行第2次查询……");
        Object queryResult2 = albumMapper.getStandardById(1L);
        System.out.println("第2次查询结束！");
        System.out.println(queryResult2);

        albumMapper.deleteById(12L);

        System.out.println("准备执行第3次查询……");
        Object queryResult3 = albumMapper.getStandardById(1L);
        System.out.println("第3次查询结束！");
        System.out.println(queryResult3);

        albumMapper.deleteById(12L);

        System.out.println("准备执行第4次查询……");
        Object queryResult4 = albumMapper.getStandardById(2L);
        System.out.println("第4次查询结束！");
        System.out.println(queryResult4);

        albumMapper.deleteById(12L);

        System.out.println("准备执行第5次查询……");
        Object queryResult5 = albumMapper.getStandardById(1L);
        System.out.println("第5次查询结束！");
        System.out.println(queryResult5);
    }

}
