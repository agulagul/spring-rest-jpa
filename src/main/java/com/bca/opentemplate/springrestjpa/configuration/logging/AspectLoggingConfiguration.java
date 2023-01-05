package com.bca.opentemplate.springrestjpa.configuration.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class AspectLoggingConfiguration extends BaseLogging {

    private String niceName(final JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "::" + joinPoint.getSignature().getName();
    }

    private String niceName(final ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "::" + joinPoint.getSignature().getName();
    }

    @Pointcut("(within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *) && !@annotation(com.bca.opentemplate.springrestjpa.util.logging.NoLogging))")
    private void isController() {
        // Just empty method to declare Controller pointcut
    }

    @Pointcut("(within(@org.springframework.stereotype.Service *))")
    private void isService() {
        // Just empty method to declare Service pointcut
    }

    @Pointcut("(within(@org.springframework.stereotype.Repository *))")
    private void isRepo() {
        // Just empty method to declare Repository pointcut
    }

    @Around("isController()")
    public Object logControllerAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object[] args = joinPoint.getArgs();
        transLog.info("Starting Controller: {}", niceName(joinPoint));
        verboseLog.info("Starting Controller: {} with argument(s): {}", niceName(joinPoint), Arrays.toString(args));
        Object result = joinPoint.proceed();
        stopWatch.stop();
        transLog.info("Completed Controller: {} in {} ms", niceName(joinPoint), stopWatch.getTotalTimeMillis());
        verboseLog.info("Completed Controller: {} in {} ms with response(s): {}", niceName(joinPoint),
                stopWatch.getTotalTimeMillis(), result);
        return result;
    }

    @Around("isService()")
    public Object logServiceAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object[] args = joinPoint.getArgs();
        transLog.info("Starting Service: {}", niceName(joinPoint));
        verboseLog.info("Starting Service: {} with argument(s): {}", niceName(joinPoint), Arrays.toString(args));
        Object result = joinPoint.proceed();
        stopWatch.stop();
        transLog.info("Completed Service: {} in {} ms", niceName(joinPoint), stopWatch.getTotalTimeMillis());
        verboseLog.info("Completed Service: {} in {} ms with response(s): {}", niceName(joinPoint),
                stopWatch.getTotalTimeMillis(), result);
        return result;
    }

    @Around("isRepo()")
    public Object logRepoAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object[] args = joinPoint.getArgs();
        transLog.info("Starting Repository: {}", niceName(joinPoint));
        verboseLog.info("Starting Repository: {} with argument(s): {}", niceName(joinPoint), Arrays.toString(args));
        Object result = joinPoint.proceed();
        stopWatch.stop();
        transLog.info("Completed Repository: {} in {} ms", niceName(joinPoint), stopWatch.getTotalTimeMillis());
        verboseLog.info("Completed Repository: {} in {} ms with response(s): {}", niceName(joinPoint),
                stopWatch.getTotalTimeMillis(), result);
        return result;
    }

    @AfterThrowing(pointcut = "isController() || isService() || isRepo()", throwing = "e")
    public void logAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        String shortErrMessage = "please check verbose log.";
        String origin = "";
        if (e != null) {
            if (e.getCause() != null && e.getLocalizedMessage() != null) {
                shortErrMessage = e.getCause().getLocalizedMessage();
            }
        }
        StackTraceElement[] se = Thread.currentThread().getStackTrace();
        origin = se[2].getClassName() + "::" + se[2].getMethodName() + "() at line " + se[2].getLineNumber();

        transLog.error("Threw exception in {} with cause = {}", niceName(joinPoint), origin, shortErrMessage);
        verboseLog.error("Threw exception in {} with cause = {}", niceName(joinPoint), origin, e);
    }
}