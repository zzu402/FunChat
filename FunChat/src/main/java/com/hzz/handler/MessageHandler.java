package com.hzz.handler;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import com.hzz.beans.Operation;
import com.hzz.service.IMessageService;
import com.hzz.service.MessageConstant;
import com.hzz.service.implement.MessageServiceImpl;
import com.hzz.enums.PrivilegeEnum;
import com.hzz.util.CommonUtils;
import com.hzz.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/27
 */
public class MessageHandler implements IMsgHandlerFace {
	private static Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	private MyHttpClient httpClient = MyHttpClient.getInstance();
	private IMessageService messageService = new MessageServiceImpl();
	private String fromUserName =null;
	private String nickName=null;
	private String selfName=null;
	private String text = null;
	private String result=null;
	@Override
	public String textMsgHandle(BaseMsg baseMsg) {
		fromUserName = baseMsg.getFromUserName();
		nickName= CommonUtils.getNickByUserName(fromUserName);
		selfName = Core.getInstance().getUserName();
		text = baseMsg.getText();
		result=null;
		messageService.setCommand(text, fromUserName, selfName);
        if (DataUtil.commandSwitch.isSaveMessage()) {// 是否启动消息备份
            messageService.saveMsg(baseMsg,selfName, baseMsg.getToUserName(), fromUserName,
                    text, baseMsg.isGroupMsg(), baseMsg.getMsgFromUserNameInGroup());
        }
		if (DataUtil.commandSwitch.isControlPc()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.CONTROL.getValue())) {// 是否控制电脑
			result = messageService.control(baseMsg);
			if (result != null) {
				if (DataUtil.commandSwitch.isSaveMessage())
					messageService.saveMsg(baseMsg,selfName,fromUserName, baseMsg.getToUserName(),result, baseMsg.isGroupMsg(), baseMsg.getMsgFromUserNameInGroup());
				return result;
			}
		}
		if (DataUtil.commandSwitch.isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
			return null;//群发消息之后不再执行下面动作
		}
		if (DataUtil.commandSwitch.isDownloadFile()) {
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.DOWNLOAD.getValue())) {
				result = messageService.sendFileToUser(baseMsg);
				if (result != null) {
                    if(DataUtil.commandSwitch.isSaveMessage())
                        messageService.saveMsg(baseMsg,Core.getInstance().getUserName(),baseMsg.getFromUserName(),baseMsg.getToUserName(),result,baseMsg.isGroupMsg(),baseMsg.getMsgFromUserNameInGroup());
                    return result;
                }
			}
			else if(text.startsWith(MessageConstant.DOWNLOAD_CMD_PREFIX)){
				return  MessageConstant.NO_PRIVILEGE;
			}
		}
		if (DataUtil.commandSwitch.isUploadFile()) {
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue())) {
				if (text.equalsIgnoreCase(MessageConstant.UPLOAD_CMD)) {
					Operation operation=new Operation();
					operation.setAction(MessageConstant.UPLOAD_CMD);
					operation.setRunner(fromUserName);
					operation.setLastOperation(System.currentTimeMillis());
					CommonUtils.operationList.put(fromUserName,operation);
					if (DataUtil.commandSwitch.isSaveMessage())
						messageService.saveMsg(baseMsg,selfName, baseMsg.getToUserName(),fromUserName,MessageConstant.WAIT_FOR_UPLOAD, baseMsg.isGroupMsg(), baseMsg.getMsgFromUserNameInGroup());
					return  MessageConstant.WAIT_FOR_UPLOAD;
				}
			}
		}
		result=autoChat(result,text, baseMsg,selfName,fromUserName,nickName);
		return result;
	}
	@Override
	public String picMsgHandle(BaseMsg baseMsg) {
		Date nowDate=new Date();
		fromUserName = baseMsg.getFromUserName();
		nickName= CommonUtils.getNickByUserName(fromUserName);
		selfName = Core.getInstance().getUserName();
		text=MessageConstant.PIC_MESSAGE;
		if (DataUtil.commandSwitch.isSavePic()) {// 是否启动保存图片
			 messageService.savePic(baseMsg,nowDate);
		}
		if (DataUtil.commandSwitch.isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(DataUtil.commandSwitch.isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName, PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.PIC.getType(),nowDate);
		}
		result=autoChat(result,text, baseMsg,selfName,fromUserName,nickName);
		return result;
	}
	private String autoChat(String result,String text,BaseMsg baseMsg,String selfName,String fromUserName,String nickName){
		if (DataUtil.commandSwitch.isAutoChat()&&(!CommonUtils.isBlackList(nickName)||CommonUtils.hasPrivilege(nickName,PrivilegeEnum.AUTOCHAT.getValue()))) {// 是否启动自动聊天
			result = DataUtil.getTempletValue(text);// 从消息模板里面获取
			if (result != null) {
				if (DataUtil.commandSwitch.isSaveMessage())
					messageService.saveMsg(baseMsg,selfName,fromUserName, baseMsg.getToUserName(),result, baseMsg.isGroupMsg(), baseMsg.getMsgFromUserNameInGroup());
				return result;
			}
			result= messageService.robot(httpClient,text);
			if (DataUtil.commandSwitch.isSaveMessage())
				messageService.saveMsg(baseMsg,selfName,fromUserName, baseMsg.getToUserName(),result, baseMsg.isGroupMsg(), baseMsg.getMsgFromUserNameInGroup());
			return result;
		}
		return null;
	}
	@Override
	public String voiceMsgHandle(BaseMsg baseMsg) {
		result=null;
		Date nowDate=new Date();
		fromUserName = baseMsg.getFromUserName();
		nickName= CommonUtils.getNickByUserName(fromUserName);
		selfName = Core.getInstance().getUserName();
		text=MessageConstant.VOICE_MESSAGE;
		if (DataUtil.commandSwitch.isSaveVoice()) {// 是否启动保存声音
			return messageService.saveVoice(baseMsg,nowDate);
		}
		if (DataUtil.commandSwitch.isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(DataUtil.commandSwitch.isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.VOICE.getType(),nowDate);
		}
		result=autoChat(result,text, baseMsg,selfName,fromUserName,nickName);
		return result;
	}

	@Override
	public String videoMsgHandle(BaseMsg baseMsg) {
		result=null;
        Date nowDate=new Date();
		fromUserName = baseMsg.getFromUserName();
		nickName= CommonUtils.getNickByUserName(fromUserName);
		selfName = Core.getInstance().getUserName();
		text=MessageConstant.VIDEO_MESSAGE;
		if (DataUtil.commandSwitch.isSaveVideo()) {// 是否启动保存视频
			return messageService.saveVideo(baseMsg,nowDate);
		}
		if (DataUtil.commandSwitch.isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(DataUtil.commandSwitch.isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.VIEDO.getType(),nowDate);
		}
		result=autoChat(result,text, baseMsg,selfName,fromUserName,nickName);
		return result;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg baseMsg) {
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg baseMsg) {

	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg baseMsg) {
	    text=null;
	    if(DataUtil.commandSwitch.isVerifyFriend()){
            MessageTools.addFriend(baseMsg, true); // 同意好友请求，false为不接受好友请求
            RecommendInfo recommendInfo = baseMsg.getRecommendInfo();
            String nickName = recommendInfo.getNickName();
            String province = recommendInfo.getProvince();
            String city = recommendInfo.getCity();
            text = String.format(MessageConstant.VERIFY_FRIEND,province,city,nickName);
        }
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg baseMsg) {
		result=null;
		fromUserName = baseMsg.getFromUserName();
		nickName= CommonUtils.getNickByUserName(fromUserName);
		selfName = Core.getInstance().getUserName();
        Date nowDate=new Date();
		text=MessageConstant.MEDIA_MESSAGE;
		if (DataUtil.commandSwitch.isSaveMedia()) {// 是否启动保存文件
			return messageService.saveMedia(baseMsg,nowDate);
		}
		if (DataUtil.commandSwitch.isMassSend()&&CommonUtils.hasPrivilege(nickName,PrivilegeEnum.MASS.getValue())) {
			messageService.massSend(baseMsg);
		}
		if(DataUtil.commandSwitch.isUploadFile()){
			if(CommonUtils.hasPrivilege(nickName,PrivilegeEnum.UPLOAD.getValue()))
				messageService.saveFile(baseMsg, MsgTypeEnum.MEDIA.getType(),nowDate);
		}
		result=autoChat(result,text, baseMsg,selfName,fromUserName,nickName);
		return result;

	}
}
