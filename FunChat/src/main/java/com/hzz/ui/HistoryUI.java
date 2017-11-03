package com.hzz.ui;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import com.hzz.util.CommonUtils;
import com.hzz.util.DataUtil;
import com.hzz.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class HistoryUI extends JFrame implements ItemListener,ActionListener {

    private JPanel left=new JPanel();
    private JPanel right=new JPanel();
    private JTextArea messageArea=new JTextArea(36,35);
    private JScrollPane scrollPane;
    private JComboBox<String> friendSelect;
    private JLabel label1=new JLabel("选择好友或群组");
    private JLabel label2=new JLabel("按条件搜索");
    private java.util.List<String>messageList;
    private String nick;
    private JTextField jTextField=new JTextField(10);
    private JButton search=new JButton("点击搜索");


    private void setNickName(){
        label1.setBounds(10,20,50,30);
        friendSelect=new JComboBox();
        for(int i = 0; i< WechatTools.getContactNickNameList().size(); i++){
            String nickName=WechatTools.getContactRemarkNameList().get(i);
            if(nickName.equals("")||nickName==null)
                nickName=WechatTools.getContactNickNameList().get(i);
            friendSelect.addItem(nickName);
        }
        for(int i=0;i<WechatTools.getGroupNickNameList().size();i++){
            friendSelect.addItem(WechatTools.getGroupNickNameList().get(i));
        }
        if(WechatTools.getContactNickNameList().size()>0) {
            nick=WechatTools.getContactRemarkNameList().get(0);
            if(nick.equals("")||nick==null)
                nick=WechatTools.getContactNickNameList().get(0);
        }
        friendSelect.addItemListener(this);
        right.setLayout(new GridLayout(15,2));
        right.add(label1);
        right.add(friendSelect);

        right.add(label2);
        right.add(jTextField);
        search.setContentAreaFilled(false);
        search.setActionCommand("search");
        search.addActionListener(this);
        right.add(search);
    }

    public void setLeft(){
        StringBuilder sb=new StringBuilder();
        if(messageList!=null){
            for(int i = 0; i< messageList.size(); i++){
                sb.append(messageList.get(i)+"\r\n");
            }
        }else{
            sb.append("您和"+nick+"当前并没有消息记录保存在本地！");
        }
        messageArea.setText(sb.toString());
        right.repaint();
    }

    public HistoryUI(JFrame main){
        this.setTitle("历史消息");
        this.setSize(620,630);
        left.setBounds(5,0,400,600);
        left.setBorder(BorderFactory.createEtchedBorder());
        left.setBorder(BorderFactory.createTitledBorder("消息栏"));
        right.setBounds(410,0,200,600);
        right.setBackground(Color.WHITE);
        right.setBorder(BorderFactory.createTitledBorder("好友名单"));
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        scrollPane=new JScrollPane(messageArea);
        scrollPane.setBorder(null);
        left.add(scrollPane);
        setNickName();
        getHistoryByNick(nick);
        setLeft();
        this.add(left);
        this.add(right);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);// 设置主窗体关闭按钮样式
        this.setLocationRelativeTo(null);// 设置居于屏幕中央
        this.setResizable(false);// 设置窗口不可缩放
        this.setLayout(null);
        this.setVisible(true);// 显示窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (main!=null)
                    main.setVisible(true);
                super.windowClosing(e);
            }
        });
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED){
            nick=(String)friendSelect.getSelectedItem();
            getHistoryByNick(nick);
            setLeft();
        }
    }

    private void getHistoryByNick(String nick) {
        String path = CommonUtils.diskPath.getMessagePath() + File.separator
                + CommonUtils.getNickByUserName(Core.getInstance().getUserName())+File.separator + nick
                + ".txt";
        messageList= FileUtil.readTextFile(path);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("search")){
            nick=jTextField.getText();
            getHistoryByNick(nick);
            setLeft();
        }

    }
}
