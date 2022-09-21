package com.atguigu.ggkt.order.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.order.mapper.CourseMapper;
import com.atguigu.ggkt.order.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-09-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public Map<String, Object> findPageCourses(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        // 获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId(); // 二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId(); // 一层分类
        Long teacherId = courseQueryVo.getTeacherId();

        // 判断条件值为空
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        // 调用方法实现查询分页
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> list = pages.getRecords();

        // 查询数据有这么几个id
        // 讲师id 课程分类id(一层和二层)
        // 获取这些id进行封装 封装数据
        list.stream().forEach(item -> {
            this.getNameById(item);
        });

        // 封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", list);

        return map;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        // 添加课程基本信息,操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        // 添加课程描述信息,操作course表

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());

        // 设置课程id
        courseDescription.setId(course.getId());
        descriptionService.save(courseDescription);

        return course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        // 课程的基本信息
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        // 课程的描述信息
        CourseDescription courseDescription = descriptionService.getById(id);
        // 封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);

        // 封装描述
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }

        return courseFormVo;
    }

    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {

        // 修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        // 修改课程描述信息
        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
        // 设置课程描述的id
        description.setId(course.getId());
        descriptionService.updateById(description);

    }

    // 根据课程id查询发布课程信息
    @Override
    public CourseFormVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    // 课程最终发布
    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1); // 课程已经发布
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

    // 删除课程
    @Override
    public void removeCourseId(Long id) {
        // 根据课程id删除小节
        videoService.removeVideoByCourseId(id);


        // 根据课程id删除章节
        chapterService.removeChapterByCourseId(id);

        // 根据课程id删除课程描述
        descriptionService.removeById(id);

        // 根据id删除课程描述
         baseMapper.deleteById(id);

    }

    private void getNameById(Course course) {
        // 先根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }

        // 根据课程分类id获取课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (subjectOne != null) {
            course.getParam().put("subjectParentTitle", subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if (subjectTwo != null) {
            course.getParam().put("subjectTitle", subjectTwo.getTitle());
        }
    }
}
