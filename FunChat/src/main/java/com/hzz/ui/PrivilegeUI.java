package com.hzz.ui;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import com.hzz.enums.PrivilegeEnum;
import com.hzz.util.CommonUtils;
import com.hzz.util.DataUtil;
import com.hzz.util.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;


/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class PrivilegeUI extends JFrame implements ActionListener,ItemListener{

    private JPanel right=new JPanel();
    private JPanel nickName =new JPanel();
    private JPanel privileges =new JPanel();
    private JLabel label1=new JLabel("选择好友或群组");
    private JComboBox<String> friendSelect;
    private JCheckBox[] cbs=new JCheckBox[PrivilegeEnum.values().length];
    private String values=null;
    private JButton jb=new JButton("提交");
    private JTextArea privilegeArea=new JTextArea(35,15);
    private JScrollPane scrollPane2;
    private String nick;



    private void setNickName(){

        label1.setBounds(10,20,50,30);
        friendSelect=new JComboBox();
        for(int i=0;i<WechatTools.getContactNickNameList().size();i++){
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
        nickName.setLayout(new GridLayout(15,2));
        nickName.add(label1);
        nickName.add(friendSelect);

    }


    public PrivilegeUI(JFrame main){
        this.setTitle("权限设置");
        this.setSize(620,630);
        nickName.setBounds(5,0,200,600);
        nickName.setBorder(BorderFactory.createTitledBorder("好友及群组名单"));
        privileges.setBounds(210,0,180,600);
        privileges.setBorder(BorderFactory.createTitledBorder("权限项"));
        right.setBounds(410,0,200,600);
        right.setBorder(BorderFactory.createTitledBorder("权限名单"));
        privilegeArea.setLineWrap(true);
        privilegeArea.setEditable(false);
        scrollPane2=new JScrollPane(privilegeArea);
        scrollPane2.setBorder(null);
        right.add(scrollPane2);
        setRight();
        setNickName();
        addPrivilege();
        setPrivileges(nick);
        this.add(privileges);
        this.add(nickName);
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

    private void setRight() {
        StringBuilder sb=new StringBuilder();
        Iterator<String> iterator= DataUtil.privilegeMap.keySet().iterator();

        while(iterator.hasNext()){
            String key= iterator.next();
            String value=DataUtil.privilegeMap.get(key);
            sb.append("用户 :");
            sb.append(key);
            sb.append("\r\n");
            sb.append("权限 :");
            sb.append(value);
            sb.append("\r\n");
        }
        privilegeArea.setText(sb.toString());
        right.repaint();
    }

    private void addPrivilege() {
        PrivilegeEnum[]privilegeEnums=PrivilegeEnum.values();
        for(int i=0;i<privilegeEnums.length;i++){
            PrivilegeEnum privilegeEnum=privilegeEnums[i];
            JCheckBox jcb=new JCheckBox(privilegeEnum.getValue());
            cbs[i]=jcb;
            privileges.add(jcb);
        }
        jb.setActionCommand("submit");
        jb.setContentAreaFilled(false);
        jb.addActionListener(this);
        privileges.add(jb);
        privileges.repaint();
    }
    private void getPrivilegeStatus(){
        PrivilegeEnum[]privilegeEnums=PrivilegeEnum.values();
        for(int i=0;i<privilegeEnums.length;i++){
            PrivilegeEnum privilegeEnum=privilegeEnums[i];
            if(values!=null&&values.contains(privilegeEnum.getValue())){
                cbs[i].setSelected(true);
            }else if(values!=null&&!values.contains(privilegeEnum.getValue()))
                cbs[i].setSelected(false);
        }

        privileges.repaint();
    }

    public static void main(String []args){
        new PrivilegeUI(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        StringBuilder sb=new StringBuilder();
        if(command.equals("submit")){
            for(int i=0;i<cbs.length-1;i++){
                JCheckBox jc=cbs[i];
                if(jc.isSelected()){
                    if(jc.getText().equals(PrivilegeEnum.BLACK.getValue())){
                        DataUtil.blackList.add(nick);
                    }
                    sb.append(jc.getText()+"-");
                }

            }
            if(cbs[cbs.length-1].isSelected()){
                sb.append(cbs[cbs.length-1].getText());
            }
            if(sb.toString().length()>0){
                PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()
                        + File.separator + "privilege.properties",nick,sb.toString());
                DataUtil.privilegeMap.put(nick,sb.toString());
                setRight();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED){
            nick=(String)friendSelect.getSelectedItem();
            setPrivileges(nick);
        }
    }
    public void setPrivileges(String nickName) {
       values= PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator + "privilege.properties",nickName,null);
        getPrivilegeStatus();
    }
}
