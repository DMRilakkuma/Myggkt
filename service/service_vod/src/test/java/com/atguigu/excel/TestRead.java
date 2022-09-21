package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-18 16:34
 * @Description:
 */

public class TestRead {
    public static void main(String[] args) {
        // 设置文件名称和路径
        String fileName = "D:\\atguigu.xlsx";
        // 调用方法进行读操作
        EasyExcel.read(fileName, User.class, new ExcelListener()).sheet().doRead();
    }

}