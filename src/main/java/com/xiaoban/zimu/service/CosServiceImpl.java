package com.xiaoban.zimu.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.xiaoban.zimu.config.CosConfigProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author xiaoban
 * @create 2021/03/04 - 11:18
 */
@Service
@Slf4j
public class CosServiceImpl implements CosService {

    private final COSClient cosClient;
    private final CosConfigProperty config;

    public CosServiceImpl(COSClient cosClient, CosConfigProperty config) {
        this.cosClient = cosClient;
        this.config = config;
    }

    @Override
    public Boolean upload( File file) throws CosClientException, CosServiceException {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        // 方法1 本地文件上传

        String key = UUID.randomUUID().toString().substring(0,2) +"-" +  file.getName();
        PutObjectResult putObjectResult = cosClient.putObject(config.getBucket(), key, file);
        // 获取文件的 etag
        String etag = putObjectResult.getETag();
        return etag!=null&&!"".equals(etag);
    }


    /**
     * 向腾讯云上传文件
     *
     * @param fileBytes  文件的字节数组
     * @param fileName   文件的名字
     */
    @Override
    public Boolean  upload(String fileName, byte[] fileBytes) {
        ByteArrayInputStream input = null;
        try {
            input = new ByteArrayInputStream(fileBytes);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度
            objectMetadata.setContentLength(input.available());
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
            PutObjectResult putObjectResult = cosClient.putObject(config.getBucket(), fileName, input, objectMetadata);
            // 获取文件的 etag
            String etag = putObjectResult.getETag();
            return etag!=null&&!"".equals(etag);
        } catch (CosServiceException serviceExp) {
            log.error("上传文件失败", serviceExp);
            throw serviceExp;
        } catch (CosClientException e) {
            log.error("上传文件失败", e);
        } finally {
            //关闭网络连接
            if (cosClient != null) {
                cosClient.shutdown();
            }
        }
        return null;
    }

    @Override
    public Boolean upload(InputStream input,String name) throws CosClientException, CosServiceException {
        // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置输入流长度为500
        objectMetadata.setContentLength(500);
        // 设置 Content type, 默认是 application/octet-stream
        objectMetadata.setContentType("application/octet-stream");
        PutObjectResult putObjectResult = cosClient.putObject(config.getBucket(),name , input, objectMetadata);
        String etag = putObjectResult.getETag();
        return etag!=null&&!"".equals(etag);
    }

    @Override
    public Boolean upload(PutObjectRequest putObjectRequest) throws CosClientException, CosServiceException {
        return false;
    }
}
