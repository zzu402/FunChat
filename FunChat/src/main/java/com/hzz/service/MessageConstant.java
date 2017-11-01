package com.hzz.service;

public interface MessageConstant {
    String CMD_HELP = "开关命令：\r\n"
            +"开启自动聊天:start auto chat\r\n"
            + "关闭自动聊天:close auto chat\r\n"
            + "开启图片保存模式:start save pic\r\n"
            + "关闭图片保存模式:start save pic\r\n"
            + "开启消息备份模式:start save message\r\n"
            + "关闭消息备份模式:close save message\r\n"
            + "开启保存视频模式:start save video\r\n"
            + "关闭保存视频模式:close save video\r\n"
            + "开启保存声音模式:start save voice\r\n"
            + "关闭保存声音模式:close save voice\r\n"
            + "开启保存文件模式:start save media\r\n"
            + "关闭保存文件模式:close save media\r\n"
            + "开启群发模式:start mass send\r\n"
            + "关闭群发模式:close mass send\r\n"
            + "开启群发组模式:start mass send group\r\n"
            + "关闭群发组模式:close mass send group\r\n"
            + "开启文件下载模式:start download file\r\n"
            + "关闭文件下载模式:close download file\r\n"
            + "开启控制电脑模式:start control pc\r\n"
            + "关闭控制电脑模式:close control pc\r\n"
            + "开启文件上传模式:start upload file\r\n"
            + "关闭文件上传模式:close upload file\r\n"
            +"文件上传命令:upload \r\n"
            +"获取资源命令:f-help \r\n"
            +"文件下载命令:f-资源编号\r\n"
            +"控制命令:exec 命令行\r\n";

    String PIC_MESSAGE="图片消息";
    String VIDEO_MESSAGE="视频消息";
    String VOICE_MESSAGE="声频消息";
    String MEDIA_MESSAGE="媒体消息";

    String PIC_SUFFIX=".jpg";
    String VIDEO_SUFFIX=".mp4";
    String VOICE_SUFFIX=".mp3";
    String SEND_FILE="发送文件:";
    String SAVE_PIC_SUCESS="图片保存成功";
    String SAVE_VIDEO_SUCESS="视频保存成功";
    String SAVE_VOICE_SUCESS="声频保存成功";
    String SAVE_MEDIA_SUCESS="媒体保存成功";
    String PIC_PATH="发送图片消息，图片路径：";
    String VOICE_PATH="发送声音消息，声音路径：";
    String VIDEO_PATH="发送视频消息，视频路径：";
    String MEDIA_PATH="发送媒体消息，媒体路径：";

    String DOWNLOAD_CMD_PREFIX="f-";
    String DOWNLOAD_CMD_HELP="f -help";
    String WAIT_FOR_UPLOAD="等待上传文件...";
    String UPLOAD_SUCCESS="文件上传成功";
    String UPLOAD_CMD="upload";
    String NO_PRIVILEGE="No Privilege";
    String CMD_HELP_PREFIX="cmd -help";
    String DATA_FORMAT="yyyy-MM-dd-HH-mm-ss";

    String DOWNLOAD_PROMPT="获取资源请以【f-资源ID】,如获取5号资源，输入f-5:\r\n";






}
