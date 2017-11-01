package com.hzz.ui;

import com.hzz.service.MessageConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class CommandUI extends JFrame implements ActionListener{
    private JPanel left=new JPanel();
    private JPanel right=new JPanel();
    private JTextArea jTextArea=new JTextArea(35,35);
    private JScrollPane scrollPane;


    public CommandUI(JFrame main){
        this.setTitle("命令设置");
        this.setSize(620,630);

        left.setBounds(5,0,400,600);
        left.setBorder(BorderFactory.createTitledBorder("助手命令大全"));
        right.setBounds(410,0,200,600);
        right.setBorder(BorderFactory.createTitledBorder("添加命令"));

        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);

        scrollPane=new JScrollPane(jTextArea);
        scrollPane.setBorder(null);
        setLeft();
        left.add(scrollPane);
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

    private void setLeft() {
        jTextArea.setText(MessageConstant.CMD_HELP);
    }

    public static void main(String []args){
        new CommandUI(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
