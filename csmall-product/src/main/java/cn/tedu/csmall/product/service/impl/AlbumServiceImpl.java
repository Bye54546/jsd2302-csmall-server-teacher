package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.mapper.PictureMapper;
import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IAlbumService;
import cn.tedu.csmall.product.util.PageInfoToPageDataConverter;
import cn.tedu.csmall.product.web.ServiceCode;
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
public class AlbumServiceImpl implements IAlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public void addNew(AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的业务，参数：{}", albumAddNewParam);
        // 检查相册名称是否被占用，如果被占用，则抛出异常
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", albumAddNewParam.getName()); // name='参数中的相册名称'
        int countByName = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册名称统计匹配的相册数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加相册失败，相册名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 将相册数据写入到数据库中
        Album album = new Album();
        BeanUtils.copyProperties(albumAddNewParam, album);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());
        albumMapper.insert(album);
        log.debug("将新的相册数据插入到数据库，完成！");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除相册】的业务，参数：{}", id);
        // 检查相册是否存在，如果不存在，则抛出异常
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册ID统计匹配的相册数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除相册失败，相册数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查是否有图片关联到了此相册，如果存在，则抛出异常
        QueryWrapper<Picture> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("album_id", id);
        int countByAlbumId = pictureMapper.selectCount(queryWrapper2);
        log.debug("根据相册ID统计匹配的图片数量，结果：{}", countByAlbumId);
        if (countByAlbumId > 0) {
            String message = "删除相册失败，仍有图片关联到此相册！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 检查是否有SPU关联到了此相册，如果存在，则抛出异常

        // 检查是否有SKU关联到了此相册，如果存在，则抛出异常

        albumMapper.deleteById(id);
    }

    @Override
    public void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam) {
        log.debug("开始处理【修改相册详情】的业务，参数：{}", albumUpdateInfoParam);
        Album album = new Album();
        BeanUtils.copyProperties(albumUpdateInfoParam, album);
        album.setId(id);
        albumMapper.updateById(album);
        log.debug("将新的相册数据更新入到数据库，完成！");
    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AlbumListItemVO> list = albumMapper.list();
        PageInfo<AlbumListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AlbumListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

}
