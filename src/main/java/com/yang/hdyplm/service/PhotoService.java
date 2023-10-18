package com.yang.hdyplm.service;

import com.yang.hdyplm.dto.PhotosDto;
import com.yang.hdyplm.pojo.Photo;
import com.yang.hdyplm.result.Result;

import java.util.List;

public interface PhotoService {
    List<Photo> photoList();

    Result uploadForm(PhotosDto photosDto);

    Result photoListById(Integer id);

    Result delPhotos(Integer id);

    Result updatePhotos(Integer id,PhotosDto photosDto);
}
