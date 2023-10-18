package com.yang.hdyplm.service;

import com.yang.hdyplm.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICosFileService {
    Result uploadCover(MultipartFile multipartFile);
    Result uploadContent(MultipartFile[] multipartFile);
}
