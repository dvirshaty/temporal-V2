package com.temporal.demos.temporalspringbootdemo.workflows;

import com.google.common.collect.Lists;
import com.temporal.demos.temporalspringbootdemo.activities.compensate.CompensateActivity;
import com.temporal.demos.temporalspringbootdemo.activities.hsia.HsiaActivity;
import com.temporal.demos.temporalspringbootdemo.activities.ssdf.AtpCallbackActivity;
import com.temporal.demos.temporalspringbootdemo.activities.ssdf.SetAbrActivity;
import com.temporal.demos.temporalspringbootdemo.activities.ssdf.SsdfAbrActivity;
import com.temporal.demos.temporalspringbootdemo.config.HsiaWorkflowConfig;
import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import com.temporal.demos.temporalspringbootdemo.exception.NonRetryException;
import com.temporal.demos.temporalspringbootdemo.repository.HisaRepository;
import com.temporal.demos.temporalspringbootdemo.repository.model.Hsia;
import com.temporal.demos.temporalspringbootdemo.service.SomeService;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.common.SearchAttributeKey;
import io.temporal.common.SearchAttributeUpdate;
import io.temporal.common.SearchAttributes;
import io.temporal.failure.CanceledFailure;
import io.temporal.internal.sync.WorkflowThread;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WorkflowImpl(taskQueues = {"HsiaTaskQueue"})
@RequiredArgsConstructor
public class HsiaWorkflowSageImpl implements HsiaWorkflowSaga {
    private static final Logger logger = Workflow.getLogger(HsiaWorkflowSageImpl.class);
    private final HisaRepository hisaRepository;
    private final HsiaWorkflowConfig hsiaWorkflowConfig;
    private final SomeService someService;
    private CancellationScope scope;
    Saga saga = new Saga(new Saga.Options.Builder().setParallelCompensation(false).build());

    private final AtpCallbackActivity atpCallbackActivity = Workflow.newActivityStub(AtpCallbackActivity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private final SetAbrActivity setAbrActivity = Workflow.newActivityStub(SetAbrActivity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).setDoNotRetry(NonRetryException.class.getName()).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private final HsiaActivity hsiaActivity = Workflow.newActivityStub(HsiaActivity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).setDoNotRetry(NonRetryException.class.getName()).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private final SsdfAbrActivity ssdfActivity = Workflow.newActivityStub(SsdfAbrActivity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).setDoNotRetry(NonRetryException.class.getName()).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());
    private final CompensateActivity compensateActivity = Workflow.newActivityStub(CompensateActivity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).setDoNotRetry(NonRetryException.class.getName()).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private boolean isAtpCallback = false;
    private boolean isBrassCallback = false;


    @Override // WorkflowMethod
    public void validateAndExecute(HsiaDto input) {
        scope = Workflow.newCancellationScope(() -> {
            // Your workflow code here.
            try {
                logger.info("start workflow !!!");

                //  ssdfActivity.getAbr();
                saga.addCompensation(compensateActivity::compensate, input);
                setAbrActivity.setAbr(input);
                logger.info("wait for ATP");
                //   Workflow.await(Duration.ofSeconds(600), () -> isAtpCallback);
                //  atpCallbackActivity.handleAtpCallback(isAtpCallback);
                SearchAttributes typedSearchAttributes = Workflow.getTypedSearchAttributes();
                String origName = typedSearchAttributes.get(SearchAttributeKey.forKeyword("dogName"));
                for(int i=0 ; i < 10; i++){
                    Workflow.sleep(5000);

                    SearchAttributes typedSearchAttributes2 = Workflow.getTypedSearchAttributes();
                    String dogName = typedSearchAttributes2.get(SearchAttributeKey.forKeyword("dogName"));
                    logger.info("Iteration {} dogName {}", i , dogName);
                    dogName = origName + i;
                    logger.info("Iteration {} new value {}", i , dogName);


                       Workflow.upsertTypedSearchAttributes(SearchAttributeUpdate.valueSet(SearchAttributeKey.forKeyword("dogName") , dogName));

                }

             /*   hsiaActivity.submitHsia(input);
                saga.addCompensation(compensateActivity::compensate2, input);*/
   /*             logger.info("wait for BRASS callback");
                Workflow.await(Duration.ofSeconds(hsiaWorkflowConfig.getWaitForHsiaCallbackSeconds()), () -> isBrassCallback);
                hsiaActivity.sspCallback(input);*/
                logger.info("Done workflow !!!");

            } catch (CanceledFailure e) {
                CancellationScope cancellationScope = Workflow.newDetachedCancellationScope(() -> {
                    logger.info("CancelExternalWorkflowException failed, try to compensate");
                    compensateActivity.compensate(input);
                    CancellationScope.throwCanceled();
                });
                cancellationScope.run();

            } catch (Exception e) {
                logger.info(e.getClass().getName());
                logger.info("workflow failed, try to compensate");

            }
        });
        scope.run();
    }

    private void saveToDB(HsiaDto input) {
        Hsia hsia = Hsia.builder().uuid(input.getUuid()).name(input.getName()).contact(input.getContact()).build();
        Hsia save = hisaRepository.save(hsia);
        input.setId(save.getId());
    }

    @Override
    public void setAtp(String uuid) {
        logger.info("got ATP!");
        List<Hsia> byUuid = hisaRepository.findByUuid(uuid);
        if (!CollectionUtils.isEmpty(byUuid)) {
            isAtpCallback = true;
        }
    }

    @Override
    public void setBrassCallback() {
        logger.info("got Brass callback !");
        isBrassCallback = true;
    }

    @Override
    public void cancelFlow() {
        logger.info("cancelFlow!");
        logger.info(Workflow.getInfo().getWorkflowId());
        if (!isAtpCallback) {
            scope.cancel("cancel me please");
        }

    }


}
