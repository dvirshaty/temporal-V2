package com.temporal.demos.temporalspringbootdemo.interceptors;

import io.temporal.activity.ActivityExecutionContext;
import io.temporal.common.interceptors.ActivityInboundCallsInterceptor;
import io.temporal.common.interceptors.ActivityInboundCallsInterceptorBase;
import io.temporal.workflow.Workflow;

import java.util.Random;


class OrderActivityInboundCallsInterceptor extends ActivityInboundCallsInterceptorBase {


    public OrderActivityInboundCallsInterceptor(ActivityInboundCallsInterceptor next) {
        super(next);
    }

   @Override
    public ActivityOutput execute(ActivityInput input) {
       System.out.println("Enter ActivityInboundCallsInterceptor - " + input);
      try{

          ActivityOutput execute = super.execute(input);
          System.out.println("Done ActivityInboundCallsInterceptor -" + execute.getResult());
          return execute;
      }catch (Exception e){
                System.out.println("Done ActivityInput failed");
                throw e;
      }

    }

}
