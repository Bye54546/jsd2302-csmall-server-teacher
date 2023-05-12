package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.service.IAlbumService;
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

    // http://localhost:8080/album/delete?id=1
    @PostMapping("/delete")
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "albumId", value = "相册ID", required = true, dataType = "long")
    })
    public String delete(@Range(min = 1, message = "根据ID删除相册失败，请提交合法的ID值！")
                         @RequestParam Long albumId) throws Exception {
        "".substring(1000000);
        return null;
    }

    @PostMapping("/update")
    @ApiOperation("修改相册")
    @ApiOperationSupport(order = 300)
    public String update() {
        throw new NullPointerException("修改出错了，导致了空指针异常！");
    }

    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 300)
    public String list() {
        throw new RuntimeException("查询出错了，导致了RuntimeException！");
    }

}
