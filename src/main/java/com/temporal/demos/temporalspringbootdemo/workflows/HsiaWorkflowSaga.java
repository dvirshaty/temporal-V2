package com.temporal.demos.temporalspringbootdemo.workflows;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.UpdateMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HsiaWorkflowSaga {
    @WorkflowMethod
    void validateAndExecute(HsiaDto input);


    @SignalMethod
    void setAtp(String uuid);

    @SignalMethod
    void setBrassCallback();

    @SignalMethod
    void cancelFlow();

}
