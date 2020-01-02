package com.syxy.generalaviation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatListEntity {
    private String seatNumber;//设备编号
    private String seatIp;//用户IP
    private String seatState;//登录状态
    private String seatName;//席位名称
    private String seatGroupId;//小组ID
    private String seatGroupMessage;//小组名称
    private String message;//消息
}
