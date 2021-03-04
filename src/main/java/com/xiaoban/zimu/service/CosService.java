package com.xiaoban.zimu.service;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;

/**
 * @author xiaoban
 * @create 2021/03/04 - 11:18
 */
public interface CosService {
    // 方法1  将本地文件上传到 COS
    
    public Boolean upload( File file)
            throws CosClientException, CosServiceException;

    /**
     * 上传
     * @param fileName
     * @param fileBytes
     * @return
     */
    Boolean  upload(String fileName, byte[] fileBytes);

    // 方法2  输入流上传到 COS
    public Boolean upload( InputStream input,String name)
            throws CosClientException, CosServiceException;
    // 方法3  对以上两个方法的包装, 支持更细粒度的参数控制, 如 content-type,  content-disposition 等
    public Boolean upload(PutObjectRequest putObjectRequest)
            throws CosClientException, CosServiceException;
}
