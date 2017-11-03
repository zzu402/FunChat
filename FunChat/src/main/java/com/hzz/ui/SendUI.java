package com.hzz.ui;

import cn.zhouyafeng.itchat4j.utils.SleepUtils;
import com.hzz.service.implement.MessageServiceImpl;
import com.hzz.util.DataUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class SendUI extends JFrame implements ActionListener{

    private JButton submit=new JButton("立即发送");
    private JButton shedule=new JButton("定时发送");
    private JPanel left=new JPanel();
    private JPanel right=new JPanel();
    private JTextArea textArea=new JTextArea(10,35);
    private JTextArea blackArea=new JTextArea(35,15);
    private JScrollPane scrollPane;

    private void submit(){
        String content=textArea.getText();
        if(content.length()>0)
            MessageServiceImpl.massSend(content);
    };
    public void setRight(){
        StringBuilder sb=new StringBuilder();
        for(int i = 0; i< DataUtil.blackList.size(); i++){
            sb.append(DataUtil.blackList.get(i)+"\r\n");

        }
        blackArea.setText(sb.toString());
        right.repaint();
    }

    private void setLeft(){
        submit.setContentAreaFilled(false);
        submit.setActionCommand("submit");
        submit.addActionListener(this);
        shedule.setContentAreaFilled(false);
        shedule.setActionCommand("shedule");
        shedule.addActionListener(this);
        textArea.setBounds(20,100,200,200);
        textArea.setAutoscrolls(true);
        left.add(textArea);
        left.add(submit);
        left.add(shedule);
    }

    public SendUI(JFrame main){
        this.setTitle("群发消息设置");
        this.setSize(620,630);

        blackArea.setLineWrap(true);
        blackArea.setEditable(false);
        scrollPane=new JScrollPane(blackArea);
        scrollPane.setBorder(null);

        left.setBounds(5,0,400,600);
        left.setBorder(BorderFactory.createEtchedBorder());
        left.setBorder(BorderFactory.createTitledBorder("群发消息设置"));
        right.setBounds(410,0,200,600);
        right.setBackground(Color.WHITE);
        right.setBorder(BorderFactory.createTitledBorder("黑名单"));
        right.add(scrollPane);
        setRight();
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
    public static void main(String []args){
           new SendUI(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("submit")){
            submit();
        }
        if(command.equals("shedule")) {
            String input = JOptionPane.showInputDialog("输入时间（秒）:");
            Pattern pattern = Pattern.compile("[0-9]*");

            if (input != null) {
                Matcher isNum = pattern.matcher(input);
                if (isNum.matches()) {
                    String text = textArea.getText();
                    if(text==null||text.equals(""))
                        return;
                    Long time = Long.parseLong(input);
                    sendMessageByShedule(text, time);
                } else if (input != null && !isNum.matches()) {
                JOptionPane.showMessageDialog(this, "请输入正确的数字");
            }
         }
        }
    }

    private void sendMessageByShedule(String text, Long time) {
        Runnable runnable = new Runnable() {
            public void run() {
                SleepUtils.sleep(time*1000);//开上一个线程让它睡到指定时间
                MessageServiceImpl.massSend(text);
            }
        };
      new Thread(runnable).start();
    }
}
