package server;

import java.io.Closeable;

/**
 * @Author: 枠成
 * @Data:2019-05-27
 * @Description:version2
 * @version:1.0
 * 释放资源工具类
 * 方案为尚学堂300集方案
 */
public class Utils {

    public static void closeAll(Closeable... targets){
        for (Closeable target:targets) {
            try {
                if (null!=target){
                    target.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
