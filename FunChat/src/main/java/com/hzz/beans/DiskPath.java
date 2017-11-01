package com.hzz.beans;
/**
 * 
 * @author zhenzong
 * 放置磁盘的存储路径
 */
public class DiskPath {
	private String picPath;
	private String voicePath;
	private String mediaPath;
	private String videoPath;
	private String qrPath;
	private String messagePath;
	private String filePath;
	private String propertiesPath;
	private String scriptPath;
	
	@Override
	public String toString() {
		return "DiskPath [picPath=" + picPath + ", voicePath=" + voicePath
				+ ", mediaPath=" + mediaPath + ", videoPath=" + videoPath
				+ ", qrPath=" + qrPath + ", messagePath=" + messagePath
				+ ", filePath=" + filePath + ", propertiesPath="
				+ propertiesPath + ", scriptPath=" + scriptPath + "]";
	}
	public String getScriptPath() {
		return scriptPath;
	}
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getVoicePath() {
		return voicePath;
	}
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getQrPath() {
		return qrPath;
	}
	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}
	public String getMessagePath() {
		return messagePath;
	}
	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getPropertiesPath() {
		return propertiesPath;
	}
	public void setPropertiesPath(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}
	
	
	

}
