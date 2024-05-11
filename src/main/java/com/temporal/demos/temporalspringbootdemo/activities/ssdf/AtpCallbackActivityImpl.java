package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "HsiaTaskQueue")
public class AtpCallbackActivityImpl implements AtpCallbackActivity {
    private static final Logger logger = Workflow.getLogger(AtpCallbackActivityImpl.class);

    @Override
    public void handleAtpCallback(boolean isATP) {

        if (!isATP) {
            logger.info("Failed to get ATP callback");
            throw new RuntimeException("Failed to get ATP callback");
        }

    }
}

