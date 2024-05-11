package com.temporal.demos.temporalspringbootdemo.interceptors;/*

package io.workshop.c5s3.interceptor;

import io.temporal.common.interceptors.WorkflowInboundCallsInterceptor;
import io.temporal.common.interceptors.WorkflowOutboundCallsInterceptor;
import io.temporal.common.interceptors.WorkflowOutboundCallsInterceptorBase;
import io.temporal.workflow.Workflow;

import java.util.Random;

public class OrderWorkflowOutboundCallsInterceptor extends WorkflowOutboundCallsInterceptor  {


    public OrderWorkflowOutboundCallsInterceptor(WorkflowOutboundCallsInterceptor next) {
        super(next);
    }

    @Override
    public ActivityOutput initiateActivity(ActivityInput input) {
        // Introduce a delay before the activity call
        Workflow.sleep(Duration.ofSeconds(5));

        // Proceed with the activity call
        return next.initiateActivity(input);
    }

}


*/
