package com.temporal.demos.temporalspringbootdemo.interceptors;


import io.temporal.common.interceptors.WorkflowOutboundCallsInterceptor;
import io.temporal.common.interceptors.WorkflowOutboundCallsInterceptorBase;
import io.temporal.workflow.Workflow;

import java.time.Duration;

/*
public class MyInterceptor {
}
*/
public class MyInterceptor extends WorkflowOutboundCallsInterceptorBase {
    private final WorkflowOutboundCallsInterceptor next;

    public MyInterceptor(WorkflowOutboundCallsInterceptor next) {

        super(next);
        System.out.println("MyInterceptor init");
        this.next = next;
    }

    @Override
    public <R> ActivityOutput<R> executeActivity(ActivityInput<R> input) {

        return super.executeActivity(input);
    }

    @Override
    public <R> LocalActivityOutput<R> executeLocalActivity(LocalActivityInput<R> input) {
        return super.executeLocalActivity(input);
    }
}