package com.hzz.util;


import com.hzz.beans.CommandSwitch;
import com.hzz.beans.MailBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/11/1
 */
public class DataUtil {
    public static Map<String, String> templetMap = null;
    public static Map<String, String> privilegeMap = null;
    public static CommandSwitch commandSwitch = new CommandSwitch();
    public static List<String> blackList=new ArrayList<>();
    public static List<String> messageList=new ArrayList<>();
    public static MailBean mailBean=new MailBean();

    static {
        templetMap=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
                + File.separator + "templet.properties");
        privilegeMap=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
                + File.separator + "privilege.properties");
        CommonUtils.getBlackListFromPrivilege();
        mailBean.setContent(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","mailContent",""));
        mailBean.setSubject(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","subject",""));
        mailBean.setFrom(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","from",""));
        mailBean.setPassword(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","password",""));
        mailBean.setUsername(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","userName",""));
        mailBean.setHost(PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath()
                + File.separator +"mail_config.properties","host",""));
    }

    public static String getTempletValue(String text) {
        boolean isAll=templetMap.containsKey("*");
        if(isAll&&!templetMap.get("*").equals("*"))
            return  templetMap.get("*");
        for (String key : templetMap.keySet()) {
            if (StringUtils.SimilarDegree(key, text) >= 0.65) {
                return templetMap.get(key);
            }
        }
        return null;
    }
}
