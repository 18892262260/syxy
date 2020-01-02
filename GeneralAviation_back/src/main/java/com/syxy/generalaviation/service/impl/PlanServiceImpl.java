package com.syxy.generalaviation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxy.generalaviation.constant.OtherConstant;
import com.syxy.generalaviation.entity.JobList;
import com.syxy.generalaviation.entity.PlanList;
import com.syxy.generalaviation.mapper.JobMapper;
import com.syxy.generalaviation.mapper.PlanMapper;
import com.syxy.generalaviation.service.PlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName PlanServiceImpl
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:32
 * @Version 1.0
 */
@Service
public class PlanServiceImpl implements PlanService {
    @Resource
    private PlanMapper planMapper;
    @Resource
    private JobMapper jobMapper;

    @Override
    public int addPlan(PlanList planList) {
        String planId = UUID.randomUUID().toString();
        planList.setPlanId(planId);
        planList.setPlanState(OtherConstant.ADD_PLAN);
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        planList.setCreatTime(sdf.format(date));
        JobList jobList = new JobList();
        jobList.setJobId(planList.getJobId());
        jobList.setJobState(OtherConstant.THERE);
        jobMapper.updateJobById(jobList);
        return planMapper.addPlan(planList);
    }

    @Override
    public int deletePlanById(String planId) {
        return planMapper.deletePlanById(planId);
    }

    @Override
    public int updatePlanById(PlanList planList) {
        return planMapper.updatePlanById(planList);
    }

    @Override
    public Map<String, Object> findAllPlan(PlanList planList, int pageNum, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<PlanList> list = planMapper.findAllPlan(planList);
        PageInfo<PlanList> pageInfo = new PageInfo<>(list);
        map.put("content",pageInfo.getList());
        map.put("count",pageInfo.getTotal());
        return map;
    }

    @Override
    public PlanList findByPlanId(PlanList planList) {
        List<PlanList> list = planMapper.findAllPlan(planList);
        if(list.size() >0){
            return list.get(0);
        }else {
            return null;
        }

    }

    @Override
    public PlanList findPlanByJobId(PlanList planList) {
        List<PlanList> list = planMapper.findAllPlan(planList);
        if(list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int recallPlan(String planId,String jobId) {
        PlanList planList = new PlanList();
        JobList jobList = new JobList();
        planList.setPlanId(planId);
        planList.setPlanState(OtherConstant.HAVE_PAI);
        jobList.setJobId(jobId);
        jobList.setJobState(OtherConstant.TWO);
        planMapper.updatePlanById(planList);
        jobMapper.updateJobById(jobList);
        return 0;
    }
}
