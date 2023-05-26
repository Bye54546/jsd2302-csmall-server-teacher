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

    // 连接点（JoinPoint）：程序执行过程中的某个节点，可能是某个方法的调用，或抛了某个异常
    // 切入点（PointCut）：选择1个或多个连接点的表达式
    // ------------------------------------------------------------------------
    // 通知（Advice）注解
    // @Around：环绕，包裹了连接点，你的代码可以在连接点之前和之后执行
    // @Before：只能在连接点之前执行
    // @After：只能在连接点之后执行
    // @AfterReturning：只能在成功的执行了连接点之后执行
    // @AfterThrowing：只能在连接点方法抛出异常之后执行
    // 以上各Advice执行情况大致是：
    // @Around开始
    // try {
    //     @Before
    //     执行连接点
    //     @AfterReturning
    // } catch (Throwable e) {
    //     @AfterThrowing
    // } finally {
    //     @After
    // }
    // @Around结束
    // ------------------------------------------------------------------------
    // execution配置的就是切入点表达式，用于匹配某些方法
    // 在切入点表达式中，可以使用通配符：
    // -- 1个星号：任意1次匹配
    // -- 2个连续的小数点：任意n次匹配，只能用于包或和参数列表
    //                 ↓ 返回值类型
    //                   ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 包名
    //                                                  ↓ 类名
    //                                                    ↓ 方法名
    //                                                      ↓↓ 参数列表
    // 另外，在表达式中，在方法的返回值的左侧还可以指定修饰符，修饰符是可选的
    // 注解是典型的修饰符之一
    // 其实，Spring MVC的统一异常处理就是通过AOP实现的，
    // Spring JDBC的事务管理也是如此
    // Spring Security的权限检查也是如此
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

        // 执行以上表达式匹配的方法，即某个Service的某个方法
        // 注意-1：必须获取调用proceed()方法返回的结果，作为当前切面方法的返回值
        // -- 如果没有获取，或没有作为当前切面方法的返回值，相当于执行了连接点方法，却没有获取返回值
        // 注意-2：调用proceed()时的异常必须抛出，否则，Controller将无法知晓Service抛出过异常，就无法向客户端响应错误信息
        // -- 前提：本例的切面是作用于Service的
        // -- 其实，你也可以使用try...catch捕获调用proceed()时的异常，但必须在catch中也抛出异常
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - start) + "毫秒");

        // 返回调用proceed()得到的结果
        return result;
    }

}