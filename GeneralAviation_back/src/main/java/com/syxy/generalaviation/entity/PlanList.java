package com.syxy.generalaviation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName PlanList
 * @Description 飞行计划
 * @Author 南辰星
 * @Date 2019/12/6 10:54
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanList {
    private String planId;//计划ID
    private String jobId;//任务ID
    private String startTime;//起飞时间
    private String flyTime;//飞行时间
    private String gasolineType;//油量时间
    private String alternateAirport;//备降机场
    private String planState;//计划状态
    private String planJobAirspace;//计划作业空域
    private String flyHeight;//飞行高度
    private String flySpeed;//飞行速度
    private String creatTime;//创建时间
    private String explainType;//讲解类型
    private String flyType;//飞行种类

    private String jobType;//任务类型
    private String flightCrew;//机组成员
    private String aircraftNumber;//航空器注册号
    private String jobPlaneType;//作业机型
    private String airborneEquipment;//机载设备
    private String adep;//起飞机场
    private String landingStation;//降落机场
    private String planeColour;//飞机颜色
}
