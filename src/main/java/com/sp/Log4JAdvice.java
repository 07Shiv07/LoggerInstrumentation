package com.sp;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Log4JAdvice {
    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin Method method) {
        System.out.println("Logger method being called: " + method.getName());
        for (Parameter param:
        method.getParameters()) {
            System.out.print(param.getName()+" ");
        }
    }
}
