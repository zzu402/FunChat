package com.hzz.ui.listener;

import com.hzz.service.MessageService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class RadioButtonActionListener implements ActionListener{


    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("autoChaton")){
            MessageService.getCommandSwitch().setAutoChat(true);
        }
        if(command.equals("autoChatoff")){
            MessageService.getCommandSwitch().setAutoChat(false);
        }
        if(command.equals("saveMsgon")){
            MessageService.getCommandSwitch().setSaveMessage(true);
        }
        if(command.equals("saveMsgoff")){
            MessageService.getCommandSwitch().setSaveMessage(false);
        }
        if(command.equals("savePicon")){
            MessageService.getCommandSwitch().setSavePic(true);
        }
        if(command.equals("savePicoff")){
            MessageService.getCommandSwitch().setSavePic(false);
        }
        if(command.equals("saveVideoon")){
            MessageService.getCommandSwitch().setSaveVideo(true);
        }
        if(command.equals("saveVideooff")){
            MessageService.getCommandSwitch().setSaveVideo(false);
        }
        if(command.equals("saveVoiceon")){
            MessageService.getCommandSwitch().setSaveVoice(true);
        }
        if(command.equals("saveVoiceoff")){
            MessageService.getCommandSwitch().setSaveVoice(false);
        }
        if(command.equals("saveMediaon")){
            MessageService.getCommandSwitch().setSaveMedia(true);
        }
        if(command.equals("saveMediaoff")){
            MessageService.getCommandSwitch().setSaveMedia(false);
        }
        if(command.equals("sendon")){
            MessageService.getCommandSwitch().setMassSend(true);
        }
        if(command.equals("sendoff")){
            MessageService.getCommandSwitch().setMassSend(false);
        }
        if(command.equals("sendGroupon")){
            MessageService.getCommandSwitch().setMassSendGroup(true);
        }
        if(command.equals("sendGroupoff")){
            MessageService.getCommandSwitch().setMassSendGroup(false);
        }
        if(command.equals("downloadFileon")){
            MessageService.getCommandSwitch().setDownloadFile(true);
        }
        if(command.equals("downloadFileoff")){
            MessageService.getCommandSwitch().setDownloadFile(false);
        }
        if(command.equals("uploadFileon")){
            MessageService.getCommandSwitch().setUploadFile(true);
        }
        if(command.equals("uploadFileoff")){
            MessageService.getCommandSwitch().setUploadFile(false);
        }
        if(command.equals("controlPcon")){
            MessageService.getCommandSwitch().setControlPc(true);
        }
        if(command.equals("controlPcoff")){
            MessageService.getCommandSwitch().setControlPc(false);
        }
    }
}
