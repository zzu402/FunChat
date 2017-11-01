package com.hzz.ui;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.SleepUtils;
import com.hzz.service.MessageService;
import com.hzz.ui.listener.ButtonActionListener;
import com.hzz.ui.listener.RadioButtonActionListener;
import javafx.scene.control.RadioButton;
import sun.plugin.dom.core.CoreConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainUI  {

	public static void main(String []args){
		MainUI UI=new MainUI();
//		SleepUtils.sleep(5000);
//		MessageService.getCommandSwitch().setAutoChat(true);
//		UI.updateButtonGroup();
	}
    private JPanel right=new JPanel();
	private static final long serialVersionUID = 1L;
	private JFrame mainForm = new JFrame("微信趣聊助手-v1.1"); // 主窗体，”
	private JLabel label1 = new JLabel("开关设置:");
	private JLabel label2=null;
	private JLabel label3 = new JLabel("自定义消息回复:");
    private JLabel label4 = new JLabel("群发消息设置:");
    private JLabel label5 = new JLabel("手机命令设置:");
    private JLabel label6 = new JLabel("访问权限设置:");
	private JRadioButton on=null;
	private JRadioButton off =null;
	private JButton templetButton=new JButton("设置模版");
    private JButton sendButton=new JButton("设置群发");
    private JButton commandButton=new JButton("设置命令");
    private JButton privilegeButton=new JButton("设置权限");

    private JTextArea messageArea=new JTextArea(35,25);
    private JScrollPane scrollPane;

	private ButtonGroup bg=null;
	private java.util.List<ButtonGroup> bgList= new ArrayList<>();
	private Container container ;
	private ButtonActionListener buttonActionListener=new ButtonActionListener(mainForm);
	private RadioButtonActionListener radioButtonActionListener=new RadioButtonActionListener();
	private int y=30;
	private void addButtonGroup(String labelName,String command,boolean turnOn){
		on=new JRadioButton("开",turnOn);
		off =new JRadioButton("关",!turnOn);
		label2 = new JLabel(labelName);
		bg=new ButtonGroup();
		bg.add(on);
		bg.add(off);
		bgList.add(bg);
		label2.setBounds(100,y,100,30);
		on.setBounds(200,y,50,30);
		off.setBounds(260,y,50,30);
		on.setActionCommand(command+"on");
		on.addActionListener(radioButtonActionListener);
		off.setActionCommand(command+"off");
		off.addActionListener(radioButtonActionListener);
		container.add(label2);
		container.add(on);
		container.add(off);
		y+=30;
	}
	public void updateButtonGroup(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<bgList.size();i++) {
					bg=bgList.get(i);
					Enumeration enumeration = bg.getElements();
					while (enumeration.hasMoreElements()) {
						JRadioButton jRadioButton = (JRadioButton) enumeration.nextElement();
						String command=jRadioButton.getActionCommand();
						if(command.equals("autoChaton")&&MessageService.getCommandSwitch().isAutoChat()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("autoChatoff")&&!MessageService.getCommandSwitch().isAutoChat()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveMsgon")&&MessageService.getCommandSwitch().isSaveMessage()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveMsgoff")&&!MessageService.getCommandSwitch().isSaveMessage()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("savePicon")&&MessageService.getCommandSwitch().isSavePic()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("savePicoff")&&!MessageService.getCommandSwitch().isSavePic()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveVideoon")&&MessageService.getCommandSwitch().isSaveVideo()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveVideooff")&&!MessageService.getCommandSwitch().isSaveVideo()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveVoiceon")&&MessageService.getCommandSwitch().isSaveVoice()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveVoiceoff")&&!MessageService.getCommandSwitch().isSaveVoice()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveMediaon")&&MessageService.getCommandSwitch().isSaveMedia()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("saveMediaoff")&&!MessageService.getCommandSwitch().isSaveMedia()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("sendon")&&MessageService.getCommandSwitch().isMassSend()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("sendoff")&&!MessageService.getCommandSwitch().isMassSend()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("sendGroupon")&&MessageService.getCommandSwitch().isMassSendGroup()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("sendGroupoff")&&!MessageService.getCommandSwitch().isMassSendGroup()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("downloadFileon")&&MessageService.getCommandSwitch().isDownloadFile()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("downloadFileoff")&&!MessageService.getCommandSwitch().isDownloadFile()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("uploadFileon")&&MessageService.getCommandSwitch().isUploadFile()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("uploadFileoff")&&!MessageService.getCommandSwitch().isUploadFile()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("controlPcon")&&MessageService.getCommandSwitch().isControlPc()){
							jRadioButton.setSelected(true);
						}
						if(command.equals("controlPcoff")&&!MessageService.getCommandSwitch().isControlPc()){
							jRadioButton.setSelected(true);
						}
						jRadioButton.paintImmediately(jRadioButton.getBounds());
					}
				}
			}
		}).start();;
	}
	private void addButtonGroups(){
		addButtonGroup("自动聊天","autoChat",MessageService.getCommandSwitch().isAutoChat());
		addButtonGroup("消息备份","saveMsg",MessageService.getCommandSwitch().isSaveMessage());
		addButtonGroup("图片保存","savePic",MessageService.getCommandSwitch().isSavePic());
		addButtonGroup("视频保存","saveVideo",MessageService.getCommandSwitch().isSaveVideo());
		addButtonGroup("音频保存","saveVoice",MessageService.getCommandSwitch().isSaveVoice());
		addButtonGroup("文件保存","saveMedia",MessageService.getCommandSwitch().isSaveMedia());
		addButtonGroup("通讯录群发","send",MessageService.getCommandSwitch().isMassSend());
		addButtonGroup("群组群发","sendGroup",MessageService.getCommandSwitch().isMassSendGroup());
		addButtonGroup("下载文件","downloadFile",MessageService.getCommandSwitch().isDownloadFile());
		addButtonGroup("上传文件","uploadFile",MessageService.getCommandSwitch().isUploadFile());
		addButtonGroup("控制电脑","controlPc",MessageService.getCommandSwitch().isControlPc());
	}

	public void setRight(){
		StringBuilder sb=new StringBuilder();
        for(int i=0;i<MessageService.messageList.size();i++){
			sb.append(MessageService.messageList.get(i));
        }
		messageArea.setText(sb.toString());
        right.repaint();
    }


	public MainUI() {
		container=mainForm.getContentPane();     /* 设置主窗体属性 */
		mainForm.setSize(620, 630);// 设置主窗体大小
        right.setBounds(310,0,300,600);
        right.setBorder(BorderFactory.createTitledBorder("消息记录"));

         /* 设置各元素位置布局 */
        label1.setBounds(30, 10, 100, 30);
		addButtonGroups();
		y+=15;
        addLine(label3,templetButton,"templet");
        addLine(label4,sendButton,"send");
        addLine(label5,commandButton,"command");
        addLine(label6,privilegeButton,"privilege");
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        scrollPane=new JScrollPane(messageArea);
        scrollPane.setBorder(null);
        right.add(scrollPane);
        container.add(label1);
        container.add(right);
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置主窗体关闭按钮样式
		mainForm.setLocationRelativeTo(null);// 设置居于屏幕中央
		mainForm.setResizable(false);// 设置窗口不可缩放
		mainForm.setLayout(null);
		mainForm.setVisible(true);// 显示窗口
	}
	private void addLine(JLabel jLabel,JButton jButton,String command){
        jLabel.setBounds(30,y,100,30);
        jButton.setBounds(210,y,90,30);
        jButton.setActionCommand(command);
        jButton.setContentAreaFilled(false);
        jButton.addActionListener(buttonActionListener);
        y+=40;
        container.add(jLabel);
        container.add(jButton);
    }
}
