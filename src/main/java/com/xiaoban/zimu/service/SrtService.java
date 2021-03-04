package com.xiaoban.zimu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaoban
 * @create 2021/03/02 - 20:22
 */
public interface SrtService {
    /**
     * 音频转字幕
     * @param file 文件
     */
    void audioToCaption(MultipartFile file);
}
