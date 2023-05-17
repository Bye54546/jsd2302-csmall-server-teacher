package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/album")
@Api(tags = "04. 相册管理模块")
@Slf4j
@Validated
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    // http://localhost:8080/album/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:8080/album/delete
    @PostMapping("/delete")
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long")
    })
    public JsonResult delete(@Range(min = 1, message = "根据ID删除相册失败，请提交合法的ID值！")
                             @RequestParam Long id) {
        log.debug("开始处理【根据ID删除相册】的请求，参数：{}", id);
        albumService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:8080/album/update
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

    // http://localhost:8080/album/list
    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询相册列表失败，请提供正确的页码值！") Integer page) {
        log.debug("开始处理【查询相册列表】的请求，页码：{}", page);
        if (page == null || page < 1) {
            page = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.list(page);
        return JsonResult.ok(pageData);
    }

}
