package com.temporal.demos.temporalspringbootdemo.workflows;

import com.temporal.demos.temporalspringbootdemo.activities.compensate.CompensateActivity;
import com.temporal.demos.temporalspringbootdemo.activities.hsia.HsiaActivity;
import com.temporal.demos.temporalspringbootdemo.activities.ssdf.SetAbrActivity;
import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

@WorkflowImpl(taskQueues = "HsiaTaskQueue")
public class HsiaWorkflowImpl implements HsiaWorkflow {
    private static final Logger logger = Workflow.getLogger(HsiaWorkflowImpl.class);

    /*    private final SsdfActivity getAbrActivity =
                Workflow.newActivityStub(SsdfActivity.class,
                        ActivityOptions.newBuilder()
                                .setStartToCloseTimeout(Duration.ofSeconds(20))
                                .build());*/
    private final SetAbrActivity setAbrActivity =
            Workflow.newActivityStub(SetAbrActivity.class,
                    ActivityOptions.newBuilder()
                            .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(20))
                            .build());

    private final HsiaActivity hsiaActivity =
            Workflow.newActivityStub(HsiaActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(20))
                            .build());


    private final CompensateActivity compensateActivity =
            Workflow.newActivityStub(CompensateActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(20))
                            .build());

    private boolean isAtpCallback = false;
    private boolean isBrassCallback = false;


    @Override // WorkflowMethod
    public void validateAndExecute(HsiaDto input) {
        try {
            logger.info("start workflow !!!");
         //   setAbrActivity.setAbr();
            logger.info("wait for ATP");
            Workflow.await(() -> isAtpCallback);
          //  getAbrActivity.getAbr();
            hsiaActivity.submitHsia(input);
            logger.info("wait for BRASS callback");
            Workflow.await(() -> isBrassCallback);
            hsiaActivity.sspCallback(input);
            logger.info("Done workflow !!!");
        } catch (Exception e) {
            logger.info("workflow failed, try to compensate");
            compensateActivity.compensate(input);
        }

    }

    @Override
    public void setAtp() {
        logger.info("got ATP!");
        isAtpCallback = true;
    }

    @Override
    public void setBrassCallback() {
        logger.info("got Brass callback !");
        isBrassCallback = true;
    }


}
