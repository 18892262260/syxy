package com.syxy.generalaviation.controller;

import com.syxy.generalaviation.entity.SeatListEntity;
import com.syxy.generalaviation.service.SeatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SeatController
 * @Description
 * @Author 南辰星
 * @Date 2019/10/10 8:43
 * @Version 1.0
 */
@RequestMapping("/seat")
@RestController
public class SeatController {
    @Resource
    private SeatService seatService;

    /**
     * 功能描述:获取IP
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 17:18
     */
    @GetMapping("/getSeatIp")
    public String getSeatIp(String seatNumber) {
        return seatService.getSeatIp(seatNumber);
    }

    /**
     * 功能描述:消息列表存储
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 17:18
     */
    @GetMapping("/getMessage")
    public List<String> getMessage(int active) {
        return seatService.getMessage(active);
    }

    /**
     * 功能描述:指定消息发送
     *
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/10/10 17:18
     */
    @PostMapping("/sendMessageToSeat")
    public void sendMessageToSeat(@RequestBody SeatListEntity seatListEntity) {
        seatService.sendMessageToSeat(seatListEntity.getMessage(), seatListEntity.getSeatNumber());
    }


}
