package com.syxy.generalaviation.constant;

import javax.websocket.Session;
import java.util.*;

import static com.syxy.generalaviation.util.websocket.CustomWebSocketUtil.readProperties;

/**
 * @ClassName SeatConstant
 * @Description 席位分配常量类
 * @Author 南辰星
 * @Date 2019/9/26 16:54
 * @Version 1.0
 */
public class SeatConstant {
    public static final int ONE = 1;//向总控发送列表
    public static final int TWO = 2;//客户端登录信息交互
    public static final int THERE = 3;//分配席位信息交互
    public static final int FOUR = 4;//向总控发送目前小组信息
    public static final int FIVE = 5;//删除小组信息交互
    public static final int SIX = 6;//发送短信
    public static final int SEVEN = 7;//计时器
    public static final int EIGHT = 8;//审批状态
    public static final int NINE = 9;//审批结果
    public static final int TEN = 10;//语音讲解
    public static final int ELEVEN = 11;//评分
    public static final int TWELVE = 12;//飞行计划数据

    /**
     * 静态数据
     */
    //存储session的容器
    public static Map<String, Session> sessionControl = new HashMap<>();
    //所有脚本时间的集合
    public static Map<String, Long> secondTimeMap = new HashMap<>();
    //事件、秒
    public static long secondTime = 0;
    //定时器集合
    public static Map<String,Timer> timerMap = new HashMap<>();
    //席位
    public static List<String> listSeatNumber = Arrays.asList(readProperties("seat.properties","seatNumber"));
    //飞行作业席名称
    public static List<String> listFlyWorkName = Arrays.asList(readProperties("seat.properties","FlyWork"));
    //飞行服务席名称
    public static List<String> listFlyServiceName = Arrays.asList(readProperties("seat.properties","FlyService"));
    //航务管理席位名称
    public static List<String> listFlyManageName = Arrays.asList(readProperties("seat.properties","FlyManage"));
    //已登录席位
    public static List<String> listSeatNumberOnline = new ArrayList<>();
    //脚本时间增加倍数
    public static int multiple = 0;
    //FlyWarning消息
    public static List<String> FlyWarning = new ArrayList<>();
    //FlyArea消息
    public static List<String> FlyArea = new ArrayList<>();
    //FlyControl消息
    public static List<String> FlyControl = new ArrayList<>();
    //讲解服务
    public static List<String> FlyExplain = new ArrayList<>();
    //飞行计划申请数据
    public static String TaskMessage = "";
}
