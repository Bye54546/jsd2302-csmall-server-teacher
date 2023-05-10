package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    // http://localhost:8080/album/add-new?name=TestName001&description=TestDescription001&sort=99
    @PostMapping("/add-new")
    public String addNew(AlbumAddNewParam albumAddNewParam) {
        try {
            albumService.addNew(albumAddNewParam);
            return "添加成功！";
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Throwable e) {
            return "添加失败！出现了某种异常！";
        }
    }

}
