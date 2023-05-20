package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    List<RoleListItemVO> list();

}
