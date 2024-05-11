package com.temporal.demos.temporalspringbootdemo.workflows;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HsiaWorkflow {
    @WorkflowMethod
    void validateAndExecute(HsiaDto input);


    @SignalMethod
    void setAtp();

    @SignalMethod
    void setBrassCallback();

}
