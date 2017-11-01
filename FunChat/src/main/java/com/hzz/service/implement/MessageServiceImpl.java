package com.hzz.service.implement;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import com.alibaba.fastjson.JSONObject;
import com.hzz.beans.Operation;
import com.hzz.enums.SwitchEnum;
import com.hzz.service.IMessageService;
import com.hzz.service.MessageConstant;
import com.hzz.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/28
 */
public class MessageServiceImpl implements IMessageService {
	private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	/**
	 * 设置命令开关
	 * 
	 * @param text
	 * @param fromUserName
	 * @param selfName
	 */
	public void setCommand(String text, String fromUserName, String selfName) {
		if (fromUserName.equals(selfName)) {
			if (text.equalsIgnoreCase(SwitchEnum.START_AUTO_CHAT.getCommand())) {
				DataUtil.commandSwitch.setAutoChat(true);
				logger.info(SwitchEnum.START_AUTO_CHAT.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_AUTO_CHAT.getCommand())) {
				DataUtil.commandSwitch.setAutoChat(false);
				logger.info(SwitchEnum.CLOSE_AUTO_CHAT.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_SAVE_MSG.getCommand())) {
				DataUtil.commandSwitch.setSaveMessage(true);
				logger.info(SwitchEnum.START_SAVE_MSG.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_SAVE_MSG.getCommand())) {
				DataUtil.commandSwitch.setSaveMessage(false);
				logger.info(SwitchEnum.CLOSE_SAVE_MSG.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_SAVE_PIC.getCommand())) {
				DataUtil.commandSwitch.setSavePic(true);
				logger.info(SwitchEnum.START_SAVE_PIC.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_SAVE_PIC.getCommand())) {
				DataUtil.commandSwitch.setSavePic(false);
				logger.info(SwitchEnum.CLOSE_SAVE_PIC.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_SAVE_VIDEO.getCommand())) {
				DataUtil.commandSwitch.setSaveVideo(true);
				logger.info(SwitchEnum.START_SAVE_VIDEO.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_SAVE_VIDEO.getCommand())) {
				DataUtil.commandSwitch.setSaveVideo(false);
				logger.info(SwitchEnum.CLOSE_SAVE_VIDEO.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_SAVE_VOICE.getCommand())) {
				DataUtil.commandSwitch.setSaveVoice(true);
				logger.info(SwitchEnum.START_SAVE_VOICE.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_SAVE_VOICE.getCommand())) {
				DataUtil.commandSwitch.setSaveVoice(false);
				logger.info(SwitchEnum.CLOSE_SAVE_VOICE.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_SAVE_MEDIA.getCommand())) {
				DataUtil.commandSwitch.setSaveMedia(true);
				logger.info(SwitchEnum.START_SAVE_MEDIA.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_SAVE_MEDIA.getCommand())) {
				DataUtil.commandSwitch.setSaveMedia(false);
				logger.info(SwitchEnum.CLOSE_SAVE_MEDIA.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_MASS_SEND.getCommand())) {
				DataUtil.commandSwitch.setMassSend(true);
				logger.info(SwitchEnum.START_MASS_SEND.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_MASS_SEND.getCommand())) {
				DataUtil.commandSwitch.setMassSend(false);
				logger.info(SwitchEnum.START_MASS_SEND.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_MASS_SEND_GROUP.getCommand())) {
				DataUtil.commandSwitch.setMassSendGroup(true);
				logger.info(SwitchEnum.START_MASS_SEND_GROUP.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_MASS_SEND_GROUP.getCommand())) {
				DataUtil.commandSwitch.setMassSendGroup(false);
				logger.info(SwitchEnum.CLOSE_MASS_SEND_GROUP.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.START_DOWNLOAD_FILE.getCommand())) {
				DataUtil.commandSwitch.setDownloadFile(true);
				logger.info(SwitchEnum.START_DOWNLOAD_FILE.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_DOWNLOAD_FILE.getCommand())) {
				DataUtil.commandSwitch.setDownloadFile(false);
				logger.info(SwitchEnum.CLOSE_DOWNLOAD_FILE.getDesc());
			}else if (text.equalsIgnoreCase(SwitchEnum.START_CONTROL_PC.getCommand())) {
				DataUtil.commandSwitch.setControlPc(true);
				logger.info(SwitchEnum.START_CONTROL_PC.getDesc());
			} else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_CONTROL_PC.getCommand())) {
				DataUtil.commandSwitch.setControlPc(false);
				logger.info(SwitchEnum.CLOSE_CONTROL_PC.getDesc());
			} else if(text.equalsIgnoreCase(SwitchEnum.START_UPLOAD_FILE.getCommand())){
				DataUtil.commandSwitch.setUploadFile(true);
				logger.info(SwitchEnum.START_UPLOAD_FILE.getDesc());
			}else if(text.equalsIgnoreCase(SwitchEnum.CLOSE_UPLOAD_FILE.getCommand())){
				DataUtil.commandSwitch.setUploadFile(false);
				logger.info(SwitchEnum.CLOSE_UPLOAD_FILE.getDesc());
			}
		}
		if (text.equalsIgnoreCase(MessageConstant.CMD_HELP_PREFIX)) {
			MessageTools.sendMsgById(MessageConstant.CMD_HELP, fromUserName);
		}
	}

	private String getPicFromPath(BaseMsg baseMsg,Date now){
		String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
				.format(now) + MessageConstant.PIC_SUFFIX; // 这里使用收到图片的时间作为文件名
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
		String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
				.format(now) + MessageConstant.VOICE_SUFFIX; // 这里使用收到图片的时间作为文件名
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
		String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
				.format(now) + MessageConstant.VIDEO_SUFFIX; // 这里使用收到图片的时间作为文件名
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
		String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
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
		if(op.getAction().equalsIgnoreCase(MessageConstant.UPLOAD_CMD)){
			Long time=op.getLastOperation();
			Long now =System.currentTimeMillis();
			if(now-time>5*60*1000){
				CommonUtils.operationList.remove(fromUserName);
				return ;
			}
			if(type.equals(MsgTypeEnum.PIC.getType())){
				String fromPath=getPicFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
						.format(new Date()) + MessageConstant.PIC_SUFFIX; // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}else if(type.equals(MsgTypeEnum.VIEDO.getType())){
				String fromPath=getVideoFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
						.format(new Date()) + MessageConstant.VIDEO_SUFFIX; // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}else if(type.equals(MsgTypeEnum.VOICE.getType())){
				String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
						.format(new Date()) + MessageConstant.VOICE_SUFFIX; // 这里使用收到图片的时间作为文件
				String fromPath=getVoiceFromPath(msg,nowDate);
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);

			}else if(type.equals(MsgTypeEnum.MEDIA.getType())){
				String fromPath=getMediaFromPath(msg,nowDate);
				String fileName = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
						.format(new Date()) + msg.getFileName(); // 这里使用收到图片的时间作为文件名
				String toPath=CommonUtils.diskPath.getFilePath()+File.separator+fileName;
				FileUtil.copyFile(fromPath,toPath);
			}
			MessageTools.sendMsgById(MessageConstant.UPLOAD_SUCCESS, msg.getFromUserName());
		}
	}

	/*
	 * 发送文件
	 */
	public void sendFileToUser(BaseMsg msg) {
		String content = msg.getContent();
		if (content.contains(MessageConstant.DOWNLOAD_CMD_HELP)) {
			StringBuilder sb = new StringBuilder();
			sb.append(MessageConstant.DOWNLOAD_PROMPT);
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
		if (content.startsWith(MessageConstant.DOWNLOAD_CMD_PREFIX)) {
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				int n = Integer.valueOf(m.group(0));
				List<File> fileList = FileUtil
						.getFilesFromDir(CommonUtils.diskPath.getFilePath());
				File f=fileList.get(n);
				if (DataUtil.commandSwitch.isSaveMessage())
					saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),
							MessageConstant.SEND_FILE+f.getName(),msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());

				if(f.getName().contains(MessageConstant.PIC_SUFFIX)){
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
		if (!DataUtil.commandSwitch.isMassSendGroup())
			return;
		for (JSONObject o : WechatTools.getGroupList()) {
			if(CommonUtils.isBlackList(o.getString("UserName")))
				continue;
			MessageTools.sendMsgById(msg.getContent(), o.getString("UserName"));
		}
	}

	public static void massSend(String content){
		if (!DataUtil.commandSwitch.isMassSend())
			return;
		for (JSONObject o : WechatTools.getContactList()) {
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(content, o.getString("UserName"));
		}
		if (!DataUtil.commandSwitch.isMassSendGroup())
			return;
		for (JSONObject o : WechatTools.getGroupList()) {
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(content, o.getString("UserName"));
		}
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
				MessageConstant.PIC_PATH + picPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info(MessageConstant.SAVE_PIC_SUCESS);
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
				MessageConstant.VOICE_PATH + voicePath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info(MessageConstant.SAVE_VOICE_SUCESS);
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
				MessageConstant.VIDEO_PATH+ videoPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info(MessageConstant.SAVE_VIDEO_SUCESS);
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
				MessageConstant.MEDIA_PATH + mediaPath, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		logger.info(MessageConstant.SAVE_MEDIA_SUCESS);
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
