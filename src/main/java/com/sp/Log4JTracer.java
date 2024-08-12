package com.sp;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.matcher.StringMatcher;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isSynthetic;

public class Log4JTracer {
    public static void premain(String args, Instrumentation instrument){

        String classNameEndsWith="LogApp";
        // The debugging listener shows what classes are being picked up by the instrumentation
        AgentBuilder.Listener.Filtering debuggingListener = new AgentBuilder.Listener.Filtering(
                new StringMatcher("Logger", StringMatcher.Mode.CONTAINS),
                AgentBuilder.Listener.StreamWriting.toSystemOut());

        // This gives a bit of a speedup when going through classes...
        AgentBuilder.RawMatcher ignoreMatcher = new AgentBuilder.RawMatcher.ForElementMatchers(ElementMatchers.nameStartsWith("net.bytebuddy."));


        new AgentBuilder.Default()
                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
                //.ignore(ignoreMatcher)
                //.with(debuggingListener)
                .type(ElementMatchers.any())
                .transform(
                        (builder, typeDescription, classLoader, module, protectionDomain)
                                ->
                        builder.method(ElementMatchers.named("info")
                                .or(ElementMatchers.named("debug"))
                                .or(ElementMatchers.named("error"))
                                .or(ElementMatchers.named("trace"))
                                .or(ElementMatchers.named("fatal"))
                                .or(ElementMatchers.named("warn"))
                        ).intercept(Advice.to(Log4JAdvice.class))
                ).installOn(instrument);

        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.method(ElementMatchers.named("toString"))
                                .intercept(Advice.to(ToStringInterceptor.class))
                ).installOn(instrument);

    }
}
