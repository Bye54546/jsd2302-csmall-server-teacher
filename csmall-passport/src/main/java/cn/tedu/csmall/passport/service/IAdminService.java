package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
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
     * 添加管理员
     *
     * @param adminAddNewParam 管理员数据
     */
    void addNew(AdminAddNewParam adminAddNewParam);

}
