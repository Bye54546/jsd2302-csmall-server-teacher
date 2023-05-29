package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryUpdateInfoParam;
import cn.tedu.csmall.product.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl() {
        log.debug("创建业务类对象：CategoryServiceImpl");
    }

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) {
        log.debug("开始处理【添加类别】的业务，参数：{}", categoryAddNewParam);
        // 应该保证此类别的名称是唯一的
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryAddNewParam.getName());
        int countByName = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别名称统计匹配的类别数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加类别失败，类别名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建Category对象
        Category category = new Category();
        // 复制属性
        BeanUtils.copyProperties(categoryAddNewParam, category);
        // 补全属性
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        // 调用Mapper对象的insert()方法执行插入
        int rows = categoryMapper.insert(category);
        if (rows != 1) {
            String message = "添加类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除类别】的业务，参数：{}", id);
        // 检查类别是否存在，如果不存在，则抛出异常
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别ID统计匹配的类别数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除类别失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 暂未检查关联数据

        int rows = categoryMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除类别失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam) {
        log.debug("开始处理【修改类别详情】的业务，参数ID：{}, 新数据：{}", id, categoryUpdateInfoParam);
        // 检查类别是否存在，如果不存在，则抛出异常
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别ID统计匹配的类别数量，结果：{}", countById);
        if (countById == 0) {
            String message = "修改类别详情失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查类别名称是否被其它类别占用，如果被占用，则抛出异常
        QueryWrapper<Category> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name", categoryUpdateInfoParam.getName())
                .ne("id", id);
        int countByName = categoryMapper.selectCount(queryWrapper2);
        log.debug("根据类别名称统计匹配的类别数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "修改类别详情失败，类别名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 执行修改
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateInfoParam, category);
        category.setId(id);
        int rows = categoryMapper.updateById(category);
        if (rows != 1) {
            String message = "修改类别详情失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("将新的类别数据更新入到数据库，完成！");
    }

}