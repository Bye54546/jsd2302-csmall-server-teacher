package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 处理管理员与角色的关联数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 批量插入管理员与角色的关联数据
     *
     * @param adminRoleList 若干个管理员与角色的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(AdminRole[] adminRoleList);

}
