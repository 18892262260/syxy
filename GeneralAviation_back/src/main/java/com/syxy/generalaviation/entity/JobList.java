package com.syxy.generalaviation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName JobList
 * @Description 任务表
 * @Author 南辰星
 * @Date 2019/12/6 10:46
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobList {
    private String jobId;//任务ID
    private String jobType;//任务类型
    private String jobName;//任务名称
    private String startTime;//任务开始时间
    private String endTime;//任务结束时间
    private String flightCrew;//机组成员
    private String aircraftNumber;//航空器注册号
    private String jobPlaneType;//作业机型
    private String airborneEquipment;//机载设备
    private String adep;//起飞机场
    private String landingStation;//降落机场
    private String planeColour;//飞机颜色
    private String jobAirspace;//作业空域
    private String jobState;//作业状态
    private String groupId;//小组名称
    private String seatNumber;//席位编号
}
