package cn.tedu.csmall.product.ex.handler;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler
//    public String handleServiceException(ServiceException e) {
//        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
//        log.warn("异常信息：{}", e.getMessage());
//        return e.getMessage();
//    }
//
//    @ExceptionHandler
//    public String handleNullPointerException(NullPointerException e) {
//        log.warn("程序运行过程中出现了NullPointerException，将统一处理！");
//        log.warn("异常信息：{}", e.getMessage());
//        return e.getMessage();
//    }
//
//    @ExceptionHandler
//    public String handleRuntimeException(RuntimeException e) {
//        log.warn("程序运行过程中出现了RuntimeException，将统一处理！");
//        log.warn("异常信息：{}", e.getMessage());
//        return e.getMessage();
//    }

    @ExceptionHandler
    public String handleThrowable(Throwable e) {
        log.warn("程序运行过程中出现了Throwable，将统一处理！");
        log.warn("异常信息：{}", e.getMessage());
        return e.getMessage();
    }

}
