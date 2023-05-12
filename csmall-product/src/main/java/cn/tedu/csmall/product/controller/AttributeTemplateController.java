package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attribute-template")
@Api(tags = "06. 属性模板管理模块")
public class AttributeTemplateController {

    @Autowired
    private IAttributeTemplateService albumService;

    // http://localhost:8080/attribute-template/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        albumService.addNew(attributeTemplateAddNewParam);
        return JsonResult.ok();
    }

}
