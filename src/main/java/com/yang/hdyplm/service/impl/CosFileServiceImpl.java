package com.yang.hdyplm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.PutObjectRequest;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.ICosFileService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CosFileServiceImpl implements ICosFileService {
 
 
    @Value("${spring.tengxun.SecretId}")
    private String secretId;
    @Value("${spring.tengxun.SecretKey}")
    private String secretKey;
 
   @Value("${spring.tengxun.region}")
    private String region;
 
    @Value("${spring.tengxun.bucketName}")
    private String bucketName;
 
    @Value("${spring.tengxun.url}")
    private String path;
 
    @Autowired
    private COSClient cosClient;

    public Result uploadCover(MultipartFile file) {  //接收一个上传的文件
        Map map=new HashMap();
        try {
            String originalfileName = file.getOriginalFilename();   //获取文件的原始文件名

            // 获得文件流
            InputStream inputStream = file.getInputStream();    //获取文件流

            //设置文件key
            String filePath = getFileKey(originalfileName); //获得文件存储路径

            // 上传文件
            cosClient.putObject(new PutObjectRequest(bucketName, filePath, inputStream, null));
            cosClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);//将文件设置为公共可读
            String url = path + "/" + filePath;
            map.put("fileName", originalfileName);
            map.put("url", url);
            return Result.success(map,"相册封面上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return Result.error("相册封面上传失败");
    }
    public Result uploadContent(MultipartFile[] file) {  //接收一个上传的文件
            Map mapEnd=new HashMap();
            List urlLists=new ArrayList();
            try {
                for (int i=0;i<file.length;i++) {
                    Map map=new HashMap();
                String originalfileName = file[i].getOriginalFilename();   //获取文件的原始文件名

                // 获得文件流
                InputStream inputStream = file[i].getInputStream();    //获取文件流

                //设置文件key
                String filePath = getFileKey(originalfileName); //获得文件存储路径

                // 上传文件
                cosClient.putObject(new PutObjectRequest(bucketName, filePath, inputStream, null));
                cosClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);//将文件设置为公共可读
                String url = path + "/" + filePath;
                urlLists.add(url);
                map.put("fileName", originalfileName);
                map.put("url", url);
                mapEnd.put("file:"+i,map);}
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("相册照片上传失败");
            } finally {
                cosClient.shutdown();
            }
            mapEnd.put("urlLists",urlLists);
            return Result.success(mapEnd,"相册照片上传成功");
        }
        //TODO:Java操作从COS对象中删除照片以后再做
   /* public Result delCos(){       //删除照片

    }*/
    /**
     * 生成文件路径
     *
     * @return
     */
    private String getFileKey(String originalfileName) {
        String filePath = "img/cover/";
        //1.获取后缀名 2.去除文件后缀 替换所有特殊字符
        String fileType = originalfileName.substring(originalfileName.lastIndexOf("."));
        String fileStr = StrUtil.removeSuffix(originalfileName, fileType).replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "_");
        filePath +=  new DateTime().toString("yyyyMMddHHmmss") + "_" + fileStr + fileType;
        return filePath;
    }
}
