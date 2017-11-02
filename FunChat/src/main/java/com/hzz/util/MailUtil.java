package com.hzz.util;

import com.hzz.beans.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class MailUtil {
    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    public static String toChinese(String text) {
        try {
            text = MimeUtility.encodeText(new String(text.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static boolean sendMail(MailBean mb) {
        String host = mb.getHost();
        final String username = mb.getUsername();
        final String password = mb.getPassword();
        String from = mb.getFrom();
        String to = mb.getTo();
        String subject = mb.getSubject();
        String content = mb.getContent();
        String fileName = mb.getFilename();
        Vector<String> file = mb.getFile();

        Properties props = new Properties();
        props.put("mail.smtp.host", host); // 设置SMTP的主机
        props.put("mail.smtp.auth", "true"); // 需要经过验证
        props.put("mail.smtp.timeout", "8000000");


        Session session = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(toChinese(subject));

            Multipart mp = new MimeMultipart();
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(content);
            mp.addBodyPart(mbpContent);

                    /* 往邮件中添加附件 */
            if (file != null) {
                Enumeration<String> efile = file.elements();
                while (efile.hasMoreElements()) {
                    MimeBodyPart mbpFile = new MimeBodyPart();
                    fileName = efile.nextElement().toString();
                    FileDataSource fds = new FileDataSource(fileName);
                    mbpFile.setDataHandler(new DataHandler(fds));
                    mbpFile.setFileName(toChinese(fds.getName()));
                    mp.addBodyPart(mbpFile);
                }
                logger.info("添加成功");
            }

            msg.setContent(mp);
            msg.setSentDate(new Date());
            Transport.send(msg);

        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MailBean mb = new MailBean();
        mb.setHost("smtp.163.com");
        mb.setUsername("18903811375@163.com");
        mb.setPassword("fj916693");
        mb.setFrom(MailUtil.toChinese("趣聊助手")+"<18903811375@163.com>");
        mb.setTo("415354918@qq.com");
        mb.setContent("本邮件中包含1个附件，请检查！");
        mb.attachFile("D:\\FunChatData\\file\\112.avi");
        MailUtil sm = new MailUtil();
        mb.setSubject(sm.toChinese("测试_JavaMail"));
        System.out.println("正在发送邮件...");
        if (sm.sendMail(mb)) {
            System.out.println("发送成功!");
        } else {
            System.out.println("发送失败!");
        }
    }


}
