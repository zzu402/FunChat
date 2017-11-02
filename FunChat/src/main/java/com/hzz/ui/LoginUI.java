package com.hzz.ui;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.SleepUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/30
 */
public class LoginUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private JLabel label;
    private Icon icon;
    private Image image;

    public LoginUI(String path){
        try{
            setTitle("扫描进行登陆");
            setSize(310,340);
            label = new JLabel();
            add(label);
            image=Toolkit.getDefaultToolkit().getImage(path);
            icon=new ImageIcon(image.getScaledInstance(300,300,Image.SCALE_DEFAULT));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);// 设置窗口不可缩放
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                    super.windowClosing(e);
                }
            });

        }catch(Exception e){
            System.out.println("初始化失败"+e.getMessage());
        }
        label.setIcon(icon);
        redict();
    }

    private void redict(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Core.getInstance().isAlive()){
                    SleepUtils.sleep(400);
                }
                while(Core.getInstance().getNickName()==null){
                    SleepUtils.sleep(400);
                }
                LoginUI.this.setVisible(false);
                MainUI mainUI=new MainUI();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            SleepUtils.sleep(2000);
                            mainUI.updateButtonGroup();
                            mainUI.setRight();
                        }
                    }
                }).start();
            }
        }).start();;
    }
}
