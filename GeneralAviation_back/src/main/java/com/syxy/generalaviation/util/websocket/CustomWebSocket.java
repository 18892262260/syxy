package com.syxy.generalaviation.util.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.syxy.generalaviation.constant.SeatConstant;
import com.syxy.generalaviation.entity.SeatListEntity;
import com.syxy.generalaviation.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.syxy.generalaviation.constant.SeatConstant.*;
import static com.syxy.generalaviation.util.websocket.CustomWebSocketUtil.getKey;
import static com.syxy.generalaviation.util.websocket.CustomWebSocketUtil.toList;

@ServerEndpoint(value = "/generalaviation/websocket")
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomWebSocket {
    private static SeatService seatService;

    //接收到的短信
    @Autowired
    public void setChatService(SeatService seatService) {
        CustomWebSocket.seatService = seatService;
    }

    //计时器
    public static Timer timer = new Timer();
    //计时器事件
    public static TimerTask timerTask = null;
    /**
     * 静态变量，用来记录当前在线连接数。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的CumWebSocket对象。
     */
    private static CopyOnWriteArraySet<CustomWebSocket> webSocketSet = new CopyOnWriteArraySet<CustomWebSocket>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        InetSocketAddress remoteAddress = WebsocketUtil.getRemoteAddress(session);
        //加入set中
        webSocketSet.add(this);
        //添加在线人数
        addOnlineCount();
        System.out.println(this.session);
        System.out.println("新连接接入。" + remoteAddress + "当前在线人数为：" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        String message_seat = null;
        String outNum = getKey(sessionControl, this.session);
        //普通用户退出
        if (!SeatConstant.listSeatNumber.get(0).equals(outNum)) {
            //获得退出用户的编号
            SeatListEntity seat_list_entity = new SeatListEntity();
            seat_list_entity.setSeatNumber(outNum);
            //从在线用户容器当中删除该用户,防止数据重复
            SeatConstant.listSeatNumberOnline.remove(outNum);
            seatService.cleanSeatList(seat_list_entity);
            SeatListEntity seatListEntity = new SeatListEntity();
            List<SeatListEntity> send = seatService.findAllSeat(seatListEntity);
            if (send.size() != 0) {
                //从查出来的列表中移除总控的席位信息
                send.remove(0);
            }
            //转成json格式
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(send));
            //拼接成前端需要的数据格式
            message_seat = "{" + ("\"type\":" + SeatConstant.ONE) + "," + "\"content\":" + array + "}";

            //清除退出用户的session
            sessionControl.remove(outNum);
            //从set中删除
            webSocketSet.remove(this);
            //在线数减1
            subOnlineCount();
            if (sessionControl.containsKey(listSeatNumber.get(0))) {
                //给总控发送用户在线情况
                sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message_seat);
            }
            System.out.println(this.session);
        } else {
            timer.cancel();
            //总控退出
            SeatListEntity seat_list_entity = new SeatListEntity();
            seat_list_entity.setSeatState("2");
            seatService.updateSeat(seat_list_entity);
            sessionControl.remove(listSeatNumber.get(0));
            System.out.println(this.session);
            //从set中删除
            webSocketSet.remove(this);
            //在线数减1
            subOnlineCount();
            //onlineCount = 0;
            //SeatConstant.sessionControl.clear();
        }

        System.out.println("有连接关闭。当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        int type = Integer.parseInt(toList(message, "type").get(0));
        switch (type) {
            //总控登录
            case SeatConstant.ONE:
                typeOne(message);
                break;
            //客户端登录
            case SeatConstant.TWO:
                typeTwo(message);
                break;
            //席位分配
            case SeatConstant.THERE:
                typeThere(message);
                break;
            //向总控发送小组信息
            case SeatConstant.FOUR:
                typeFour();
                break;
            //删除小组
            case SeatConstant.FIVE:
                typeFive(message);
                break;
            //发送短信
            case SeatConstant.SIX:
                typeSix(message);
                break;
            //脚本计时器
            case SeatConstant.SEVEN:
                typeSeven(message);
                break;
            //审批状态
            case SeatConstant.EIGHT:
                typeEight(message);
                break;
            //审批结果
            case SeatConstant.NINE:
                typeNine(message);
                break;
            //语音讲解
            case SeatConstant.TEN:
                typeTen(message);
                break;
        }
    }

    /**
     * 暴露给外部的群发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

    /**
     * 群发
     *
     * @param message
     */
    private static void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            CustomWebSocket customWebSocket = (CustomWebSocket) item;
            //群发
            try {
                customWebSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("----websocket-------异常");
        System.out.println(this.session);
        error.printStackTrace();
    }

    /**
     * 减少在线人数
     */
    private void subOnlineCount() {
        CustomWebSocket.onlineCount--;
    }

    /**
     * 添加在线人数
     */
    private void addOnlineCount() {
        CustomWebSocket.onlineCount++;
    }

    /**
     * 当前在线人数
     *
     * @return
     */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 发送信息
     *
     * @param message
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        //获取session远程基本连接发送文本消息
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 功能描述:总控登录
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/11 15:24
     */
    private void typeOne(String message) throws IOException {
        String message_seat = null;

        //将登录人num加入在线列表
        SeatConstant.listSeatNumberOnline.add(SeatConstant.listSeatNumber.get(0));
        SeatListEntity seat_list_entity = new SeatListEntity();
        if (message.contains("clear")) {
            int clear = Integer.parseInt(toList(message, "clear").get(0));
            if (clear == 1) {
                //清除在线状态和IP，防止垃圾数据
                seatService.cleanSeatList(seat_list_entity);
                timer.cancel();
                sessionControl.clear();
                webSocketSet.clear();
                webSocketSet.add(this);
            }
        }
        if (!sessionControl.containsKey(SeatConstant.listSeatNumber.get(0))) {
            //存储总控session
            sessionControl.put(SeatConstant.listSeatNumber.get(0), this.session);
        }

        //获取登录人IP
        String remoteAddress = WebsocketUtil.getRemoteAddress(this.session).toString();
        //列表已有数据
        List<SeatListEntity> listFind = seatService.findAllSeat(seat_list_entity);
        //已有number
        List<String> numStr = new ArrayList<>();
        for (SeatListEntity seatListEntity : listFind) {
            if (!SeatConstant.listSeatNumber.contains(seatListEntity.getSeatNumber())) {
                seatService.deleteSeatListByNumber(seatListEntity.getSeatNumber());
            }
            numStr.add(seatListEntity.getSeatNumber());
        }
        //将分组数据插入数据库
        for (int i = 0; i < SeatConstant.listSeatNumber.size(); i++) {
            seat_list_entity.setSeatNumber(SeatConstant.listSeatNumber.get(i));
            if (!numStr.contains(SeatConstant.listSeatNumber.get(i))) {
                seat_list_entity.setSeatState("2");
                seatService.addSeatList(seat_list_entity);
            }
        }
        seat_list_entity.setSeatState("1");
        seat_list_entity.setSeatIp(remoteAddress);
        seat_list_entity.setSeatNumber(SeatConstant.listSeatNumber.get(0));
        //更新在线状态
        seatService.updateSeat(seat_list_entity);
        SeatListEntity sendSeatList = new SeatListEntity();
        List<SeatListEntity> list = new ArrayList<>();
        list = seatService.findAllSeat(sendSeatList);
        if (list.size() != 0) {
            list.remove(0);
        }
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
        message_seat = "{" + ("\"type\":" + SeatConstant.ONE) + "," + "\"content\":" + array + "}";
        sendMessage(message_seat);
    }

    /**
     * 功能描述:普通用户登录
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/11 15:26
     */
    private void typeTwo(String message) throws IOException {
        String message_seat = null;
        String remoteAddress = WebsocketUtil.getRemoteAddress(this.session).toString();
        List<String> seatNumber = toList(message, "seatNumber");
        String str = seatNumber.get(0);
        //将登录用户编号存到内存
        SeatConstant.listSeatNumberOnline.add(str);
        SeatListEntity seat_list_entity = new SeatListEntity();
        seat_list_entity.setSeatNumber(str);
        seat_list_entity.setSeatState("1");
        seat_list_entity.setSeatIp(remoteAddress);
        //更新席位状态
        seatService.updateSeat(seat_list_entity);
        //存储session
        if (!sessionControl.containsKey(str)) {
            sessionControl.put(str, this.session);
        } else {
            sessionControl.remove(str);
            sessionControl.put(str, this.session);
        }

        //以下为向总控发送在线信息
        SeatListEntity sendSeat = new SeatListEntity();
        List<SeatListEntity> listSend = seatService.findAllSeat(sendSeat);
        listSend.remove(0);
        JSONArray arraySend = JSONArray.parseArray(JSON.toJSONString(listSend));
        message_seat = "{" + ("\"type\":" + SeatConstant.TWO) + "," + "\"content\":" + arraySend + "}";
        if (sessionControl.get(SeatConstant.listSeatNumber.get(0)) != null) {
            //更新目前在线客户端状态
            sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message_seat);
        }
        sendSeat.setSeatNumber(str);
        List<SeatListEntity> listSendToNumber = seatService.findAllSeat(sendSeat);
        message_seat = "{" + ("\"type\":" + SeatConstant.THERE) + "," + "\"content\":" + "\"" + listSendToNumber.get(0).getSeatName() + "\"" + "}";
        if (StringUtils.isNotBlank(sessionControl.get(str).toString())) {
            //客户端登录之后，向客户端发送其目前席位信息
            sessionControl.get(str).getBasicRemote().sendText(message_seat);
        }
    }

    /**
     * 功能描述:席位分配
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/11 15:26
     */
    private void typeThere(String message) throws IOException {
        String message_seat = null;
        //从消息中提取各席位信息对应的编号
        List<String> FlyWork1 = toList(message, SeatConstant.listFlyWorkName.get(0));
        List<String> FlyService = toList(message, SeatConstant.listFlyServiceName.get(0));
        List<String> FlyManage = toList(message, SeatConstant.listFlyManageName.get(0));
        SeatListEntity seat_list_entity = new SeatListEntity();
        String groupId = UUID.randomUUID().toString();
        seat_list_entity.setSeatGroupId(groupId);
        //向客户端发送其席位名称
        for (Object o : FlyWork1) {
            String number = o.toString();
            seat_list_entity.setSeatNumber(number);
            seat_list_entity.setSeatName(SeatConstant.listFlyWorkName.get(0));
            seatService.updateSeat(seat_list_entity);
            if (sessionControl.get(number) != null) {
                message_seat = "{" + ("\"type\":" + SeatConstant.THERE) + "," + "\"content\":" + "\"FlyWork1\"" + "}";
                sessionControl.get(number).getBasicRemote().sendText(message_seat);
            }
        }
        for (Object o : FlyService) {
            String number = o.toString();
            seat_list_entity.setSeatNumber(number);
            seat_list_entity.setSeatName(SeatConstant.listFlyServiceName.get(0));
            seatService.updateSeat(seat_list_entity);
            if (sessionControl.get(number) != null) {
                message_seat = "{" + ("\"type\":" + SeatConstant.THERE) + "," + "\"content\":" + "\"FlyService\"" + "}";
                sessionControl.get(number).getBasicRemote().sendText(message_seat);
            }
        }
        for (Object o : FlyManage) {
            String number = o.toString();
            seat_list_entity.setSeatNumber(number);
            seat_list_entity.setSeatName(SeatConstant.listFlyManageName.get(0));
            seatService.updateSeat(seat_list_entity);
            if (sessionControl.get(number) != null) {
                message_seat = "{" + ("\"type\":" + SeatConstant.THERE) + "," + "\"content\":" + "\"FlyManage\"" + "}";
                sessionControl.get(number).getBasicRemote().sendText(message_seat);
            }
        }
        seat_list_entity.setSeatGroupMessage(message);
        //更新分组
        seatService.addSeatGroup(seat_list_entity);
        List<SeatListEntity> send = seatService.findAllGroup();
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(send));
        message_seat = "{" + ("\"type\":" + SeatConstant.THERE) + "," + "\"content\":" + array + "}";
        if(sessionControl.containsKey(listSeatNumber.get(0))){
            sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message_seat);
        }
    }

    /**
     * 功能描述:向总控发送小组信息
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 14:47
     */
    private void typeFour() throws IOException {
        String message_seat = null;
        List<SeatListEntity> send = seatService.findAllGroup();
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(send));
        message_seat = "{" + ("\"type\":" + SeatConstant.FOUR) + "," + "\"content\":" + array + "}";
        sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message_seat);
    }

    /**
     * 功能描述:删除小组
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 13:57
     */
    private void typeFive(String message) {
        String seatGroupId = toList(message, "seatGroupId").get(0);
        //删除小组表小组
        seatService.cleanSeatGroup(seatGroupId);
        //删除席位
        seatService.deleteGroup(seatGroupId);

    }

    /**
     * 功能描述:发送短信，包括群发和单独发送
     *
     * @param: message
     * @return:
     * @auther: 南辰星
     * @date:
     */
    private synchronized void typeSix(String message) throws IOException {
        String count = toList(message, "count").get(0);
        int active = Integer.parseInt(toList(message, "active").get(0));
        switch (active) {
            case 1:
                SeatConstant.FlyWarning.add(message);
                break;
            case 2:
                SeatConstant.FlyArea.add(message);
                break;
            case 3:
                SeatConstant.FlyControl.add(message);
                break;
            case 4:
                SeatConstant.FlyExplain.add(message);
                break;
        }
        if (count.equals("1")) {
            List<String> messageNumber = toList(message, "messageNumber");
            String num = messageNumber.get(0);
            sessionControl.get(num).getBasicRemote().sendText(message);
        } else {
            for (String num : SeatConstant.listSeatNumberOnline) {
                sessionControl.get(num).getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 功能描述:脚本定时器
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 14:48
     */
    public void typeSeven(String message) throws IOException {
        String message_seat = null;
        if (sessionControl.size() < 2) {
            message_seat = "{" + ("\"type\":" + SeatConstant.SEVEN) + "," + "\"content\":" + "\"" + "未连接" + "\"" + "}";
            sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message_seat);
        } else {
            String groupId = toList(message, "groupId").get(0);
            //multiple为倍数
            SeatConstant.multiple = Integer.parseInt(toList(message, "multiple").get(0));
            //count为记录暂停时时间的标志
            int count = Integer.parseInt(toList(message, "count").get(0));
            if (count == 0) {
                //以下为取得时间并转换为秒
                List<String> toStr = toList(message, "date");
                String dateStr = toStr.get(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                assert date != null;
                long dateTime = date.getTime();
                SeatConstant.secondTime = dateTime / 1000;
                secondTimeMap.put(groupId, secondTime);
            }

            SeatListEntity seatListEntity = new SeatListEntity();
            seatListEntity.setSeatGroupId(groupId);
            List<SeatListEntity> seatList = seatService.findAllSeat(seatListEntity);
            if (!timerMap.containsKey(groupId)) {
                timerMap.put(groupId, new Timer());
            }
            CustomWebSocketUtil.timerSocket(groupId, seatList);
        }
    }

    private void typeEight(String message) throws IOException {
        sessionControl.get(SeatConstant.listSeatNumber.get(0)).getBasicRemote().sendText(message);
    }

    private synchronized void typeNine(String message) throws IOException {
        sendAll(message);
    }

    /**
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/17 9:13
     */
    private synchronized void typeTen(String message) throws IOException {
        sendAll(message);
    }


}
