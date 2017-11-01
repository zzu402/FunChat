package com.hzz.ui.listener;

import com.hzz.util.DataUtil;

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
            DataUtil.commandSwitch.setAutoChat(true);
        }
        if(command.equals("autoChatoff")){
            DataUtil.commandSwitch.setAutoChat(false);
        }
        if(command.equals("saveMsgon")){
            DataUtil.commandSwitch.setSaveMessage(true);
        }
        if(command.equals("saveMsgoff")){
            DataUtil.commandSwitch.setSaveMessage(false);
        }
        if(command.equals("savePicon")){
            DataUtil.commandSwitch.setSavePic(true);
        }
        if(command.equals("savePicoff")){
            DataUtil.commandSwitch.setSavePic(false);
        }
        if(command.equals("saveVideoon")){
            DataUtil.commandSwitch.setSaveVideo(true);
        }
        if(command.equals("saveVideooff")){
            DataUtil.commandSwitch.setSaveVideo(false);
        }
        if(command.equals("saveVoiceon")){
            DataUtil.commandSwitch.setSaveVoice(true);
        }
        if(command.equals("saveVoiceoff")){
            DataUtil.commandSwitch.setSaveVoice(false);
        }
        if(command.equals("saveMediaon")){
            DataUtil.commandSwitch.setSaveMedia(true);
        }
        if(command.equals("saveMediaoff")){
            DataUtil.commandSwitch.setSaveMedia(false);
        }
        if(command.equals("sendon")){
            DataUtil.commandSwitch.setMassSend(true);
        }
        if(command.equals("sendoff")){
            DataUtil.commandSwitch.setMassSend(false);
        }
        if(command.equals("sendGroupon")){
            DataUtil.commandSwitch.setMassSendGroup(true);
        }
        if(command.equals("sendGroupoff")){
            DataUtil.commandSwitch.setMassSendGroup(false);
        }
        if(command.equals("downloadFileon")){
            DataUtil.commandSwitch.setDownloadFile(true);
        }
        if(command.equals("downloadFileoff")){
            DataUtil.commandSwitch.setDownloadFile(false);
        }
        if(command.equals("uploadFileon")){
            DataUtil.commandSwitch.setUploadFile(true);
        }
        if(command.equals("uploadFileoff")){
            DataUtil.commandSwitch.setUploadFile(false);
        }
        if(command.equals("controlPcon")){
            DataUtil.commandSwitch.setControlPc(true);
        }
        if(command.equals("controlPcoff")){
            DataUtil.commandSwitch.setControlPc(false);
        }
    }
}
