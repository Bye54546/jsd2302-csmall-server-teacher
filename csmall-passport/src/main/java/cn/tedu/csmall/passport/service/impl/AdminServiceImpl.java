package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AdminServiceImpl() {
        log.debug("创建业务类对象：AdminServiceImpl");
    }

    @Override
    public void login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的业务，参数：{}", adminLoginInfoParam);
        // 创建认证时所需的参数对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginInfoParam.getUsername(),
                adminLoginInfoParam.getPassword());
        // 执行认证，并获取认证结果
        Authentication authenticateResult
                = authenticationManager.authenticate(authentication);
        log.debug("验证登录完成！");

        // 将认证结果存入到SecurityContext中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticateResult);
    }

    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewParam);
        // 检查管理员用户名是否被占用，如果被占用，则抛出异常
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminAddNewParam.getUsername());
        int countByUsername = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员用户名统计匹配的管理员数量，结果：{}", countByUsername);
        if (countByUsername > 0) {
            String message = "添加管理员失败，用户名已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // TODO 检查管理员手机号码是否被占用，如果被占用，则抛出异常
        // TODO 检查管理员电子邮箱是否被占用，如果被占用，则抛出异常

        // 将管理员数据写入到数据库中
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewParam, admin);
        admin.setLastLoginIp(null);
        admin.setLoginCount(0);
        admin.setGmtLastLogin(null);
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员数据插入到数据库，完成！");

        // 将管理员与角色的关联数据写入到数据库中
        Long[] roleIds = adminAddNewParam.getRoleIds();
        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < adminRoleList.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleIds[i]);
            adminRole.setGmtCreate(now);
            adminRole.setGmtModified(now);
            adminRoleList[i] = adminRole;
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        if (rows != adminRoleList.length) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员与角色的关联数据插入到数据库，完成！");
    }

}