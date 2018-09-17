package com.mzd.mywy.Interceptor;

import com.mzd.mywy.exception.MyException;
import com.mzd.mywy.annotation.Log;
import com.mzd.mywy.myenum.LogEnum;
import com.mzd.mywy.ormbean.Wylog;
import com.mzd.mywy.utils.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogAop {

    @Around("@annotation(mylog)")
    public Object around(ProceedingJoinPoint point, Log mylog) throws MyException {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            result = point.proceed();
        } catch (Throwable e) {
            log.error(e.toString());
            saveLog(point, 0, LogEnum.getcode(LogEnum.faile), e.toString());
            //抛出异常，让controller去捕获
            throw new MyException();
        }
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time, LogEnum.getcode(LogEnum.success), "");
        return result;
    }

    /**
     * 储存日志
     *
     * @param joinPoint
     * @param i
     * @param getcode
     * @param string
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long i, String getcode, String string) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Wylog wylog = new Wylog();
        wylog.setId(MyStringUtils.getuuid());
        wylog.setDatetime(String.valueOf(i));
        Log log = method.getDeclaredAnnotation(Log.class);
        if (log != null) {
            wylog.setRemark(log.value());
        }
        String classname = joinPoint.getTarget().getClass().getName();
        String methodname = signature.getName();
        wylog.setMethod(classname + "." + methodname + "()");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

    }


}
