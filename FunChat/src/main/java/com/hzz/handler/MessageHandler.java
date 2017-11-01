package com.hzz.handler;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import com.hzz.beans.Operation;
import com.hzz.service.MessageService;
import com.hzz.service.PrivilegeEnum;
import com.hzz.util.CommonUtils;

import java.util.Date;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/27
 */
public class MessageHandler implements IMsgHandlerFace {
	private MyHttpClient httpClient = MyHttpClient.getInstance();
	private MessageService messageService = new MessageService();

	@Override
	public String textMsgHandle(BaseMsg baseMsg) {
		String fromUserName = baseMsg.getFromUserName();
		String nickName= CommonUtils.getNickByUserName(fromUserName);
		String selfName = Core.getInstance().getUserName();
		String text = baseMsg.getText();
		String result=null;
		messageService.setCommand(text, fromUserName, selfName);
		if (messageService.getCommandSwitch().isControlPc()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.CONTROL.getValue())) {// 是否控制电脑
			result = messageService.control(baseMsg);
			if (result != null) {
				if (messageService.getCommandSwitch().isSaveMessage())
					messageService.saveMsg(baseMsg,selfName,fromUserName,baseMsg.getToUserName(),result,baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
				return result;
			}
		}
		if (messageService.getCommandSwitch().isSaveMessage()) {// 是否启动消息备份
			messageService.saveMsg(baseMsg,selfName, baseMsg.getToUserName(), fromUserName,
					text, baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
		}
		if (messageService.getCommandSwitch().isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if (messageService.getCommandSwitch().isDownloadFile()) {
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.DOWNLOAD.getValue()))
				messageService.sendFileToUser(baseMsg);
			else if(text.startsWith("f-")){
				return "No Privilege";
			}
		}
		if (messageService.getCommandSwitch().isUploadFile()) {
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue())) {
				if (text.equalsIgnoreCase("upload")) {
					Operation operation=new Operation();
					operation.setAction("upload");
					operation.setRunner(fromUserName);
					operation.setLastOperation(System.currentTimeMillis());
					CommonUtils.operationList.put(fromUserName,operation);
					if (messageService.getCommandSwitch().isSaveMessage())
						messageService.saveMsg(baseMsg,selfName,baseMsg.getToUserName(),fromUserName,"等待上传文件...",baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
					return "等待上传文件...";
				}
			}
		}
		result=autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return result;
	}
	@Override
	public String picMsgHandle(BaseMsg baseMsg) {
		String result=null;
		Date nowDate=new Date();
		String fromUserName = baseMsg.getFromUserName();
		String nickName= CommonUtils.getNickByUserName(fromUserName);
		String selfName = Core.getInstance().getUserName();
		String text="这是一张图片消息。";
		if (messageService.getCommandSwitch().isSavePic()) {// 是否启动保存图片
			 messageService.savePic(baseMsg,nowDate);
		}
		if (messageService.getCommandSwitch().isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(messageService.getCommandSwitch().isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName, PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.PIC.getType(),nowDate);
		}
		result=autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return result;
	}
	public String autoChat(String result,String text,BaseMsg baseMsg,String selfName,String fromUserName,String nickName){
		if (messageService.getCommandSwitch().isAutoChat()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.AUTOCHAT.getValue())) {// 是否启动自动聊天
			result = messageService.getTempletValue(text);// 从消息模板里面获取
			if (result != null) {
				if (messageService.getCommandSwitch().isSaveMessage())
					messageService.saveMsg(baseMsg,selfName,fromUserName,baseMsg.getToUserName(),result,baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
				return result;
			}
			result= messageService.robot(httpClient,text);
			if (messageService.getCommandSwitch().isSaveMessage())
				messageService.saveMsg(baseMsg,selfName,fromUserName,baseMsg.getToUserName(),result,baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
			return result;
		}
		return null;
	}
	@Override
	public String voiceMsgHandle(BaseMsg baseMsg) {
		String result=null;
		Date nowDate=new Date();
		String fromUserName = baseMsg.getFromUserName();
		String nickName= CommonUtils.getNickByUserName(fromUserName);
		String selfName = Core.getInstance().getUserName();
		String text="这是一段声音。";
		if (messageService.getCommandSwitch().isSaveVoice()) {// 是否启动保存声音
			return messageService.saveVoice(baseMsg,nowDate);
		}
		if (messageService.getCommandSwitch().isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(messageService.getCommandSwitch().isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.VOICE.getType(),nowDate);
		}
		result=autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return result;
	}

	@Override
	public String viedoMsgHandle(BaseMsg baseMsg) {
		String result=null;
        Date nowDate=new Date();
		String fromUserName = baseMsg.getFromUserName();
		String nickName= CommonUtils.getNickByUserName(fromUserName);
		String selfName = Core.getInstance().getUserName();
		String text="这是一段视频。";
		if (messageService.getCommandSwitch().isSaveVideo()) {// 是否启动保存视频
			return messageService.saveVideo(baseMsg,nowDate);
		}
		if (messageService.getCommandSwitch().isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(messageService.getCommandSwitch().isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.VIEDO.getType(),nowDate);
		}
		autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return null;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg baseMsg) {
//		String result=null;
//		String fromUserName = baseMsg.getFromUserName();
//		String nickName= CommonUtils.getNickByUserName(fromUserName);
//		String selfName = Core.getInstance().getUserName();
//		String text="这是一张卡片。";
//		result=autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg baseMsg) {

	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg baseMsg) {
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg baseMsg) {
		String result=null;
		String fromUserName = baseMsg.getFromUserName();
		String nickName= CommonUtils.getNickByUserName(fromUserName);
		String selfName = Core.getInstance().getUserName();
        Date nowDate=new Date();
		String text="这是文件。";
		if (messageService.getCommandSwitch().isSaveMedia()) {// 是否启动保存文件
			return messageService.saveMedia(baseMsg,nowDate);
		}
		if (messageService.getCommandSwitch().isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(messageService.getCommandSwitch().isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.MEDIA.getType(),nowDate);
		}
		result=autoChat(result,text,baseMsg,selfName,fromUserName,nickName);
		return result;

	}
}
