package com.yang.hdyplm.service.impl;

import com.sun.tools.internal.xjc.outline.PackageOutline;
import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.dto.PhotosDto;
import com.yang.hdyplm.mapper.PhotoMapper;
import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.Photo;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.PhotoService;
import com.yang.hdyplm.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Photo> photoList() {
        //判断是否在Redis中存在
        if (redisCache.hasKey("photoInfos")){
            List<Photo> photoInfos = redisCache.getCacheList("photoInfos");
            for (Photo photoInfo : photoInfos) {
                photoInfo.setViews(redisCache.getCacheMapValue("views", photoInfo.getId().toString()).toString());
            }
            return photoInfos;
        }
        else {
            if (photoMapper.photoList().size()!=0){
                List<Photo> photos = photoMapper.photoList();   //从MySql数据库中出查找数据
                if (!redisCache.hasKey("views")){       //先单独存储Views
                    for (Photo photoInfo : photos) {
                        redisCache.setCacheMapValue("views",photoInfo.getId().toString(),Integer.valueOf(photoInfo.getViews()));
                    }
                }
                redisCache.setCacheList("photoInfos",photos);   //存入redis中
                redisCache.setExpire("photoInfos",1,TimeUnit.HOURS);    //设置过期时间
                return photos;
            }else {
                return null;
            }
        }
    }

    @Override
    public Result uploadForm(PhotosDto photosDto) {
        Photo photo=new Photo();
        photo.setName(photosDto.getName());
        photo.setTime(photosDto.getTime());
        photo.setDesc(photosDto.getDesc());
        photo.setCoverUrl(photosDto.getCoverUrl());
        photo.setViews("0");
        String[] photoUrl = photosDto.getPhotosUrl();
        List strings=new ArrayList();
        for (int i=0;i<12;i++){
            strings.add("");
        }
        List<String> list = Arrays.asList(photoUrl);
        for (int i=1;i<=list.size();i++){
            strings.set(i-1,list.get(i-1));
        }
        photo.setReleaseUser(userMapper.getById(Math.toIntExact(BaseContext.getCurrentId())).getName());
        photo.setP1(strings.get(0).toString());
        photo.setP2(strings.get(1).toString());
        photo.setP3(strings.get(2).toString());
        photo.setP4(strings.get(3).toString());
        photo.setP5(strings.get(4).toString());
        photo.setP6(strings.get(5).toString());
        photo.setP7(strings.get(6).toString());
        photo.setP8(strings.get(7).toString());
        photo.setP9(strings.get(8).toString());
        photo.setP10(strings.get(9).toString());
        photo.setP11(strings.get(10).toString());
        photo.setP12(strings.get(11).toString());
        photoMapper.insertForm(photo); //返回主键id
        redisCache.deleteObject("photoInfos");
        redisCache.setCacheMapValue("views",photo.getId().toString(),0);
        return Result.success();
    }

    @Override
    public Result photoListById(Integer id) {
        Long views = redisCache.incrMapValue("views", id.toString(), 1);//浏览量加一
        log.info(id+":浏览量加一");
        //判断是否在Redis中存在
        if (redisCache.hasKey("photoInfos")){
            List<Photo> photoInfos = redisCache.getCacheList("photoInfos");
            for (Photo photoInfo : photoInfos) {
                if (photoInfo.getId()==id){
                    Map map=new HashMap();
                    map.put("rows",photoInfo);
                    return Result.success(map);
                }
            }
        }else {
            Photo photo = photoMapper.photoListById(id);
            Map map=new HashMap();
            map.put("rows",photo);
            return Result.success(map);
        }
        return Result.error("相册不存在");
    }

    @Override
    public Result delPhotos(Integer id) {
        Integer delPhotoBNum = photoMapper.delPhotos(id);
        redisCache.deleteObject("photoInfos");
        redisCache.delCacheMapValue("views",id.toString()); //删除该相册的views
        return Result.success("删除记录数："+delPhotoBNum);
    }

    @Override
    public Result updatePhotos(Integer id,PhotosDto photosDto) {
        Photo photo=new Photo();
        photo.setId(id);
        photo.setName(photosDto.getName());
        photo.setTime(photosDto.getTime());
        photo.setDesc(photosDto.getDesc());
        photo.setCoverUrl(photosDto.getCoverUrl());
        String[] photoUrl = photosDto.getPhotosUrl();
        List strings=new ArrayList();
        for (int i=0;i<12;i++){
            strings.add("");
        }
        List<String> list = Arrays.asList(photoUrl);
        for (int i=1;i<=list.size();i++){
            strings.set(i-1,list.get(i-1));
        }
        photo.setP1(strings.get(0).toString());
        photo.setP2(strings.get(1).toString());
        photo.setP3(strings.get(2).toString());
        photo.setP4(strings.get(3).toString());
        photo.setP5(strings.get(4).toString());
        photo.setP6(strings.get(5).toString());
        photo.setP7(strings.get(6).toString());
        photo.setP8(strings.get(7).toString());
        photo.setP9(strings.get(8).toString());
        photo.setP10(strings.get(9).toString());
        photo.setP11(strings.get(10).toString());
        photo.setP12(strings.get(11).toString());
        photoMapper.updatePhotos(photo);
        redisCache.deleteObject("photoInfos");//更新后删除redis的旧值
        return Result.success("相册更新成功");
    }
}
