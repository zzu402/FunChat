package com.hzz.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hzz.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void saveMsg(String selfName, String toUserName,
			String fromUserName, String text, boolean isGroupMsg,String msgUserNameInGroup) {
		
		String userName = CommonUtils
				.select(fromUserName, toUserName, selfName);// 选取非本人的id
		String nick=CommonUtils.getNickByUserName(userName);
		String path = CommonUtils.diskPath.getMessagePath() + File.separator
				+ nick;
		File file = new File(path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(new Date());
		path = path + File.separator + nick
				+ ".txt";
		if(isGroupMsg){
			nick=CommonUtils.getNickByMsgUserNameInGroup(fromUserName,msgUserNameInGroup);
		}else{
			nick=CommonUtils.getNickByUserName(fromUserName);
		}

		String msg = date + "\r\n"
				+ nick + " : " + text
				+ "\r\n";
		MessageService.messageList.add(msg);
		writeByFileReader(path, msg);
		logger.info("消息:" + msg + " 保存成功");
	}
	
	public static List<File> getFilesFromDir(String fileDir){
		
		List<File> fileList = new ArrayList<File>();  
        File file = new File(fileDir);  
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
        if (files == null) {// 如果目录为空，直接退出  
            return fileList;  
        }  
        // 遍历，目录下的所有文件  
        for (File f : files) {  
            if (f.isFile()) {  
                fileList.add(f);  
            } else if (f.isDirectory()) {  
                getFilesFromDir(f.getAbsolutePath());  
            }  
        }  
        return fileList;
	}

	private static void writeByFileReader(String path, String msg) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			fileWritter.write(msg);
			fileWritter.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}
	public static void main(String[]a){
		copyFile("G:/weixin/pic\\时间已逝\\玉梅\\2017-10-30-20-49-50.jpg","G:/weixin/file\\2017-10-30-20-49-57.jpg");
	}

	public static void copyFile(String fromFile, String toFile) {
		try {
			FileInputStream ins = new FileInputStream(fromFile);
			FileOutputStream out = new FileOutputStream(toFile);
			byte[] b = new byte[1024];
			int n=0;
			while((n=ins.read(b))!=-1){
				out.write(b, 0, n);
			}
			ins.close();
			out.close();
		}
		catch (Exception e) {
			System.out.println("复制单个文件操作出错");
		}

	}


}
