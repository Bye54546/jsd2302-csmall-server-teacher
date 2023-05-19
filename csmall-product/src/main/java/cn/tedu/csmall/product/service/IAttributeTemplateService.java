package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAttributeTemplateService {

    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

}