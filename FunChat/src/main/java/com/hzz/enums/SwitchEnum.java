package com.hzz.enums;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/11/1
 */
public enum SwitchEnum {

    START_AUTO_CHAT("start auto chat","开启自动聊天模块"),
    CLOSE_AUTO_CHAT("close auto chat","关闭自动聊天模块"),
    START_SAVE_MSG("start save message","开启聊天备份模块"),
    CLOSE_SAVE_MSG("close save message","关闭聊天备份模块"),
    START_SAVE_PIC("start save pic","开启图片备份模块"),
    CLOSE_SAVE_PIC("close save pic","关闭图片备份模块"),
    START_SAVE_VIDEO("start save video","开启视频备份模块"),
    CLOSE_SAVE_VIDEO("close save video","关闭视频备份模块"),
    START_SAVE_VOICE("start save voice","开启声频备份模块"),
    CLOSE_SAVE_VOICE("close save voice","关闭声频备份模块"),
    START_SAVE_MEDIA("start save media","开启媒体备份模块"),
    CLOSE_SAVE_MEDIA("close save media","关闭媒体备份模块"),
    START_MASS_SEND("start mass send","开启通讯录群聊模块"),
    CLOSE_MASS_SEND("close mass send","关闭通讯录群聊模块"),
    START_MASS_SEND_GROUP("start mess send group","开启群组群聊模块"),
    CLOSE_MASS_SEND_GROUP("close mess send group","关闭群组群聊模块"),
    START_DOWNLOAD_FILE("start download file","开启文件下载模块"),
    CLOSE_DOWNLOAD_FILE("close download file","关闭文件下载模块"),
    START_CONTROL_PC("start control pc","开启控制电脑模块"),
    CLOSE_CONTROL_PC("close control pc","关闭控制电脑模块"),
    START_UPLOAD_FILE("start upload file","开启文件上传模块"),
    CLOSE_UPLOAD_FILE("close upload file","关闭文件上传模块"),
    START_VERIFY_FRIEND("start verify friend","开启验证好友模块"),
    CLOSE_VERIFY_FRIEND("close verify friend","关闭验证好友模块")
    ;


    private String command;
    private String desc;
    SwitchEnum(String command,String desc){
        this.command=command;
        this.desc=desc;
    }
    public String getCommand(){
        return  this.command;
    }
    public String getDesc(){
        return  this.desc;
    }





}
