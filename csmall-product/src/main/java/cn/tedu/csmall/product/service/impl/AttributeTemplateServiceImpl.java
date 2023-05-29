package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import cn.tedu.csmall.product.util.PageInfoToPageDataConverter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {

    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    public AttributeTemplateServiceImpl() {
        log.info("创建业务对象：AttributeTemplateServiceImpl");
    }

    @Override
    public void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的业务，参数：{}", attributeTemplateAddNewParam);
        // 检查属性模板名称是否被占用，如果被占用，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", attributeTemplateAddNewParam.getName());
        int countByName = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板名称统计匹配的属性模板数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加属性模板失败，属性模板名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 将属性模板数据写入到数据库中
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateAddNewParam, attributeTemplate);
        attributeTemplate.setGmtCreate(LocalDateTime.now());
        attributeTemplate.setGmtModified(LocalDateTime.now());
        int rows = attributeTemplateMapper.insert(attributeTemplate);
        if (rows != 1) {
            String message = "添加属性模板失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的属性模板数据写入到数据库，完成！");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除属性模板】的业务，参数：{}", id);
        // 检查数据是否存在，如果不存在，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板ID统计匹配的属性模板数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除属性模板失败，属性模板数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 暂未检查关联数据

        int rows = attributeTemplateMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除属性模板失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改属性模板详情】的业务，参数ID：{}, 新数据：{}", id, attributeTemplateUpdateInfoParam);
        // 检查属性模板是否存在，如果不存在，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板ID统计匹配的属性模板数量，结果：{}", countById);
        if (countById == 0) {
            String message = "修改属性模板详情失败，属性模板数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查属性模板名称是否被其它属性模板占用，如果被占用，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name", attributeTemplateUpdateInfoParam.getName())
                .ne("id", id);
        int countByName = attributeTemplateMapper.selectCount(queryWrapper2);
        log.debug("根据属性模板名称统计匹配的属性模板数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "修改属性模板详情失败，属性模板名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 执行修改
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateUpdateInfoParam, attributeTemplate);
        attributeTemplate.setId(id);
        int rows = attributeTemplateMapper.updateById(attributeTemplate);
        if (rows != 1) {
            String message = "修改属性模板详情失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("将新的属性模板数据更新入到数据库，完成！");
    }

    @Override
    public AttributeTemplateStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的业务，参数：{}", id);
        AttributeTemplateStandardVO queryResult = attributeTemplateMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "查询属性模板详情失败，属性模板数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询属性模板列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AttributeTemplateListItemVO> list = attributeTemplateMapper.list();
        PageInfo<AttributeTemplateListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AttributeTemplateListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

}
