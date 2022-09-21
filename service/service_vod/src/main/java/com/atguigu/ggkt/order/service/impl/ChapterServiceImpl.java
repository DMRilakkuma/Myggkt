package com.atguigu.ggkt.order.service.impl;


import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.order.mapper.ChapterMapper;
import com.atguigu.ggkt.order.service.ChapterService;
import com.atguigu.ggkt.order.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-09-18
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    // 1 课程大纲的列表方法(章节和小节列表)
    @Override
    public List<ChapterVo> getTreeList(Long courseId) {

        List<ChapterVo> finalChapterList = new ArrayList<>();

        // 1 根据courseId获取课程里面所有的章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        // 根据courseId获取课程里面的所有小节
        LambdaQueryWrapper<Video> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        // 封装章节
        // 遍历所有的章节
        for (int i = 0; i < chapterList.size(); i++) {
            // 得到课程的每个章节
            Chapter chapter = chapterList.get(i);
            // chapter -- ChaterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            // 得到的每个章节对象放到list集合中去
            finalChapterList.add(chapterVo);

            // 封装章节里的小节

            // 创建list集合用户封装章节中所有小节
            List<VideoVo> videoVoList = new ArrayList<VideoVo>();
            // 遍历小节list
            for (Video video : videoList) {
                // 判断小节是哪个章节下面的小节
                // 章节id 和小节chapter
                if (chapter.getId().equals(video.getChapterId())) {
                    // VideoVo -- VideoVo
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    // 放到videoVoList
                    videoVoList.add(videoVo);
                }
            }

            // 把章节里面所有小节集合放到章节里面去
            chapterVo.setChildren(videoVoList);
        }

        // 封装章节里面的小节
        return finalChapterList;
    }

    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
