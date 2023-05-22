package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 处理角色数据的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IRoleService {

    /**
     * 查询角色列表，将使用默认的每页记录数
     *
     * @return 角色列表
     */
    List<RoleListItemVO> list();

}
