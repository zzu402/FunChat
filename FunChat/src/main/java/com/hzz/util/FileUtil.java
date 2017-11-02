package com.hzz.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hzz.service.MessageConstant;

public class FileUtil {
	public static List<String> readTextFile(String path){
		List<String>textList=new ArrayList<>();
		File f=new File(path);
		if(!f.exists())
			return null;
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"GBK"));
			String text=br.readLine();
			while(text!=null){
				text=new String(text.getBytes(),"utf-8");
				textList.add(text);
				text=br.readLine();
			}
		} catch (Exception e) {
			System.out.println("文件读取失败");
			return null;
		}

		return textList;
	}

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
		String date = new SimpleDateFormat(MessageConstant.DATA_FORMAT)
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
		DataUtil.messageList.add(msg);
		writeByFileReader(path, msg);
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
			System.out.print("消息写入失败");
		}

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
