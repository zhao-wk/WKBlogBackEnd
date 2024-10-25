package com.zhaowk.aspect;

import com.alibaba.fastjson.JSON;
import com.zhaowk.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.zhaowk.annotation.SystemLog)")
    public void pt(){

    }

    /**
     * 定义通知方法
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        }finally {
            log.info("===========End===========" + System.lineSeparator());
        }
        return ret;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("===========Start===========");
        //打印url
        log.info("URL           : {}", request.getRequestURL());
        //打印描述信息
        log.info("BusinessName  : {}", systemLog.bussinessName());
        //打印Http Method
        log.info("HTTP_METHOD   : {}", request.getMethod());
        //打印调用controller的全路径以及执行方法
        log.info("Class Method  : {}. {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        //打印请求的ip
        log.info("IP            : {}", request.getRemoteHost());
        //打印请求入参
        log.info("Request Args  : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    private void handleAfter(Object ret) {
        log.info("Response      : {}", JSON.toJSONString(ret));
    }
}
