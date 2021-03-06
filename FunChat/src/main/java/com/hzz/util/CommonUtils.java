package com.hzz.util;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzz.beans.DiskPath;
import com.hzz.beans.MailBean;
import com.hzz.beans.Operation;
import com.hzz.enums.PrivilegeEnum;

import java.io.File;
import java.util.*;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/28
 */
public class CommonUtils {

    public static DiskPath diskPath =new DiskPath() ;
    public static Map<String,Operation> operationList=new HashMap<>();

    public static String select(String path1,String path2,String self) {
        if (path1.equals(self)) return path2;
        else return path1;
    }
    public static String getNickByMsgUserNameInGroup(String groupId,String msgUserNameInGroup){
         JSONArray jsonArray=WechatTools.getMemberListByGroupId(groupId);
         if(jsonArray==null)
             return null;
         for(int i=0;i<jsonArray.size();i++){
             JSONObject jsonObject=jsonArray.getJSONObject(i);
             if(jsonObject.getString("UserName").equals(msgUserNameInGroup)){
                 return jsonObject.getString("NickName");
             }
         }
         return msgUserNameInGroup;
    }

    public static String getRemarkNameByUserName(String userName){
        boolean isFind=false;
        String self=Core.getInstance().getUserName();
        if(self==null)
            return "";
        if(userName.equals(self))
            return Core.getInstance().getUserSelf().getString("RemarkName");

        for (JSONObject o : WechatTools.getContactList()) {
            if (o.getString("UserName").equals(userName)) {
                isFind=true;
                return o.getString("RemarkName");
            }
        }
        if(!isFind){ //如果联系人列表没有，就从群名找
            for (JSONObject o : WechatTools.getGroupList()) {
                if (o.getString("UserName").equals(userName)) {
                    isFind=true;
                    return o.getString("RemarkName");
                }
            }
        }
        return "";
    }

    public static String getNickByUserName(String userName){
        //这里增加一个信息，如果用户有备注，则返回备注名称，否则返回昵称
        String remark=getRemarkNameByUserName(userName);
        if(remark!=null&&!remark.equals(""))
            return remark;
        boolean isFind=false;
        String self=Core.getInstance().getUserName();
        if(self==null)
            return userName;
        if(userName.equals(self))
        	return Core.getInstance().getUserSelf().getString("NickName");
        
        for (JSONObject o : WechatTools.getContactList()) {
            if (o.getString("UserName").equals(userName)) {
                isFind=true;
                return o.getString("NickName");
            }
        }
        if(!isFind){ //如果联系人列表没有，就从群名找
            for (JSONObject o : WechatTools.getGroupList()) {
                if (o.getString("UserName").equals(userName)) {
                    isFind=true;
                    return o.getString("NickName");
                }
            }
        }

        return userName;
    }

    /*
     * 创建原始的文件夹
     */
    public static void createOriginMkdir(){
        initDiskPath();
        File file=new File(diskPath.getPicPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getVideoPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getVoicePath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getMediaPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getPropertiesPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getQrPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getMessagePath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getFilePath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();
        file=new File(diskPath.getScriptPath());
        if(!file .exists()  && !file .isDirectory())
            file.mkdirs();

    }
    /**
     * 从配置文件中初始化路径
     */
    private static  void initDiskPath(){
        //String programUrl= System.getProperty("user.dir");
        String rootPath=String.format("%s//%s","..","FunChatData");
        diskPath.setPicPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","picPath",rootPath+"//pic"));
        diskPath.setVideoPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","videoPath",rootPath+"//video"));
        diskPath.setVoicePath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","voicePath",rootPath+"//voice"));
        diskPath.setMediaPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","mediaPath",rootPath+"//media"));
        diskPath.setQrPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","qrPath",rootPath+"//qr"));
        diskPath.setMessagePath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","messagePath",rootPath+"//message"));
        diskPath.setFilePath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","filePath",rootPath+"//file"));
        diskPath.setPropertiesPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","propertiesPath",rootPath+"//properties"));
        diskPath.setScriptPath(PropertiesUtils.getPropertiesValue(CommonUtils.class,"/disk_path.properties","scriptPath",rootPath+"//script"));
    }

    public static void getBlackListFromPrivilege(){
        Iterator<String> iterator=DataUtil.privilegeMap.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            String value = DataUtil.privilegeMap.get(key);
            if(value.contains(PrivilegeEnum.BLACK.getValue())){
                DataUtil.blackList.add(key);
            }
        }
    }

    public static boolean isBlackList(String nickName){
        String privilege=DataUtil.privilegeMap.get(nickName);
        if(privilege!=null){
            if(privilege.contains(PrivilegeEnum.BLACK.getValue()));
            return true;
        }
        return false;
    }

    public static boolean  hasPrivilege(String nickName,String op){
        if(DataUtil.privilegeMap.size()<=0||op==null||nickName.equals(Core.getInstance().getNickName()))
            return true;
        String privilege=DataUtil.privilegeMap.get(nickName);
        if(privilege==null)//如果没有设置权限默认有权限
            return true;
        if(privilege!=null){
            if(privilege.contains(op))
              return true;
        }
        return false;
    }
}
