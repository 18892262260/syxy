package com.syxy.generalaviation.service;

import com.syxy.generalaviation.entity.PlanList;

import java.util.Map;

/**
 * @ClassName PlanService
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:30
 * @Version 1.0
 */
public interface PlanService {
    /**
     * 功能描述:增加计划
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:38
     */
    int addPlan(PlanList planList);

    /**
     * 功能描述:根据ID删除计划
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:38
     */
    int deletePlanById(String planId);

    /**
     * 功能描述:根据ID修改计划
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:41
     */
    int updatePlanById(PlanList planList);

    /**
     * 功能描述:查询计划
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:41
     */
    Map<String, Object> findAllPlan(PlanList planList, int pageNum, int pageSize);

    /**
     * 功能描述:根据ID查询
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/9 14:09
     */
    PlanList findByPlanId(PlanList planList);

    /**
     * 功能描述:通过任务ID查找最新计划
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/10 10:37
     */
    PlanList findPlanByJobId(PlanList planList);

    /**
     * 功能描述:计划撤回
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/10 15:21
     */
    int recallPlan(String planId,String jobId);
}
