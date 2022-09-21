package com.atguigu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-18 16:20
 * @Description:
 */

@Data
public class User {

    @ExcelProperty(value = "用户编号",index = 0)
    private int id;

    @ExcelProperty(value = "用户名称", index = 1)
    private String name;

}