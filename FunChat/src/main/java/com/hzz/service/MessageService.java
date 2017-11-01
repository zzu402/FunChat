package com.hzz.service;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import com.alibaba.fastjson.JSONObject;
import com.hzz.beans.CommandSwitch;
import com.hzz.beans.Operation;
import com.hzz.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/28
 */
public class MessageService {
	private Logger logger = LoggerFactory.getLogger(MessageService.class);
	private static CommandSwitch commandSwitch = new CommandSwitch();
	public static Map<String, String> templet = null;
	public static Map<String, String> privilegeList = null;
	public static List<String> blackList=new ArrayList<>();
	public static List<String> messageList=new ArrayList<>();

	public static void main(String []a){
		MessageService service=new MessageService();
		System.out.print(service.getTempletValue("你好"));
	}

	static {
		templet=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
				+ File.separator + "templet.properties");
		privilegeList=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
				+ File.separator + "privilege.properties");
		CommonUtils.getBlackListFromPrivilege();
	}
	public static CommandSwitch getCommandSwitch() {
		return commandSwitch;
	}

	/**
	 * 设置命令开关
	 * 
	 * @param text
	 * @param fromUserName
	 * @param selfName
	 */
	public void setCommand(String text, String fromUserName, String selfName) {
		if (fromUserName.equals(selfName)) {
			if (text.equalsIgnoreCase("start auto chat")) {
				commandSwitch.setAutoChat(true);
				logger.info("开启自动聊天模式");
			} else if (text.equalsIgnoreCase("close auto chat")) {
				commandSwitch.setAutoChat(false);
				logger.info("关闭自动聊天模式");
			} else if (text.equalsIgnoreCase("start save message")) {
				commandSwitch.setSaveMessage(true);
				logger.info("开启聊天备份模式");
			} else if (text.equalsIgnoreCase("close save message")) {
				commandSwitch.setSaveMessage(false);
				logger.info("关闭聊天备份模式");
			} else if (text.equalsIgnoreCase("start save pic")) {
				commandSwitch.setSavePic(true);
				logger.info("开启图片保存模式");
			} else if (text.equalsIgnoreCase("close save pic")) {
				commandSwitch.setSavePic(false);
				logger.info("关闭图片保存模式");
			} else if (text.equalsIgnoreCase("start save video")) {
				commandSwitch.setSaveVideo(true);
				logger.info("开启视频保存模式");
			} else if (text.equalsIgnoreCase("close save video")) {
				commandSwitch.setSaveVideo(false);
				logger.info("关闭视频保存模式");
			} else if (text.equalsIgnoreCase("start save voice")) {
				commandSwitch.setSaveVoice(true);
				logger.info("开启音频保存模式");
			} else if (text.equalsIgnoreCase("close save voice")) {
				commandSwitch.setSaveVoice(false);
				logger.info("关闭音频保存模式");
			} else if (text.equalsIgnoreCase("start save media")) {
				commandSwitch.setSaveMedia(true);
				logger.info("开启数据保存模式");
			} else if (text.equalsIgnoreCase("close save media")) {
				commandSwitch.setSaveMedia(false);
				logger.info("关闭数据保存模式");
			} else if (text.equalsIgnoreCase("start mass send")) {
				commandSwitch.setMassSend(true);
				logger.info("开启群发模式");
			} else if (text.equalsIgnoreCase("close mass send")) {
				commandSwitch.setMassSend(false);
				logger.info("关闭群发模式");
			} else if (text.equalsIgnoreCase("start mass send group")) {
				commandSwitch.setMassSendGroup(true);
				logger.info("开启群发组模式");
			} else if (text.equalsIgnoreCase("close mass send group")) {
				commandSwitch.setMassSendGroup(false);
				logger.info("关闭群发组模式");
			} else if (text.equalsIgnoreCase("start download file")) {
				commandSwitch.setDownloadFile(true);
				logger.info("开启文件下载模式");
			} else if (text.equalsIgnoreCase("close download file")) {
				commandSwitch.setDownloadFile(false);
				logger.info("关闭文件下载模式");
			}else if (text.equalsIgnoreCase("start control pc")) {
				commandSwitch.setControlPc(true);
				logger.info("开启控制电脑模式");
			} else if (text.equalsIgnoreCase("close control pc")) {
				commandSwitch.setControlPc(false);
				logger.info("关闭控制电脑模式");
			} else if(text.equalsIgnoreCase("start upload file")){
				commandSwitch.setUploadFile(true);
				logger.info("开启文件上传模式");
			}else if(text.equalsIgnoreCase("close upload file")){
				commandSwitch.setUploadFile(false);
				logger.info("关闭文件上传模式");
			}
		}
		if (text.equalsIgnoreCase("cmd -help")) {
			MessageTools.sendMsgById(MessageConstant.CMD_HELP, fromUserName);
		}
	}

	private String getPicFromPath(BaseMsg baseMsg,Date now){
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(now) + ".jpg"; // 这里使用收到图片的时间作为文件名
		String userName = CommonUtils.select(baseMsg.getFromUserName(),
				baseMsg.getToUserName(), Core.getInstance().getUserName());
		String path = CommonUtils.diskPath.getPicPath() + File.separator
				+ CommonUtils.getNickByUserName(userName);
		File file = new File(path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String picPath = path + File.separator + fileName; // 保存图片的路径
		return picPath;
	}
	private String getVoiceFromPath(BaseMsg baseMsg,Date now){
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(now) + ".mp3"; // 这里使用收到图片的时间作为文件名
		String userName = CommonUtils.select(baseMsg.getFromUserName(),
				baseMsg.getToUserName(), Core.getInstance().getUserName());
		String path = CommonUtils.diskPath.getVoicePath() + File.separator
				+ CommonUtils.getNickByUserName(userName);
		File file = new File(path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String voicePath = path + File.separator + fileName; // 保存图片的路径
		return voicePath;
	}
	private String getVideoFromPath(BaseMsg baseMsg,Date now){
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(now) + ".mp4"; // 这里使用收到图片的时间作为文件名
		String userName = CommonUtils.select(baseMsg.getFromUserName(),
				baseMsg.getToUserName(), Core.getInstance().getUserName());
		String path = CommonUtils.diskPath.getVideoPath() + File.separator
				+ CommonUtils.getNickByUserName(userName);
		File file = new File(path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String videoPath = path + File.separator + fileName; // 保存图片的路径
		return videoPath;
	}

	private String getMediaFromPath(BaseMsg baseMsg,Date now){
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(now) + baseMsg.getFileName(); //
		String userName = CommonUtils.select(baseMsg.getFromUserName(),
				baseMsg.getToUserName(), Core.getInstance().getUserName());
		String path = CommonUtils.diskPath.getMediaPath() + File.separator
				+ CommonUtils.getNickByUserName(userName);
		File file = new File(path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String mediaPath = path + File.separator + fileName; // 保存图片的路径
		return mediaPath;
	}

	public void saveFile(BaseMsg msg,String type,Date nowDate){
		String fromUserName= msg.getFromUserName();
		Operation op= CommonUtils.operationList.get(fromUserName);
		if(op==null)
			return ;
		if(op.getAction().equalsIgnoreCase("upload")){
			Long time=op.getLastOperation();
			Long now =System.currentTimeMillis();
			if(now-time>5*60*1000){
				CommonUtils.operationList.remove(fromUserName);
				return ;
			}
			if(type.equals(MsgTypeEnum.PIC.getType())){
				String fromPath=getPicFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
						.format(new Date()) + ".jpg"; // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}else if(type.equals(MsgTypeEnum.VIEDO.getType())){
				String fromPath=getVideoFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
						.format(new Date()) + ".mp4"; // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}else if(type.equals(MsgTypeEnum.VOICE.getType())){
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
						.format(new Date()) + ".mp3"; // 这里使用收到图片的时间作为文件
				String fromPath=getVoiceFromPath(msg,nowDate);
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);

			}else if(type.equals(MsgTypeEnum.MEDIA.getType())){
				String fromPath=getMediaFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
						.format(new Date()) + msg.getFileName(); // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}
			MessageTools.sendMsgById("文件上传成功", msg.getFromUserName());
		}
	}

	/*
	 * 发送文件
	 */
	public void sendFileToUser(BaseMsg msg) {
		String content = msg.getContent();
		if (content.contains("f-help")) {
			StringBuilder sb = new StringBuilder();
			sb.append("获取资源请以【f-资源ID】,如获取5号资源，输入f-5:\r\n");
			List<File> fileList = FileUtil.getFilesFromDir(CommonUtils.diskPath
					.getFilePath());
			File file = null;
			for (int i = 0; i < fileList.size(); i++) {
				file = fileList.get(i);
				sb.append("["+i+"] " + file.getName() + "\r\n");
			}
			saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),sb.toString(),msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());
			MessageTools.sendMsgById(sb.toString(), msg.getFromUserName());
		}
		if (content.startsWith("f-")) {
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				int n = Integer.valueOf(m.group(0));
				List<File> fileList = FileUtil
						.getFilesFromDir(CommonUtils.diskPath.getFilePath());
				File f=fileList.get(n);
				if (getCommandSwitch().isSaveMessage())
					saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),"发送文件："+f.getName(),msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());

				if(f.getName().contains(".jpg")){
					MessageTools.sendPicMsgByUserId(msg.getFromUserName(),f.getAbsolutePath());
				}else
					MessageTools.sendFileMsgByUserId(msg.getFromUserName(),
						f.getAbsolutePath());
			}
		}
	}

	/**
	 * 群发信息
	 * 
	 * @param
	 */
	public void massSend(BaseMsg msg) {
		if (!msg.getFromUserName().equals(msg.getToUserName()))
			return;
		for (JSONObject o : WechatTools.getContactList()) {
			if(CommonUtils.isBlackList(o.getString("UserName")))
				continue;
			MessageTools.sendMsgById(msg.getContent(), o.getString("UserName"));
		}
		if (!commandSwitch.isMassSendGroup())
			return;
		for (JSONObject o : WechatTools.getGroupList()) {
			if(CommonUtils.isBlackList(o.getString("UserName")))
				continue;
			MessageTools.sendMsgById(msg.getContent(), o.getString("UserName"));
		}
	}

	public static void massSend(String content){
		if (!commandSwitch.isMassSend())
			return;
		for (JSONObject o : WechatTools.getContactList()) {
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(content, o.getString("UserName"));
		}
		if (!commandSwitch.isMassSendGroup())
			return;
		for (JSONObject o : WechatTools.getGroupList()) {
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(content, o.getString("UserName"));
		}
	}

	public String getTempletValue(String text) {
		for (String key : templet.keySet()) {
			if (StringUtils.SimilarDegree(key, text) >= 0.65) {
				return templet.get(key);
			}
		}
		return null;
	}

	/**
	 * 保存图片
	 * 
	 * @param baseMsg
	 * @return
	 */
	public String savePic(BaseMsg baseMsg,Date nowDate) {

		String picPath =getPicFromPath(baseMsg,nowDate);
		DownloadTools
				.getDownloadFn(baseMsg, MsgTypeEnum.PIC.getType(), picPath);
		FileUtil.saveMsg(Core.getInstance().getUserName(),
				baseMsg.getToUserName(), baseMsg.getFromUserName(),
				"发送图片消息，图片路径：" + picPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info("图片保存成功");
		return null;

	}

	/**
	 * 保存声音
	 * 
	 * @param baseMsg
	 * @return
	 */
	public String saveVoice(BaseMsg baseMsg,Date now) {

		String voicePath = getVoiceFromPath(baseMsg ,now);
		DownloadTools.getDownloadFn(baseMsg, MsgTypeEnum.VOICE.getType(),
				voicePath);
		FileUtil.saveMsg(Core.getInstance().getUserName(),
				baseMsg.getToUserName(), baseMsg.getFromUserName(),
				"发送声音消息，声音路径：" + voicePath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info("声音保存成功");
		return null;

	}

	/**
	 * 保存视频
	 * 
	 * @param baseMsg
	 * @return
	 */
	public String saveVideo(BaseMsg baseMsg,Date nowDate) {

		String videoPath = getVideoFromPath(baseMsg,nowDate);
		DownloadTools.getDownloadFn(baseMsg, MsgTypeEnum.VIEDO.getType(),
				videoPath);
		FileUtil.saveMsg(Core.getInstance().getUserName(),
				baseMsg.getToUserName(), baseMsg.getFromUserName(),
				"发送视频消息，视频路径：" + videoPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info("视频保存成功");
		return null;

	}

	/**
	 * 保存文件
	 * 
	 * @param baseMsg
	 * @return
	 */
	public String saveMedia(BaseMsg baseMsg,Date now) {

		String mediaPath =getMediaFromPath(baseMsg,now);
		DownloadTools.getDownloadFn(baseMsg, MsgTypeEnum.MEDIA.getType(),
				mediaPath);
		FileUtil.saveMsg(Core.getInstance().getUserName(),
				baseMsg.getToUserName(), baseMsg.getFromUserName(),
				"发送文件消息，文件路径：" + mediaPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info("文件保存成功");
		return null;

	}
	public String robot(MyHttpClient httpClient, String text) {
		return Robot.startRobot(httpClient, text);
	}
	public String control(BaseMsg baseMsg) {
		return ProcessUtil.control(baseMsg);
	}
	public void saveMsg(BaseMsg baseMsg,String selfName, String toUserName, String fromUserName, String text, boolean groupMsg, String msgFromUserNameInGroup) {
		FileUtil.saveMsg(selfName, toUserName, fromUserName,
				text, groupMsg,msgFromUserNameInGroup);
	}
}
