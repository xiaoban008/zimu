package com.xiaoban.zimu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoban
 * @date 2021/2/1 下午10:48
 */
@Data
public class SrtEntity implements Serializable {
    public static final String NEW_LINE = System.getProperty("line.separator");


    /**
     * 字幕序号
     */
    private Integer number;
    /**
     * 开始时间
     */
    private Integer bg;
    private Integer bgSecond;
    /**
     * 结束时间
     */
    private Integer ed;
    private Integer edSecond;

    /**
     * 字幕内容
     */
    private String content;

    public SrtEntity(Integer number, Integer bg, Integer ed, String content) {
        this.number = number;
        this.bg = bg;
        this.ed = ed;
        this.content = content;
    }

    public SrtEntity() {
    }

    public String format(){
        String cot = number+"";

        String start = "hh:mm:ss,millisecond";
        String end = "hh:mm:ss,millisecond";
        start= start.replace("hh",getHh(bg));
        start= start.replace("mm",getMm(bg));
        start= start.replace("millisecond",bg%1000+"");
        start= start.replace("ss",getBgSecondStr());

        end= end.replace("hh",getHh(ed));
        end= end.replace("mm",getMm(ed));
        end= end.replace("ss",getEdSecondStr());
        end= end.replace("millisecond",ed%1000+"");

        cot+=NEW_LINE;
        cot+=start+" --> "+end;
        cot+=NEW_LINE;
        cot+=getContent();
        cot+=NEW_LINE;

        return cot;
    }
    private String getMm(Integer millisecond ){
        if (millisecond==null){
            return "00";
        }
        int tmp = millisecond/60000;
        tmp = tmp%60;
        if (tmp==0){
            return "00";
        }
        if (tmp<10){
            return "0"+tmp;
        }
        return tmp +"";
    }

    public static final int MaxLen = 25;
    public String getContent() {
        if (content!=null){
            content= content.replace("，"," ");
            content=content.replace(","," ");
            content=content.replace("。"," ");
            content=content.replace("."," ");
            if (content.endsWith("、")){
                content=content.substring(0,content.indexOf("、"));
            }
            if (content.length()>MaxLen){
               String temp = content.substring(0,MaxLen);
               String temp1 = content.substring(MaxLen);
               content = temp+NEW_LINE+temp1;
            }
        }
        return content;
    }

    private String getHh(Integer millisecond ){
        if (millisecond==null){
            return "00";
        }
        int tmp = millisecond/3600000;
        if (tmp==0){
            return "00";
        }
        if (tmp<10){
            return "0"+tmp;
        }
        return tmp +"";
    }

    public String getBgSecondStr() {
        bgSecond =bg==null?0:bg/1000;
        bgSecond = bgSecond%60;
        if (bgSecond==0){
            return "00";
        }
        if (bgSecond<10){
            return "0"+bgSecond;
        }
        return bgSecond+"";
    }

    public String getEdSecondStr() {
        edSecond =ed==null?0:ed/1000;
        edSecond = edSecond%60;
        if (edSecond==0){
            return "00";
        }
        if (edSecond<10){
            return "0"+edSecond;
        }
        return edSecond+"";
    }

    public static void main(String[] args) {
        System.out.println("这里的honesty以及knowledge都是不可数名词".length());
    }

}
