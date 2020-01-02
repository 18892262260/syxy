package com.syxy.generalaviation.service;

import com.syxy.generalaviation.entity.JobList;

import java.util.Map;

/**
 * @ClassName JobService
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:30
 * @Version 1.0
 */
public interface JobService {
    /**
     * 功能描述:增加任务
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:38
     */
    int addJob(JobList jobList);

    /**
     * 功能描述:根据ID删除任务
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:38
     */
    int deleteJobById(String jobId);

    /**
     * 功能描述:根据ID修改任务
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:41
     */
    int updateJobById(JobList jobList);

    /**
     * 功能描述:查询任务
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/6 14:41
     */
    Map<String,Object> findAllJob(JobList jobList, int pageNum, int pageSize);

    /**
     * 功能描述:根据ID查询
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/9 14:06
     */
    JobList findByJobId(JobList jobList);
}
