package com.example.demo.Retry;

import com.example.demo.Retry.RetryOnOptimisticLockingFailure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class TryAgainAspect implements Ordered {

    private int maxRetries;
    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getOrder() {
        return this.order;
    }

    @Pointcut("@annotation(com.example.demo.Retry.RetryOnOptimisticLockingFailure)")
    public void retryOnOptFailure() {
    }

    @Around("retryOnOptFailure()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature msig = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        RetryOnOptimisticLockingFailure annotation = currentMethod.getAnnotation(RetryOnOptimisticLockingFailure.class);
        this.setMaxRetries(annotation.tryTimes());

        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (ObjectOptimisticLockingFailureException| StaleObjectStateException exception) {
                exception.printStackTrace();
                if (numAttempts > maxRetries) {
                    System.out.println("System error, all retry failed");
                } else {
                    System.out.println(" === retry ===" + numAttempts + "times");
                }
            }
        } while (numAttempts <= this.maxRetries);

        return null;
    }

}
