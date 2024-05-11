package com.temporal.demos.temporalspringbootdemo.interceptors;

import io.temporal.common.interceptors.WorkflowInboundCallsInterceptor;
import io.temporal.common.interceptors.WorkflowInboundCallsInterceptorBase;
import io.temporal.common.interceptors.WorkflowOutboundCallsInterceptor;
import io.temporal.workflow.Workflow;

import java.util.Random;

public class OrderWorkflowInboundCallsInterceptor extends WorkflowInboundCallsInterceptorBase {

    private boolean signalLock = true;

    public OrderWorkflowInboundCallsInterceptor(WorkflowInboundCallsInterceptor next) {
        super(next);


    }

    @Override
    public void init(WorkflowOutboundCallsInterceptor outboundCalls) {
        new MyInterceptor(outboundCalls);
        super.init(outboundCalls);
    }

/*  @Override
    public WorkflowOutput execute(WorkflowInput input) {
        System.out.println("Enter WorkflowInboundCallsInterceptor %%%%");
      System.out.println("exit WorkflowInboundCallsInterceptor");

        return super.execute(input);
    }*/


    @Override
    public void handleSignal(SignalInput input) {
        Workflow.await(() -> signalLock);
        signalLock = false;
        try {
            super.handleSignal(input);
        } finally {
            signalLock = true;
        }
    }
}

