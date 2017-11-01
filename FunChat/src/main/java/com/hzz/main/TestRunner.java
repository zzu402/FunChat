package com.hzz.main;

import com.hzz.handler.MessageHandler;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

import com.hzz.util.CommonUtils;
/**
 * @Author: huangzz
 * @Description:
 * @Date :2017/10/27
 */
public class TestRunner  {

    public static void main(String []args){
    	CommonUtils.createOriginMkdir();
        String qrPath = CommonUtils.diskPath.getQrPath(); // 保存登陆二维码图片的路径
        IMsgHandlerFace msgHandler = new MessageHandler(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
        CommonUtils.createUserMkdir(Core.getInstance().getNickName());
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
    }
    
   

	

}
