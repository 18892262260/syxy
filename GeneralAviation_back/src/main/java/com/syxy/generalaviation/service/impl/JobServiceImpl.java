package com.syxy.generalaviation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxy.generalaviation.constant.OtherConstant;
import com.syxy.generalaviation.entity.JobList;
import com.syxy.generalaviation.mapper.JobMapper;
import com.syxy.generalaviation.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName JobServiceImpl
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:30
 * @Version 1.0
 */
@Service
public class JobServiceImpl implements JobService {
    @Resource
    private JobMapper jobMapper;

    @Override
    public int addJob(JobList jobList) {
        String jogId = UUID.randomUUID().toString();
        jobList.setJobId(jogId);
        if(StringUtils.isBlank(jobList.getFlightCrew())){
            jobList.setJobState(OtherConstant.ONE);
        }
        if(StringUtils.isNotBlank(jobList.getFlightCrew())){
            jobList.setJobState(OtherConstant.TWO);
        }
        return jobMapper.addJob(jobList);
    }

    @Override
    public int deleteJobById(String jobId) {
        return jobMapper.deleteJobById(jobId);
    }

    @Override
    public int updateJobById(JobList jobList) {
        jobList.setSeatNumber(jobMapper.findJobByPilotName(jobList.getFlightCrew()));
        if(StringUtils.isBlank(jobList.getFlightCrew())){
            jobList.setJobState(OtherConstant.ONE);
        }
        if(StringUtils.isNotBlank(jobList.getFlightCrew())){
            jobList.setJobState(OtherConstant.TWO);
        }
        return jobMapper.updateJobById(jobList);
    }

    @Override
    public Map<String,Object> findAllJob(JobList jobList, int pageNum, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<JobList> list = jobMapper.findAllJob(jobList);
        PageInfo<JobList> pageInfo = new PageInfo<>(list);
        map.put("content",pageInfo.getList());
        map.put("count",pageInfo.getTotal());
        return map;
    }

    @Override
    public JobList findByJobId(JobList jobList) {
        return jobMapper.findAllJob(jobList).get(0);
    }

}
