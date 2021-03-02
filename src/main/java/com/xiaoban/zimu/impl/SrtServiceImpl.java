package com.xiaoban.zimu.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;
import com.xiaoban.zimu.SrtService;
import com.xiaoban.zimu.model.MsgResult;
import com.xiaoban.zimu.model.SrtConfig;
import com.xiaoban.zimu.model.SrtEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoban
 * @create 2021/03/02 - 20:23
 */
@Service
@Slf4j
public class SrtServiceImpl implements SrtService {

    @Value("${xiaoban.tmp}")
    private String tmp;

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    private static final String APP_ID = "60051ae2";
    private static final String SECRET_KEY = "19d43dac030203583ca100c4169abff6";

    private static final SrtConfig srtConfig = new SrtConfig(APP_ID,SECRET_KEY);

    //音频文件路径
    //1、绝对路径：D:\......\demo-3.0\src\main\resources\audio\lfasr.wav
    //2、相对路径：./resources/audio/lfasr.wav
    //3、通过classpath：
//    private static final String AUDIO_FILE_PATH = LfasrSDKDemo.class.getResource("/").getPath()+"/audio/lfasr.wav";
    private static  String AUDIO_FILE_PATH = "/Volumes/DATA/tmp/C.mp3";
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static void standard(String path1) throws InterruptedException, IOException {
        AUDIO_FILE_PATH = path1;
        //1、创建客户端实例
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);

        //2、上传
        Message task = lfasrClient.upload(AUDIO_FILE_PATH);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        double i1 = 5;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            log.debug("正在转出....{}",message.getData());

            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());
        List<MsgResult> msgResults = JSON.parseArray(result.getData(), MsgResult.class);
        int i = 1;
        String path = AUDIO_FILE_PATH.substring(0,AUDIO_FILE_PATH.lastIndexOf("/"));
        File file = new File(path+"/1.srt");
        if (!file.exists()){
            file.mkdirs();
            file.createNewFile();
            file = new File("srt/1.srt");
            if (!file.exists()){
                throw new IOException("文件不存在且创建失败");
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            for (MsgResult res : msgResults) {
                log.info("正在写入第 「{}」 行",i);
                SrtEntity entity = new SrtEntity(i++,res.getBg(),res.getEd(),res.getOnebest());
                try{
                    bw.write(entity.format());
                    bw.newLine();
                }catch (Exception e){
                    log.error("写入失败");
                }
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //退出程序，关闭线程资源，仅在测试main方法时使用。
    }

    /**
     * 语音转文字 处理
     * @param file 文件
     */
    @Override
    public void audioToCaption(MultipartFile file) {
        if (file.isEmpty()) {
            return ;
        }
        log.info(tmp);
        String fileName = file.getOriginalFilename();
        String filePath = tmp+"/";
        File dest = new File(filePath + fileName);
        dest.mkdirs();
        try {
            file.transferTo(dest);
            log.info("上传成功");
            standard(dest.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            log.error(e.toString(), e);
        }
    }
}
