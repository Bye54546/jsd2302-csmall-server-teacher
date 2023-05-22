package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 处理管理员数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据用户名查询管理员的登录信息
     *
     * @param username 用户名
     * @return 匹配的管理员的登录信息，如果没有匹配的数据，则返回null
     */
    AdminLoginInfoVO getLoginInfoByUsername(String username);

}
