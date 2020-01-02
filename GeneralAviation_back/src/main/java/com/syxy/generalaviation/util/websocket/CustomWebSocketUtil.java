package com.syxy.generalaviation.util.websocket;

import com.alibaba.fastjson.JSONObject;
import com.syxy.generalaviation.constant.SeatConstant;
import com.syxy.generalaviation.entity.SeatListEntity;
import com.syxy.generalaviation.util.PropertiesUtil;

import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.syxy.generalaviation.constant.SeatConstant.*;
import static com.syxy.generalaviation.util.websocket.CustomWebSocket.timerTask;

/**
 * @ClassName WebSocketService
 * @Description
 * @Author 南辰星
 * @Date 2019/10/29 14:56
 * @Version 1.0
 */
public class CustomWebSocketUtil {
    /**
     * 功能描述:定时器
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date:
     */
    public static void timerSocket(String groupId, List<SeatListEntity> seatList) {
        //当倍数小于等于零的 时候为暂停或者结束，直接销毁该计时器
        if (SeatConstant.multiple <= 0) {
            secondTimeMap.remove(groupId);
            secondTimeMap.put(groupId, secondTime);
            timerMap.get(groupId).cancel();
        } else {
            //先销毁之前的计时器，创建一个新计时器
            timerMap.get(groupId).cancel();
            timerMap.remove(groupId);
            timerMap.put(groupId, new Timer());
        }

        if(secondTimeMap.containsKey(groupId)){
            secondTime = secondTimeMap.get(groupId);
        }
        //清空事件
        timerTask = null;
        //创建新事件
        timerTask = new TimerTask() {
            @Override
            public synchronized void run() {
                //秒数按照倍数减少
                SeatConstant.secondTime += SeatConstant.multiple;
                String sendTime = stampToDate(String.valueOf(SeatConstant.secondTime * 1000));
                for (SeatListEntity seatListEntity : seatList) {
                    try {
                        String sendMessage = "{" + ("\"type\":" + SeatConstant.SEVEN) + "," + "\"content\":" + "\"" + sendTime + "\"" + "}";
                        if (sessionControl.get(seatListEntity.getSeatNumber()) != null) {
                            sessionControl.get(seatListEntity.getSeatNumber()).getBasicRemote().sendText(sendMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        if (SeatConstant.multiple > 0) {
            timerMap.get(groupId).scheduleAtFixedRate(timerTask, 0, 1000);// 1秒一次
        }
    }
    /**
     * 功能描述:时间戳转时间
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/30 15:00
     */
    private static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 功能描述:前台传JSON字符串，转为集合
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date:
     */
    public static List<String> toList(String message, String key) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String listStr = jsonObject.get(key).toString().replace("[\"", "").replace("\"]", "").replace("\"", "");
        String[] str = listStr.split(",");
        return new ArrayList<String>(Arrays.asList(str));
    }

    /**
     * 功能描述:取配置文件数据方法
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date:
     */
    public static String[] readProperties(String pathName, String arrayName) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties prop = propertiesUtil.seatPropertiesUtil(pathName);
        String str = prop.getProperty(arrayName);
        return str.split(",");
    }





    //根据value值获取到对应的一个key值
    public static String getKey(Map<String, Session> map, Session value) {
        String key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for (String getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
    }
}
