package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-15 16:32
 * @Description:
 */

@ControllerAdvice  // aop
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局....");
        e.printStackTrace();
        return Result.fail(null).message("执行全局异常处理");
    }

    // 全局异常处理 ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("特定.....");
        e.printStackTrace();
        return Result.fail(null).message("执行ArithmeticException异常处理");
    }

    // 全局异常处理 ArithmeticException
    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result error(GgktException e) {
        System.out.println("特定.....");
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }

}