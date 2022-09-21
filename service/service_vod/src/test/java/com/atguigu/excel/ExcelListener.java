package com.atguigu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-18 16:30
 * @Description:
 */

public class ExcelListener extends AnalysisEventListener<User> {


    // 一行一行读取excel内容，把每行内容封装到User对象
    // 从excel第二行读取
    @Override
    public void invoke(User user, AnalysisContext context) {
        System.out.println(user);
    }

    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}