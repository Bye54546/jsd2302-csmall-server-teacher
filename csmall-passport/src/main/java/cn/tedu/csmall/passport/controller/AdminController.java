package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.commons.security.LoginPrincipal;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.service.IAdminService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 处理管理员相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = "application/json; charset=utf-8")
@Validated
@Api(tags = "01. 管理员管理模块")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    // http://localhost:9181/admin/login
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiOperationSupport(order = 50)
    public JsonResult login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的请求，参数：{}", adminLoginInfoParam);
        String jwt = adminService.login(adminLoginInfoParam);
        return JsonResult.ok(jwt);
    }

    // http://localhost:9181/admin/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AdminAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加管理员】的请求，参数：{}", albumAddNewParam);
        adminService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9081/admin/list
    @GetMapping("/list")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 420)
    public JsonResult list(@ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        log.debug("当事人ID：{}", loginPrincipal.getId());
        log.debug("当事人用户名：{}", loginPrincipal.getUsername());
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN, "此功能尚未开发！");
    }

}
