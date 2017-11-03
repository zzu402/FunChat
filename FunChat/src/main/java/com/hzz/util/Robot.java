package com.hzz.util;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class Robot {
	private static Logger logger = LoggerFactory.getLogger(Robot.class);
	private static String apiKey = "629ede157ed34a428ab7d3ee27b8ae9c";
	static {
		apiKey=PropertiesUtils.getPropertiesValue(CommonUtils.diskPath.getPropertiesPath(),"apiKey",apiKey);
	}
	public static String startRobot(MyHttpClient httpClient,String text){
		String result ="";
		String url = "http://www.tuling123.com/openapi/api";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("key", apiKey);
		paramMap.put("info", text);
		paramMap.put("userid", "123456");
		String paramStr = JSON.toJSONString(paramMap);
		try {
			HttpEntity entity = httpClient.doPost(url, paramStr);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			if (obj.getString("code").equals("100000")) {
				result = obj.getString("text");
			} else {
				result = "你好，今天天气真好！";
			}
		} catch (Exception e) {
			logger.info("机器人通讯失败："+e.getMessage());
		}
		return result;
	}
}
