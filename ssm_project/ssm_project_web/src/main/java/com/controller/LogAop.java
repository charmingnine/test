package com.controller;

import com.domain.SysLog;
import com.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Resource
    private HttpServletRequest request;
    @Resource
    private SysLogService sysLogService;

    private Date startTime; // 访问时间
    private Class executionClass;// 访问的类
    private Method executionMethod; // 访问的方法

    //前置通知,主要获取访问时间、访问的类、访问的方法
    @Before("execution(* com.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //访问时间
        startTime = new Date();
        //访问的类
        executionClass = jp.getTarget().getClass();
        //访问的方法
        String methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        if (args == null || args.length == 0) {//无参数
            executionMethod = executionClass.getMethod(methodName);
        } else {
            //有参数，就遍历，获取对应的Class，装入到一个Class[]
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            //获取有参数的方法
            executionMethod = executionClass.getMethod(methodName, classArgs);
        }
    }

    //后置通知，主要获取日志中其它信息，时长、ip、url...
    @After("execution(* com.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        //获取类上的@RequestMapping对象
        if (executionClass != SysLogController.class) {
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                //获取方法上的@RequestMapping对象
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    // 它的值应该是类上的@RequestMapping的value+方法上的@RequestMapping的value
                    String url = "";
                    url = classAnnotation.value()[0] + methodAnnotation.value()[0];
                    SysLog sysLog = new SysLog();
                    //获取访问时长
                    long excutionTime = new Date().getTime() - startTime.getTime();
                    //获取ip
                    String ip=request.getRemoteAddr();
                    // 可以通过securityContext获取，也可以从request.getSession中获取
                    SecurityContext context = SecurityContextHolder.getContext();
                    String username = ((User)
                            (context.getAuthentication().getPrincipal())).getUsername();

                    // 将sysLog对象属性封装
                    sysLog.setExecutionTime(excutionTime);
                    sysLog.setUrl(url);
                    sysLog.setIp(ip);
                    sysLog.setUsername(username);
                    sysLog.setMethod("[类名]" + executionClass.getName() + "[方法名]" +
                            executionMethod.getName());
                    sysLog.setVisitTime(startTime);
                    // 调用Service，调用dao将sysLog insert数据库
                    sysLogService.save(sysLog);
                }
            }
        }
    }

}
