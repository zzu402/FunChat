package com.hzz.beans;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/28
 */
public class CommandSwitch {
    private boolean autoChat = false;// 启动自动聊天
    private boolean saveMessage = true;// 启动消息备份
    private boolean saveVoice = true;//
    private boolean saveMedia = true;//
    private boolean saveVideo = true;//
    private boolean savePic = true;//
    private boolean massSend=false;//群发开关
    private boolean massSendGroup=false;//群发 组
    private boolean downloadFile=true;//下载文件
    private boolean controlPc=true;//控制电脑
    private boolean uploadFile=true;

    public boolean isUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(boolean uploadFile) {
        this.uploadFile = uploadFile;
    }

    public boolean isControlPc() {
		return controlPc;
	}
	public void setControlPc(boolean controlPc) {
		this.controlPc = controlPc;
	}
	public boolean isDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(boolean downloadFile) {
		this.downloadFile = downloadFile;
	}
	public boolean isMassSendGroup() {
		return massSendGroup;
	}
	public void setMassSendGroup(boolean massSendGroup) {
		this.massSendGroup = massSendGroup;
	}
	public boolean isMassSend() {
		return massSend;
	}
	public void setMassSend(boolean massSend) {
		this.massSend = massSend;
	}
	public boolean isAutoChat() {
        return autoChat;
    }
    public void setAutoChat(boolean autoChat) {
        this.autoChat = autoChat;
    }

    public boolean isSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(boolean saveMessage) {
        this.saveMessage = saveMessage;
    }

    public boolean isSaveVoice() {
        return saveVoice;
    }

    public void setSaveVoice(boolean saveVoice) {
        this.saveVoice = saveVoice;
    }

    public boolean isSaveMedia() {
        return saveMedia;
    }

    public void setSaveMedia(boolean saveMedia) {
        this.saveMedia = saveMedia;
    }

    public boolean isSaveVideo() {
        return saveVideo;
    }

    public void setSaveVideo(boolean saveVideo) {
        this.saveVideo = saveVideo;
    }

    public boolean isSavePic() {
        return savePic;
    }

    public void setSavePic(boolean savePic) {
        this.savePic = savePic;
    }

    @Override
    public String toString() {
        return "CommandSwitch{" +
                "autoChat=" + autoChat +
                ", saveMessage=" + saveMessage +
                ", saveVoice=" + saveVoice +
                ", saveMedia=" + saveMedia +
                ", saveVideo=" + saveVideo +
                ", savePic=" + savePic +
                ", massSend=" + massSend +
                ", massSendGroup=" + massSendGroup +
                ", downloadFile=" + downloadFile +
                ", controlPc=" + controlPc +
                ", uploadFile=" + uploadFile +
                '}';
    }
}
