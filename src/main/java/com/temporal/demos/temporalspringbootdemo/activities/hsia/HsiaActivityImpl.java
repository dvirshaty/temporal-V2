package com.temporal.demos.temporalspringbootdemo.activities.hsia;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "DvirTaskQueue")
public class HsiaActivityImpl implements HsiaActivity {


    private static final Logger logger = Workflow.getLogger(HsiaActivityImpl.class);

    @Override
    public void submitHsia(HsiaDto hsiaDto) {
        logger.info("start Submit to Hsia - {}", hsiaDto);
    }

    @Override
    public void sspCallback(HsiaDto hsiaDto) {
        logger.info("start sspCallback for Hsia - {}", hsiaDto);


    }

    @Override
    public void cancel(String id) {

    }


}
