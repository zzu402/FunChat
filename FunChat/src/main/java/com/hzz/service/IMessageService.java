package com.hzz.service;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;

import java.util.Date;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/11/1
 */
public interface IMessageService {

    void setCommand(String text, String fromUserName, String selfName);

    void saveFile(BaseMsg msg,String type,Date nowDate);

    String sendFileToUser(BaseMsg msg);

    void massSend(BaseMsg msg);

    String savePic(BaseMsg baseMsg,Date nowDate);

    String saveVoice(BaseMsg baseMsg,Date now);

    String saveMedia(BaseMsg baseMsg,Date now);

    String saveVideo(BaseMsg baseMsg,Date nowDate);

    String robot(MyHttpClient httpClient, String text);

    String control(BaseMsg baseMsg);

    void saveMsg(BaseMsg baseMsg,String selfName, String toUserName, String fromUserName, String text, boolean groupMsg, String msgFromUserNameInGroup);



}
