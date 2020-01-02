package com.syxy.generalaviation.mapper;

import com.syxy.generalaviation.entity.JobList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName JobMapper
 * @Description TODO
 * @Author 南辰星
 * @Date 2019/12/6 14:29
 * @Version 1.0
 */
@Mapper
public interface JobMapper {
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
    List<JobList> findAllJob(JobList jobList);

    /**
     * 功能描述:根据姓名查找飞行员
     * @param:
     * @return:
     * @auther: 南辰星
     * @date: 2019/12/10 9:21
     */
    String findJobByPilotName(String name);
}
