package com.atguigu.ggkt.order.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-09-18
 */
public interface CourseService extends IService<Course> {


    Map<String, Object> findPageCourses(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    // 添加课程的基本信息
    Long saveCourseInfo(CourseFormVo courseFormVo);


    // 根据id查询课程信息
    CourseFormVo getCourseInfoById(Long id);

    // 修改课程信息
    void updateCourseId(CourseFormVo courseFormVo);


    CourseFormVo getCoursePublishVo(Long id);


    // 课程最终发布
    void publishCourse(Long id);

    void removeCourseId(Long id);
}
