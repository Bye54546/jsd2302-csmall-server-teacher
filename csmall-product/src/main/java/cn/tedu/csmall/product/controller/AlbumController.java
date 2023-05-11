package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.service.IAlbumService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
@Api(tags = "04. 相册管理模块")
@Slf4j
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @ExceptionHandler
    public String handleServiceException(ServiceException e) {
        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        return e.getMessage();
    }

    // http://localhost:8080/album/add-new?name=TestName001&description=TestDescription001&sort=99
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public String addNew(AlbumAddNewParam albumAddNewParam) {
        albumService.addNew(albumAddNewParam);
        return "添加成功！";
    }

    // http://localhost:8080/album/delete?id=1
    @PostMapping("/delete")
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "albumId", value = "相册ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long")
    })
    public String delete(Long albumId, Long userId) {
        throw new ServiceException("别急，还没做！");
    }

}
