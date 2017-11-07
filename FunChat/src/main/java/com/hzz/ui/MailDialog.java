package com.hzz.ui;

import com.hzz.util.CommonUtils;
import com.hzz.util.DataUtil;
import com.hzz.util.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MailDialog extends JDialog implements ActionListener {

    private JLabel host=new JLabel("邮箱主机：");
    private JLabel userName=new JLabel("邮箱账户：");
    private JLabel password=new JLabel("邮箱密码：");
    private JLabel from=new JLabel("发送账户：");
    private JLabel mailContent=new JLabel("邮件内容：");
    private JLabel subject=new JLabel("邮件主题：");

    private JTextField hostFiled=new JTextField(10);
    private JTextField userNameFiled=new JTextField(10);
    private JTextField passwordFiled=new JTextField(10);
    private JTextField fromFiled=new JTextField(10);
    private JTextField subjectFiled=new JTextField(10);
    private JTextArea mailContentArea=new JTextArea(4,10);
    private JButton button=new JButton("确定");
    public MailDialog(){
        setTitle("邮箱设置");
        setSize(400, 400);//对话框的大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//关闭后销毁对话框
        setLocationRelativeTo(null);
        JPanel dialogPanel=new JPanel(new GridLayout(6,2));
        hostFiled.setText(DataUtil.mailBean.getHost());
        userNameFiled.setText(DataUtil.mailBean.getUsername());
        passwordFiled.setText(DataUtil.mailBean.getPassword());
        fromFiled.setText(DataUtil.mailBean.getFrom());
        subjectFiled.setText(DataUtil.mailBean.getSubject());
        mailContentArea.setText(DataUtil.mailBean.getContent());
        dialogPanel.add(host);
        dialogPanel.add(hostFiled);
        dialogPanel.add(userName);
        dialogPanel.add(userNameFiled);
        dialogPanel.add(password);
        dialogPanel.add(passwordFiled);
        dialogPanel.add(from);
        dialogPanel.add(fromFiled);
        dialogPanel.add(subject);
        dialogPanel.add(subjectFiled);
        dialogPanel.add(mailContent);
        dialogPanel.add(mailContentArea);
        button.setContentAreaFilled(false);
        button.setActionCommand("submit");
        button.addActionListener(this);
        add(dialogPanel);
        add(button,BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("submit")){
                String h=hostFiled.getText();
                String u=userNameFiled.getText();
                String p=passwordFiled.getText();
                String f=fromFiled.getText();
                String s=subjectFiled.getText();
                String c=mailContentArea.getText();

                DataUtil.mailBean.setHost(h);
                DataUtil.mailBean.setUsername(u);
                DataUtil.mailBean.setPassword(p);
                DataUtil.mailBean.setFrom(f);
                DataUtil.mailBean.setSubject(s);
                DataUtil.mailBean.setContent(c);

                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","host",h);
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","userName",u);
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","password",p);
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","from",f);
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","subject",s);
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator +"mail_config.properties","mailContent",c);

                JOptionPane.showMessageDialog(this,"邮箱配置成功！");



            }
    }
}
