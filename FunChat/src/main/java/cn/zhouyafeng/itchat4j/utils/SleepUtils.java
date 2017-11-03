package cn.zhouyafeng.itchat4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoxiaomo on 2017/5/6.
 */
public class SleepUtils {
    private  static Logger logger= LoggerFactory.getLogger(SleepUtils.class);

    /**
     * 毫秒为单位
     * @param time
     */
    public static void sleep( long time ){
        try {
            Thread.sleep( time );
        } catch (InterruptedException e) {
            logger.error("线程睡眠出错",e.getMessage());
        }
    }

}
