package com.atguigu.ggkt.order.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // 文件上传的方法
    String upload(MultipartFile file);
}
