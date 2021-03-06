package com.hzz.service.implement;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.SleepUtils;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import com.alibaba.fastjson.JSONObject;
import com.hzz.beans.MailBean;
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
			} else if (text.equalsIgnoreCase(SwitchEnum.START_ROBOT_CHAT.getCommand())) {
                DataUtil.commandSwitch.setRobotChat(true);
                logger.info(SwitchEnum.START_ROBOT_CHAT.getDesc());
            } else if (text.equalsIgnoreCase(SwitchEnum.CLOSE_ROBOT_CHAT.getCommand())) {
                DataUtil.commandSwitch.setRobotChat(false);
                logger.info(SwitchEnum.CLOSE_ROBOT_CHAT.getDesc());
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
			} else if(text.equalsIgnoreCase(SwitchEnum.START_VERIFY_FRIEND.getCommand())){
				DataUtil.commandSwitch.setUploadFile(true);
				logger.info(SwitchEnum.START_VERIFY_FRIEND.getDesc());
			}else if(text.equalsIgnoreCase(SwitchEnum.CLOSE_VERIFY_FRIEND.getCommand())){
				DataUtil.commandSwitch.setUploadFile(false);
				logger.info(SwitchEnum.START_VERIFY_FRIEND.getDesc());
			}
		}
		if (text.equalsIgnoreCase(MessageConstant.CMD_HELP_PREFIX)) {
			MessageTools.sendMsgById(MessageConstant.CMD_HELP, fromUserName);
		}
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
	public String sendFileToUser(BaseMsg msg) {
		String content = msg.getContent();
		final String nick=CommonUtils.getNickByUserName(msg.getFromUserName());
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
			return  sb.toString();
		}
		if(content.startsWith(MessageConstant.DOWNLOAD_BY_EMAIL)&&content.contains(MessageConstant.EMAIL_USER_COUNT)){
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				int n = Integer.valueOf(m.group(0));
				List<File> fileList = FileUtil
						.getFilesFromDir(CommonUtils.diskPath.getFilePath());
				final File f = fileList.get(n);
				String[]s=content.split("-u");
				if(s.length>1){
					final String to=s[1];
					if(f.length()>50*1024*1024){
						return MessageConstant.FILE_HUGE_MAIL;
					}
					new Thread(new Runnable() {
						@Override
						public void run() {
							MailBean mb = new MailBean();
							mb.setHost(DataUtil.mailBean.getHost());
							mb.setUsername(DataUtil.mailBean.getUsername());
							mb.setPassword(DataUtil.mailBean.getPassword());
							mb.setFrom(MailUtil.toChinese(DataUtil.mailBean.getFrom()));
							mb.setTo(to);
							mb.setContent(DataUtil.mailBean.getContent());
							mb.attachFile(f.getAbsolutePath());
							MailUtil sm = new MailUtil();
							mb.setSubject(sm.toChinese(DataUtil.mailBean.getSubject()));
							MessageTools.sendMsgById(MessageConstant.MAIL_SENDING,msg.getFromUserName());
							if (sm.sendMail(mb)) {
								FileUtil.writeByFileReader(CommonUtils.diskPath.getMessagePath()+File.separator+CommonUtils.getNickByUserName(Core.getInstance().getUserName())+File.separator+"email.txt","用户："+nick+"获取资源"+f.getName()+"成功"+"邮箱地址:"+to);
								MessageTools.sendMsgById(MessageConstant.EMAIL_SUCCESS,msg.getFromUserName());
								logger.info(MessageConstant.EMAIL_SUCCESS);
								if(DataUtil.commandSwitch.isSaveMessage())
									saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),MessageConstant.EMAIL_SUCCESS,msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());
							} else {
								MessageTools.sendMsgById(MessageConstant.EMAIL_ERROR,msg.getFromUserName());
								logger.info(MessageConstant.EMAIL_ERROR);
								if(DataUtil.commandSwitch.isSaveMessage())
									saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),MessageConstant.EMAIL_ERROR,msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());
							}
						}
					}).start();
					SleepUtils.sleep(1000);
				}
			}
		}

		if (content.startsWith(MessageConstant.DOWNLOAD_CMD_PREFIX)) {
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				int n = Integer.valueOf(m.group(0));
				List<File> fileList = FileUtil
						.getFilesFromDir(CommonUtils.diskPath.getFilePath());
				File f=fileList.get(n);
				if(f.length()>1*1024*1024){
					return MessageConstant.FILE_HUGE;
				}
				if (DataUtil.commandSwitch.isSaveMessage())
					saveMsg(msg,Core.getInstance().getUserName(),msg.getFromUserName(),msg.getToUserName(),
							MessageConstant.SEND_FILE+f.getName(),msg.isGroupMsg(),msg.getMsgFromUserNameInGroup());

				if(f.getName().contains(MessageConstant.PIC_SUFFIX)||f.getName().contains("")){
					MessageTools.sendPicMsgByUserId(msg.getFromUserName(),f.getAbsolutePath());
				}else
					MessageTools.sendFileMsgByUserId(msg.getFromUserName(),
						f.getAbsolutePath());
                logger.info("用户【"+CommonUtils.getNickByUserName(msg.getFromUserName())+"】请求获取文件-【"+f.getAbsolutePath()+"】");
			}
		}
		return null;

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
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(msg.getContent(), o.getString("UserName"));
		}
		if (!DataUtil.commandSwitch.isMassSendGroup())
			return;
		for (JSONObject o : WechatTools.getGroupList()) {
			if(CommonUtils.isBlackList(o.getString("NickName")))
				continue;
			MessageTools.sendMsgById(msg.getContent(), o.getString("UserName"));
		}
		logger.info(MessageConstant.MASS_SUCCESS);
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
}
