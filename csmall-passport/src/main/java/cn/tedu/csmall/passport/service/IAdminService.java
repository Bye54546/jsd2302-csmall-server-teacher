package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理管理员数据的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IAdminService {

    /**
     * 管理员登录
     *
     * @param adminLoginInfoParam 封装了用户名、密码等登录相关信息的对象
     * @return 此管理员的信息对应的JWT数据
     */
    String login(AdminLoginInfoParam adminLoginInfoParam);

    /**
     * 添加管理员
     *
     * @param adminAddNewParam 管理员数据
     */
    void addNew(AdminAddNewParam adminAddNewParam);

}
