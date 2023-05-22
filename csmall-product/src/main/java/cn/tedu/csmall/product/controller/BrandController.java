package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.BrandUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IBrandService;
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

/**
 * 处理品牌相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/brand")
@Validated
@Api(tags = "02. 品牌管理模块")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    public BrandController() {
        log.info("创建控制器对象：BrandController");
    }

    // http://localhost:9180/brand/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加品牌")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌】的请求，参数：{}", brandAddNewParam);
        brandService.addNew(brandAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/brand/delete
    @PostMapping("/delete")
    @ApiOperation("根据ID删除品牌")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult delete(@Range(min = 1, message = "请提交有效的品牌ID值！") Long id) {
        log.debug("开始处理【根据ID删除品牌】的请求，参数：{}", id);
        brandService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/brand/update
    @PostMapping("/update")
    @ApiOperation("修改品牌详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult updateInfoById(@RequestParam @Range(min = 1, message = "请提交有效的品牌ID值！") Long id,
                                     @Valid BrandUpdateInfoParam brandUpdateInfoParam) {
        log.debug("开始处理【修改品牌详情】的业务，参数：{}", brandUpdateInfoParam);
        brandService.updateInfoById(id, brandUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/brand/standard
    @GetMapping("/standard")
    @ApiOperation("根据ID查询品牌详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@Range(min = 1, message = "根据ID查询品牌详情失败，请提交合法的ID值！")
                                      @RequestParam Long id) {
        log.debug("开始处理【根据ID查询品牌详情】的请求，参数：{}", id);
        BrandStandardVO result = brandService.getStandardById(id);
        return JsonResult.ok(result);
    }

    // http://localhost:9180/brand/list
    @GetMapping("/list")
    @ApiOperation("查询品牌列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询品牌列表失败，请提供正确的页码值！") Integer page) {
        log.debug("开始处理【查询品牌列表】的请求，页码：{}", page);
        if (page == null || page < 1) {
            page = 1;
        }
        PageData<BrandListItemVO> pageData = brandService.list(page);
        return JsonResult.ok(pageData);
    }

}