package cn.tedu.csmall.product.ex.handler;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleServiceException(ServiceException e) {
        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    public List<String> handleBindException(BindException e) {
        log.warn("程序运行过程中出现了BindException，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        // String message = e.getFieldError().getDefaultMessage();
        // return message;

        // StringJoiner stringJoiner = new StringJoiner("，", "请求参数错误，", "！");
        // List<FieldError> fieldErrors = e.getFieldErrors();
        // for (FieldError fieldError : fieldErrors) {
        //    String defaultMessage = fieldError.getDefaultMessage();
        //    stringJoiner.add(defaultMessage);
        // }
        // return stringJoiner.toString();

        List<String> messageList = new ArrayList<>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            messageList.add(defaultMessage);
        }
        return messageList;
    }

    @ExceptionHandler
    public String handleThrowable(Throwable e) {
        // 【注意】在项目正式上线时，禁止使用 e.printStackTrace();
        log.warn("程序运行过程中出现了Throwable，将统一处理！");
        log.warn("异常：", e); // 取代 e.printStackTrace();，效果相同，注意，第1个参数中不要使用 {} 进行占位
        String message = "服务器忙，请稍后再试！【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在全局异常处理器中补充处理对应异常的方法】";
        return message;
    }

}