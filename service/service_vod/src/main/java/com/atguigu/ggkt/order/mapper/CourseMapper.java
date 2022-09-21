package com.atguigu.ggkt.order.mapper;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-09-18
 */
public interface CourseMapper extends BaseMapper<Course> {

    // 根据课程id查询发布的课程信息
    CourseFormVo selectCoursePublishVoById(Long id);
}
