package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import cn.tedu.csmall.commons.web.JsonResult;
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

/**
 * 处理属性模板相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/attribute-template")
@Validated
@Api(tags = "06. 属性模板管理模块")
public class AttributeTemplateController {

    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    public AttributeTemplateController() {
        log.info("创建控制器对象：AttributeTemplateController");
    }

    // http://localhost:9180/attribute-template/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的请求，参数：{}", attributeTemplateAddNewParam);
        attributeTemplateService.addNew(attributeTemplateAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/attribute-template/delete
    @PostMapping("/delete")
    @ApiOperation("根据ID删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult delete(@Range(min = 1, message = "请提交有效的属性模板ID值！") Long id) {
        log.debug("开始处理【根据ID删除属性模板】的请求，参数：{}", id);
        attributeTemplateService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/attribute-template/update
    @PostMapping("/update")
    @ApiOperation("修改属性模板详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult updateInfoById(@RequestParam @Range(min = 1, message = "请提交有效的属性模板ID值！") Long id,
                                     @Valid AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改属性模板详情】的业务，参数：{}", attributeTemplateUpdateInfoParam);
        attributeTemplateService.updateInfoById(id, attributeTemplateUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/attribute-template/standard
    @GetMapping("/standard")
    @ApiOperation("根据ID查询属性模板详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@Range(min = 1, message = "根据ID查询属性模板详情失败，请提交合法的ID值！")
                                      @RequestParam Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的请求，参数：{}", id);
        AttributeTemplateStandardVO result = attributeTemplateService.getStandardById(id);
        return JsonResult.ok(result);
    }

    // http://localhost:9180/attribute-template/list
    @GetMapping("/list")
    @ApiOperation("查询属性模板列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询属性模板列表失败，请提供正确的页码值！") Integer page) {
        log.debug("开始处理【查询属性模板列表】的请求，页码：{}", page);
        if (page == null || page < 1) {
            page = 1;
        }
        PageData<AttributeTemplateListItemVO> pageData = attributeTemplateService.list(page);
        return JsonResult.ok(pageData);
    }

}