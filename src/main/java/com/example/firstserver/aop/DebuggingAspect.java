package com.example.firstserver.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // AOP 클래스 선언 : 부가 기능을 주입하는 클래스
@Component // IOc 컨테이너가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {

    //대상 메소드 선택: CommentService#Create()
    @Pointcut("execution(* com.example.firstserver.api.*.*(..))")
    private void cut(){}

    //실행 시점 설정 : cut() 의 대상이 수행되기 이전
    @Before("cut()")
    public void loggingArgs(JoinPoint joinPoint){ // cut()의 대상 메소드
        // 입력값 가져오기
        Object[] args =joinPoint.getArgs();
        //클래스명
        String className =joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        //메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        //입력값 로깅하기
        //CommentService#Create()의 입력값 => 5
        for(Object obj : args){
            log.info("{}#{}의 입력값 => {}", className,methodName,obj);
        }
    }
    //실행 시점 설정 : Cut() 에 지정된 대상 호출 성공 후!
    @AfterReturning(value = "cut()", returning = "returnObj")
    public  void loggingReturnValue(JoinPoint joinPoint,
                                    Object returnObj){ //리턴값

        String className =joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        //메소드명
        String methodName = joinPoint.getSignature()
                .getName();

        //반환갑 로깅
            log.info("{}#{}의 반환값=> {}", className,methodName,returnObj);


    }
}
