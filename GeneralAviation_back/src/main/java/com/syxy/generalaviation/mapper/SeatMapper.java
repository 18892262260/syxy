package com.syxy.generalaviation.mapper;

import com.syxy.generalaviation.entity.SeatListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatMapper{
    /**
     * 功能描述:插入设备信息
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 17:24
     */
    int addSeatList(SeatListEntity seat_list_entity);

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
     * @param: 总控退出后修改状态
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/25 13:42
     */
    int cleanSeatList(SeatListEntity seat_list_entity);

    /**
     * 功能描述:清除小组表
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
     * 功能描述:修改编号
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/9/26 10:44
     */
    int deleteSeatListByNumber(String num);

}
