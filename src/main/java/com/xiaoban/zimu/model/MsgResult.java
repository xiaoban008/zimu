package com.xiaoban.zimu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoban
 * @date 2021/2/1 下午10:33
 */
@Data
public class MsgResult implements Serializable {
    private Integer bg;
    private Integer ed;
    private String onebest;
    private String speaker;
    private String si;
   private String wordsResultList;// 	分词列表
    //注：仅开启分词或者多候选时返回，且英文暂不支持
    private String alternativeList ;	//多候选列表，按置信度排名
    //注：仅开启分词或者多候选时返回，且英文暂不支持
    private String wordBg ;	//词相对于本句子的起始帧，其中一帧是10ms
    //注：仅开启分词或者多候选时返回，且英文暂不支持
    private String wordEd ;	//词相对于本句子的终止帧，其中一帧是10ms
    //注：仅开启分词或者多候选时返回，且英文暂不支持
    private String wordsName; 	//词内容
    //注：仅开启分词或者多候选时返回，且英文暂不支持
    private String wc ;//	句子置信度，范围为[0,1]
    //注：仅开启分词或者多候选时返回，且英文暂不支持

}
