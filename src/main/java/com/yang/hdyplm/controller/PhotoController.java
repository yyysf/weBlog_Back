package com.yang.hdyplm.controller;

import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.dto.PhotosDto;
import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.Photo;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("/hdyplm/photo")
public class PhotoController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PhotoService photoService;
    @GetMapping("/photoList")
    public Result photoList(){
        List<Photo> photos = photoService.photoList();
        Map data=new HashMap();
        data.put("rows",photos);
        return Result.success(data);
    }
    @PostMapping("/uploadPhotoForm")
    public Result uploadPhotoForm(@RequestBody PhotosDto photosDto){
        Result result = photoService.uploadForm(photosDto);
        return result;
    }
    @GetMapping("/photoContents/{id}")
    public Result photoContentsById(@PathVariable Integer id){
        Result result = photoService.photoListById(id);
        return result;
    }
    @PostMapping("/delPhotos/{id}")
    public Result delPhotos(@PathVariable Integer id){
        Result result = photoService.delPhotos(id);
        return result;
    }
    @PutMapping("/updatePhotos/{id}")
    public Result updatePhotos(@PathVariable Integer id,@RequestBody PhotosDto photosDto){
        Result result = photoService.updatePhotos(id,photosDto);
        return result;
    }
}
