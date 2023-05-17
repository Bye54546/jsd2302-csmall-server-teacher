package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import cn.tedu.csmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {

    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    @Override
    public void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的业务，参数：{}", attributeTemplateAddNewParam);
        // 检查属性模板名称是否被占用，如果被占用，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", attributeTemplateAddNewParam.getName()); // name='参数中的属性模板名称'
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
        attributeTemplateMapper.insert(attributeTemplate);
        log.debug("将新的属性模板数据写入到数据库，完成！");
    }

}
