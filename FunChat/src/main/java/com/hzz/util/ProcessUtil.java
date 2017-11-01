package com.hzz.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class ProcessUtil {
	/**
	 * 执行
	 * @return
	 */

	public static String script(String command){
	    Process p=exec(command);
	    if(p==null)
	        return "命令执行失败，请检查命令是否正确";
		 if(p.isAlive())
		     return "命令执行成功";
		 return "命令执行失败,请检查命令是否正确";
	}
	public static String control(BaseMsg msg){
		String text=msg.getText();
		if(text.startsWith("exec")){
			String command= text.substring("exec".length());
			return  script(command);
		}
		return null;
	}
	public static Process exec(String command){
		return exec(command,null,null);
	}
	public static Process exec(String command,String[] envp,String dir){
		try {
			File f=null;
			if(dir!=null)
				f=new File(dir);
			Process process=Runtime.getRuntime().exec(command,envp,f);
			return process;
		} catch (Exception e) {
			return null;
		}
	}
	public static void destroy(Process p){
		if(p!=null)
			p.destroy();
	}

}
