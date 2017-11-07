package com.hzz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    public static String getPropertiesValue(String path,String key,String defaultValue){
        Properties ppts = new Properties();
        try {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            ppts.load(new InputStreamReader(new FileInputStream(path),"utf-8"));
            return  ppts.getProperty(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Map<String,String> getPropertiesValues(String path ){
        Map<String,String>values=new HashMap<>();
        Properties properties = new Properties();
        try {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            properties.load(new InputStreamReader(new FileInputStream(path),"utf-8"));
            ArrayList<Object> list = new ArrayList<Object>(properties.keySet());
            for (int i = 0; i < list.size(); i++) {
                values.put((String) list.get(i),
                        (String) properties.get(list.get(i)));
            }
        } catch (Exception e) {
            return values;
        }
        return  values;
    }

    public static String getPropertiesValue(Class clazz,String path,String key,String defalutValue){
        Properties properties=new Properties();
        try {
            properties.load(new InputStreamReader(clazz.getResourceAsStream(path),"utf-8"));
            return properties.getProperty(key);
        } catch (Exception e) {
            return  defalutValue;
        }
    }


    public static void writeProperties(String path,String keyname,String keyvalue) {
        Properties properties = new Properties();
        try {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            properties.load(new InputStreamReader(new FileInputStream(path),"utf-8"));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(path);
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (Exception e) {
            logger.error("属性更新失败:"+e.getMessage());
        }
    }


}
