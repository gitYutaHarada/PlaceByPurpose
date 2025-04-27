package com.example.demo.aop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLog {

    @Around("execution(* com.example.demo..*.*(..))") // すべてのメソッドに適用
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // メソッド開始ログ
        outputLog("メソッド開始", joinPoint);

        Object result;
        try {
            // メソッド実行
            result = joinPoint.proceed();
        } catch (Throwable e) {
            // 例外発生時のログ
            outputLog("メソッド異常終了", joinPoint);
            throw e; // 例外を再スロー
        }

        // メソッド終了ログ
        outputLog("メソッド終了", joinPoint);
        return result;
    }

    // 共通ログ出力メソッド
    private void outputLog(String str, ProceedingJoinPoint joinPoint) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strNow = LocalDateTime.now().format(formatter);
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // ログ表示
        System.out.println(strNow + " : " + str + " : " + className + "." + methodName + "()");
    }
}
