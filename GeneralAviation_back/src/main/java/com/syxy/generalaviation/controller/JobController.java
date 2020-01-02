package com.syxy.generalaviation.controller;

import com.syxy.generalaviation.entity.JobList;
import com.syxy.generalaviation.service.JobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName JobController
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:02
 * @Version 1.0
 */
@RequestMapping("/job")
@RestController
public class JobController {
    @Resource
    private JobService jobService;

    @GetMapping("/findAllJob")
    public Map<String,Object> findAllJob(JobList jobList,int pageNum,int pageSize){
        return jobService.findAllJob(jobList,pageNum,pageSize);
    }
    @GetMapping("/findByJobId")
    public JobList findByJobId(JobList jobList){
        return jobService.findByJobId(jobList);
    }

    @GetMapping("/deleteJobById")
    public int deleteJobById(String jobId){
        return jobService.deleteJobById(jobId);
    }

    @PostMapping("/updateJobById")
    public int deleteJobById(@RequestBody JobList jobList){
        return jobService.updateJobById(jobList);
    }

    @PostMapping("/addJob")
    public int addJob(@RequestBody JobList jobList){
        return jobService.addJob(jobList);
    }


}
