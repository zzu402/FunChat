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
            +"开启验证好友模式:start verify friend\r\n"
            +"关闭验证好友模式:close verify friend\r\n"
            +"文件上传命令:upload \r\n"
            +"获取资源命令:f-help \r\n"
            +"文件下载命令:f-资源编号\r\n"
            +"文件邮件发送命令:e-资源编号 -u邮箱地址\r\n"
            +"控制命令:exec 命令行\r\n";

    String PIC_MESSAGE="图片消息";
    String VIDEO_MESSAGE="视频消息";
    String VOICE_MESSAGE="声频消息";
    String MEDIA_MESSAGE="媒体消息";

    String PIC_SUFFIX=".jpg";
    String PIC_SUFFIX2=".png";
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

    String EMAIL_SUCCESS="文件通过邮件发送成功";
    String EMAIL_ERROR="文件通过邮件发送失败，请检查邮箱名";

    String DOWNLOAD_BY_EMAIL="e-";
    String EMAIL_USER_COUNT="-u";

    String DOWNLOAD_CMD_PREFIX="f-";
    String DOWNLOAD_CMD_HELP="f-help";
    String WAIT_FOR_UPLOAD="等待上传文件...";
    String UPLOAD_SUCCESS="文件上传成功";
    String UPLOAD_CMD="upload";
    String NO_PRIVILEGE="No Privilege";
    String CMD_HELP_PREFIX="cmd -help";
    String DATA_FORMAT="yyyy-MM-dd-HH-mm-ss";
    String FILE_HUGE="文件超过1M小于50M，请使用邮件发送方式，发送命令为 e-[资源id] -u[邮箱号码]\r\n"
            +"例如e-5 -u****@qq.com\r\n";
    String FILE_HUGE_MAIL="文件超过50M，拒绝发送\r\n";
    String MAIL_SENDING="正在发送邮件中，可能需要等待几分钟才能收到...";
    String DOWNLOAD_PROMPT="获取资源请以【f-资源ID】,如获取5号资源，输入f-5:\r\n";

    String VERIFY_FRIEND="你好，来自%s%s 的%s，欢迎添加我为好友！";






}
