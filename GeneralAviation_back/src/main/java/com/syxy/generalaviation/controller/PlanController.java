package com.syxy.generalaviation.controller;

import com.syxy.generalaviation.entity.PlanList;
import com.syxy.generalaviation.service.PlanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName PlanController
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:02
 * @Version 1.0
 */
@RequestMapping("/plan")
@RestController
public class PlanController {
    @Resource
    private PlanService planService;

    @GetMapping("/findAllPlan")
    public Map<String, Object> findAllPlan(PlanList planList, int pageNum, int pageSize){
        return planService.findAllPlan(planList,pageNum,pageSize);
    }

    @GetMapping("/findByPlanId")
    public PlanList findByPlanId(PlanList planList){
        return planService.findByPlanId(planList);
    }
    @GetMapping("/findPlanByJobId")
    public PlanList findPlanByJobId(PlanList planList){
        return planService.findPlanByJobId(planList);
    }

    @GetMapping("/deletePlanById")
    public int deletePlanById(String planId){
        return planService.deletePlanById(planId);
    }

    @GetMapping("/recallPlan")
    public int recallPlan(String planId,String jobId){
        return planService.recallPlan(planId,jobId);
    }

    @PostMapping("/updatePlanById")
    public int deletePlanById(@RequestBody PlanList planList){
        return planService.updatePlanById(planList);
    }

    @PostMapping("/addPlan")
    public int addPlan(@RequestBody PlanList planList){
        return planService.addPlan(planList);
    }
}
