package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.security.LoginPrincipal;
import cn.tedu.csmall.product.service.IAlbumService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 处理相册相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/album")
@Validated
@Api(tags = "04. 相册管理模块")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    public AlbumController() {
        log.debug("创建控制器类对象：AlbumController");
    }

    // http://localhost:9180/album/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/album/9527/delete
    @PostMapping("/{id}/delete")
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long")
    })
    public JsonResult delete(@Range(min = 1, message = "根据ID删除相册失败，请提交合法的ID值！")
                             @PathVariable Long id) {
        log.debug("开始处理【根据ID删除相册】的请求，参数：{}", id);
        albumService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/album/update
    @PostMapping("/update")
    @ApiOperation("修改相册详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long")
    })
    public JsonResult updateInfoById(@RequestParam @Range(min = 1, message = "请提交有效的相册ID值！") Long id,
                                     @Valid AlbumUpdateInfoParam albumUpdateInfoParam) {
        log.debug("开始处理【修改相册详情】的业务，参数：{}", albumUpdateInfoParam);
        albumService.updateInfoById(id, albumUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/album/standard
    @GetMapping("/standard")
    @ApiOperation("根据ID查询相册详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@Range(min = 1, message = "根据ID查询相册详情失败，请提交合法的ID值！")
                                      @RequestParam Long id) {
        log.debug("开始处理【根据ID查询相册详情】的请求，参数：{}", id);
        AlbumStandardVO result = albumService.getStandardById(id);
        return JsonResult.ok(result);
    }

    // http://localhost:9180/album/list
    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询相册列表失败，请提供正确的页码值！") Integer page,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        log.debug("开始处理【查询相册列表】的请求，页码：{}", page);
        log.debug("当事人：{}", loginPrincipal);
        if (page == null || page < 1) {
            page = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.list(page);
        return JsonResult.ok(pageData);
    }

}
