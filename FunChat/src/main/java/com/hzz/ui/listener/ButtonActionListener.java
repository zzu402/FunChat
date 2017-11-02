package com.hzz.ui.listener;

import com.hzz.ui.*;
import org.omg.CORBA.MARSHAL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/31
 */
public class ButtonActionListener implements ActionListener{
    private JFrame main;

    public ButtonActionListener(JFrame main){
        this.main=main;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("templet")){
            main.setVisible(false);
            new TempletUI(main);
        }else if(command.equals("send")){
            main.setVisible(false);
            new SendUI(main);
        }else if (command.equals("command")){
            main.setVisible(false);
            new CommandUI(main);
        }else if(command.equals("privilege")){
            main.setVisible(false);
            new PrivilegeUI(main);
        }else if(command.equals("history")){
            main.setVisible(false);
            new HistoryUI(main);
        }

    }
}
