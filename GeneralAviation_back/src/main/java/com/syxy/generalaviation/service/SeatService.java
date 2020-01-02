package com.syxy.generalaviation.service;

import com.syxy.generalaviation.entity.SeatListEntity;

import java.util.List;

public interface SeatService {
    /**
     * 查找所有
     * @return
     */
    List<SeatListEntity> findAllSeat(SeatListEntity seat_list_entity);
    /**
     * 功能描述:修改状态以及IP
     * @param: SeatListEntity
     * @return: int
     * @auther: 南辰星
     * @date: 2019/9/23 14:47
     */
    int updateSeat(SeatListEntity seat_list_entity);

    /**
     * 功能描述:增加小组
     * @param: SeatListEntity
     * @return: int
     * @auther: 南辰星
     * @date: 2019/9/23 14:47
     */
    int addSeatGroup(SeatListEntity seat_list_entity);

    /**
     * 功能描述:查找小组
     * @param: SeatListEntity
     * @return: int
     * @auther: 南辰星
     * @date: 2019/9/23 14:47
     */
    List<SeatListEntity> findAllGroup();
    /**
     * 功能描述:
     * @param: 总控退出后清除 主表
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 13:42
     */
    int cleanSeatList(SeatListEntity seat_list_entity);

    /**
     * 功能描述:总控退出后，清除小组表
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 13:42
     */
    int cleanSeatGroup(String seatGroupId);

    /**
     * 功能描述:删除小组清除主表对应数据
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 13:53
     */
    int deleteGroup(String seatGroupId);

    /**
     * 功能描述:插入设备信息
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 17:24
     */
    int addSeatList(SeatListEntity seat_list_entity);

    /**
     * 功能描述:修改编号
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/26 10:44
     */
    int deleteSeatListByNumber(String num);

    /**
     * 功能描述:获取IP
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 09:05
     */
    String getSeatIp(String seatNumber);

    /**
     * 功能描述:获取消息列表
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 16:50
     */
    List<String> getMessage(int active);

    /**
     * 功能描述:接收空域消息
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/11 9:15
     */
    void getTaskMessage(String message);
    /**
     * 功能描述:发送空域消息
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/11 9:53
     */
    String sendTaskMessage();
    /**
     * 功能描述:向坐席发送信息接口
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/11/7 8:55
     */
    void sendMessageToSeat(String message,String seatNumber);
}
