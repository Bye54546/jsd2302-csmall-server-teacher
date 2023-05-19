package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    // insert into ams_admin_role
    //      (admin_id, role_id, gmt_create, gmt_modified)
    // values
    //      (?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)
    //
    // List<AdminRole>
    // AdminRole[]
    // AdminRole...
    int insertBatch(AdminRole[] adminRoleList);

}
