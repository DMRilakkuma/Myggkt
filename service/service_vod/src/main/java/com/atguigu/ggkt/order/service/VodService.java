package com.atguigu.ggkt.order.service;

import java.io.InputStream;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-20 11:25
 * @Description:
 */

public interface VodService {
    String uploadVideo(InputStream inputStream, String originalFilename);

    // 删除腾讯云视频
    void removeVideo(String fileId);
}