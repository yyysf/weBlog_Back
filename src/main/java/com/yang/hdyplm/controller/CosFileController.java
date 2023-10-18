package com.yang.hdyplm.controller;

import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.impl.CosFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CosFileController {
    @Autowired
    private CosFileServiceImpl cosFileService;
    @PostMapping("hdyplm/cosCover")
    public Result upCosFileCover(@RequestParam MultipartFile file){
        Result upload = cosFileService.uploadCover(file);
        return upload;
    }
    @PostMapping("hdyplm/cosContent")
    public Result upCosFileCContent(@RequestParam MultipartFile[] file){
        Result upload = cosFileService.uploadContent(file);
        return upload;
    }
}
