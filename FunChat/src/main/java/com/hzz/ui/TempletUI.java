package com.hzz.ui;

import com.hzz.util.CommonUtils;
import com.hzz.util.DataUtil;
import com.hzz.util.PropertiesUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class TempletUI extends JFrame implements ActionListener{
    private JLabel label1=new JLabel("消息设置:");
    private JButton add=new JButton("加行");
    private JButton delete =new JButton("减行");
    private JButton submit=new JButton("提交");
    private JPanel left=new JPanel();
    private JPanel right=new JPanel();
    private JTextArea templetArea=new JTextArea(35,15);
    private JScrollPane scrollPane;
    private JLabel label2=null;
    private JLabel label3=null;
    private JTextField textField1=null;
    private JTextField textField2=null;
    private java.util.List<JTextField> keyLine=new ArrayList<JTextField>();
    private java.util.List<JTextField> valueLine=new ArrayList<JTextField>();
    private java.util.List<JLabel> label2s=new ArrayList<JLabel>();
    private java.util.List<JLabel> label3s=new ArrayList<JLabel>();
    private int y=60;
    private void addLine(){
        if(y>520)
            return;
        label2=new JLabel("消息：");
        label3=new JLabel("回复：");
        textField1=new JTextField();
        textField2=new JTextField();
        label2.setBounds(20,y,60,30);
        textField1.setBounds(70,y,100,30);
        label3.setBounds(190,y,60,30);
        textField2.setBounds(250,y,100,30);
        label2s.add(label2);
        label3s.add(label3);
        keyLine.add(textField1);
        valueLine.add(textField2);
        left.add(label2);
        left.add(label3);
        left.add(textField1);
        left.add(textField2);
        left.repaint();
        y+=40;
    }

    private void deleteLine(){
        if(keyLine.size()<=0)
            return;
        y-=40;
        left.remove(label2s.get(label2s.size()-1));
        label2s.remove(label2s.size()-1);
        left.remove(label3s.get(label3s.size()-1));
        label3s.remove(label3s.size()-1);
        left.remove(keyLine.get(keyLine.size()-1));
        keyLine.remove(keyLine.size()-1);
        left.remove(valueLine.get(valueLine.size()-1));
        valueLine.remove(valueLine.size()-1);
        left.repaint();
    }



    private void submit(){
        for(int i=0;i<valueLine.size();i++){
            String key=keyLine.get(i).getText();
            String value=valueLine.get(i).getText();
            if(key==null||key.equals("")||value==null||value.equals(""))
                continue;
            DataUtil.templetMap.put(key,value);
            PropertiesUtils.writeProperties(CommonUtils.diskPath.getPropertiesPath()+ File.separator+"templet.properties",key,value);
//            PropertiesUtils.writeProperties("D:\\templet.properties",key,value);
        }
        JOptionPane.showMessageDialog(this,"设置成功");
        setRight();
    };

    private void setRight(){
        StringBuilder sb=new StringBuilder();
        Iterator<String> iterator=DataUtil.templetMap.keySet().iterator();

        while(iterator.hasNext()){
            String key= iterator.next();
            String value=DataUtil.templetMap.get(key);
            sb.append("消息 :");
            sb.append(key);
            sb.append("\r\n");
            sb.append("回复 :");
            sb.append(value);
            sb.append("\r\n");
        }
        templetArea.setText(sb.toString());
        right.repaint();
    }


    private void setLeft(){
        label1.setBounds(20, 10, 100, 30);
        add.setBounds(120,10,60,30);
        add.setContentAreaFilled(false);
        add.setActionCommand("add");
        add.addActionListener(this);
        delete.setBounds(190,10,60,30);
        delete.setContentAreaFilled(false);
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        submit.setBounds(260,10,60,30);
        submit.setContentAreaFilled(false);
        submit.setActionCommand("submit");
        submit.addActionListener(this);
        left.add(label1);
        left.add(add);
        left.add(delete);
        left.add(submit);
    }

    public TempletUI(JFrame main){
        this.setTitle("自动回复消息设置");
        this.setSize(620,630);
        templetArea.setLineWrap(true);
        templetArea.setEditable(false);
        scrollPane=new JScrollPane(templetArea);
        scrollPane.setBorder(null);
        right.add(scrollPane);
        left.setBounds(5,0,400,600);
        left.setBorder(BorderFactory.createTitledBorder("操作"));
        right.setBounds(410,0,200,600);
        right.setBorder(BorderFactory.createTitledBorder("当前模板"));
        right.setBackground(Color.WHITE);

        setLeft();
        setRight();

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

    public static void main(String []args){
        new TempletUI(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("add")){
            addLine();
        }else if(command.equals("delete")){
            deleteLine();
        }else if(command.equals("submit")){
            submit();
        }

    }
}
