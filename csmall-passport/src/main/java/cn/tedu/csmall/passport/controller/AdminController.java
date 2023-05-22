package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 处理管理员相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
@Api(tags = "01. 管理员管理模块")
public class AdminController {

    @Autowired
    private IAdminService albumService;

    // http://localhost:8080/admin/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AdminAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加管理员】的请求，参数：{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

}
