package com.syxy.generalaviation.service.impl;

import com.syxy.generalaviation.constant.SeatConstant;
import com.syxy.generalaviation.service.SeatService;
import com.syxy.generalaviation.entity.SeatListEntity;
import com.syxy.generalaviation.mapper.SeatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static com.syxy.generalaviation.constant.SeatConstant.TaskMessage;
import static com.syxy.generalaviation.constant.SeatConstant.sessionControl;


@Service
public class SeatServiceImpl implements SeatService {
    @Resource
    private SeatMapper seatMapper;

    @Override
    public List<SeatListEntity> findAllSeat(SeatListEntity seat_list_entity) {
        return seatMapper.findAllSeat(seat_list_entity);
    }

    @Override
    public int updateSeat(SeatListEntity seat_list_entity) {
        return seatMapper.updateSeat(seat_list_entity);
    }

    @Override
    public int addSeatGroup(SeatListEntity seat_list_entity) {
        return seatMapper.addSeatGroup(seat_list_entity);
    }

    @Override
    public List<SeatListEntity> findAllGroup() {
        return seatMapper.findAllGroup();
    }

    @Override
    public int cleanSeatList(SeatListEntity seat_list_entity) {
        return seatMapper.cleanSeatList(seat_list_entity);
    }

    @Override
    public int cleanSeatGroup(String seatGroupId) {
        return seatMapper.cleanSeatGroup(seatGroupId);
    }

    @Override
    public int deleteGroup(String seatGroupId) {
        return seatMapper.deleteGroup(seatGroupId);
    }

    @Override
    public int addSeatList(SeatListEntity seat_list_entity) {
        return seatMapper.addSeatList(seat_list_entity);
    }

    @Override
    public int deleteSeatListByNumber(String num) {
        return seatMapper.deleteSeatListByNumber(num);
    }

    @Override
    public String getSeatIp(String seatNumber) {
        SeatListEntity seat_list_entity = new SeatListEntity();
        seat_list_entity.setSeatNumber(seatNumber);
        if(seatMapper.findAllSeat(seat_list_entity).size() != 0){
            SeatListEntity seat = seatMapper.findAllSeat(seat_list_entity).get(0);
            return seat.getSeatIp();
        }else {
            return "用户未登录";
        }
    }

    @Override
    public List<String> getMessage(int active) {
        if (active == 1) {
            return SeatConstant.FlyWarning;
        } else if (active == 2) {
            return SeatConstant.FlyArea;
        } else if (active == 3) {
            return SeatConstant.FlyControl;
        } else {
            return SeatConstant.FlyExplain;
        }
    }

    @Override
    public void getTaskMessage(String message) {
        if(message != null){
            TaskMessage = "";
            TaskMessage = message;
        }

    }

    @Override
    public String sendTaskMessage() {
        return TaskMessage;
    }

    @Override
    public void sendMessageToSeat(String message,String seatNumber) {
        try {
            if(sessionControl.containsKey(seatNumber)){
                sessionControl.get(seatNumber).getBasicRemote().sendText(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
