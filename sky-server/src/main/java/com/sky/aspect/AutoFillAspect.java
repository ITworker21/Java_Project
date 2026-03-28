package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut()
    {

    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint)
    {
        log.info("开始公共字段自动填充");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0)
            return;
        Object enity = args[0];

        LocalDate now = LocalDate.now();
        Long currentId = BaseContext.getCurrentId();

        if(operationType == OperationType.INSERT)
        {
            try {
                Method setCreateTime = enity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = enity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateTime = enity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = enity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,long.class);

                setCreateTime.invoke(enity,now);
                setCreateUser.invoke(enity,currentId);
                setUpdateTime.invoke(enity,now);
                setUpdateUser.invoke(enity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
