package com.hzz.util;

import com.hzz.beans.CommandSwitch;

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

    static {
        templetMap=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
                + File.separator + "templet.properties");
        privilegeMap=PropertiesUtils.getPropertiesValues(CommonUtils.diskPath.getPropertiesPath()
                + File.separator + "privilege.properties");
        CommonUtils.getBlackListFromPrivilege();
    }

    public static String getTempletValue(String text) {
        for (String key : templetMap.keySet()) {
            if (StringUtils.SimilarDegree(key, text) >= 0.65) {
                return templetMap.get(key);
            }
        }
        return null;
    }

}
