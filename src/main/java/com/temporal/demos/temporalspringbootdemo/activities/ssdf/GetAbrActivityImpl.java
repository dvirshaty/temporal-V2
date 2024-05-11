package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import com.temporal.demos.temporalspringbootdemo.abr.AbrClient;
import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "HsiaTaskQueue")
public class GetAbrActivityImpl implements GetAbrActivity {

    private static final Logger logger = Workflow.getLogger(GetAbrActivityImpl.class);


    private final AbrClient abrClient;

    @Override
    public void getAbr(HsiaDto input) {

        logger.info("get ABR - {}",input);
    }

    @Override
    public boolean validate(String abrId) {
        return true;
    }
}
