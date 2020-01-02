package com.syxy.generalaviation.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * @ClassName 读取配置文件工具包
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/9/25 17:18
 * @Version 1.0
 */
public class PropertiesUtil {

    public Properties seatPropertiesUtil(String name) {
        URL url = this.getClass().getClassLoader().getResource(name);
        InputStreamReader inputStreamReader = null;
        Properties prop = new Properties();
        try {
            assert url != null;
            InputStream inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            //加载配置文件;
            prop.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
