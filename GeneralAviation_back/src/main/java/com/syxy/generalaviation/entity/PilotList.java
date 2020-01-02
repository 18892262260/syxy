package com.syxy.generalaviation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PilotList
 * @Description
 * @Author 南辰星
 * @Date 2019/12/10 9:04
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PilotList {
    private String pilotId;//飞行员ID
    private String pilotName;//飞行员姓名
    private String seatNumber;//飞行员坐席编号
}
