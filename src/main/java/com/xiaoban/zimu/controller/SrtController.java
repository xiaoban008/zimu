package com.xiaoban.zimu.controller;

import com.xiaoban.zimu.SrtService;
import com.xiaoban.zimu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaoban
 * @create 2021/03/02 - 20:25
 */
@RestController
@Slf4j
@RequestMapping("/trade")
public class SrtController {

    private final SrtService srtService;

    public SrtController(SrtService srtService) {
        this.srtService = srtService;
    }

    @PostMapping("/srt")
    public Result audioToCaption(MultipartFile file){
        log.info("文件是{}",file.getName());
        srtService.audioToCaption(file);
        return new Result();
    }

}
