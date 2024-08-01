package com.sp;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class ToStringInterceptor {

    /*@Advice.OnMethodExit
    public static void onExit(@Advice.Origin("#t") String className,
                              @Advice.Origin("#m") String methodName,
                              @Advice.Return(readOnly = false) String returnValue) {
        int lineNumber = 0; // Line number can't be easily retrieved without deeper integration.

        // Log the class, method, and the original toString result
        String logMessage = String.format("%s.%s => %s", className, methodName, returnValue);
        System.out.println(logMessage);

    }*/

    @Advice.OnMethodExit
    public static void onExit(@Advice.Origin String method,
                              @Advice.This Object obj,
                              @Advice.Return(readOnly = false) String returnValue) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = null;

        // Find the first non-JDK and non-ByteBuddy stack trace element
        for (StackTraceElement element : stackTraceElements) {
            if (!element.getClassName().startsWith("java.") &&
                    !element.getClassName().startsWith("net.bytebuddy.")) {
                caller = element;
                break;
            }
        }

        if (caller != null) {
            String className = caller.getClassName();
            String methodName = caller.getMethodName();
            int lineNumber = caller.getLineNumber();

            String logMessage = String.format("%s:%d %s => %s", className, lineNumber, methodName, returnValue);
            System.out.println(logMessage);
        }
    }
}
