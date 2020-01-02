package com.syxy.generalaviation.mapper;

import com.syxy.generalaviation.entity.PlanList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName PlanMapper
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:30
 * @Version 1.0
 */
@Mapper
public interface PlanMapper {
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
    List<PlanList> findAllPlan(PlanList planList);
}
