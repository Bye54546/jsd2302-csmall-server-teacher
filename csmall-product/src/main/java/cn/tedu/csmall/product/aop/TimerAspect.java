package cn.tedu.csmall.product.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Around("execution(* cn.tedu.csmall.product.service.*.*(..))")
    public Object xxx(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        // 获取匹配的方法的相关信息
        String targetClassName = pjp.getTarget().getClass().getName(); // 获取匹配的方法所在的类
        String signatureName = pjp.getSignature().getName(); // 获取匹配的方法的签名中的方法名称
        Object[] args = pjp.getArgs(); // 方法的参数列表
        System.out.println("类型：" + targetClassName);
        System.out.println("方法名：" + signatureName);
        System.out.println("参数列表：" + Arrays.toString(args));

        // 执行以上表达式匹配的方法
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - start) + "毫秒");
        return result;
    }

}