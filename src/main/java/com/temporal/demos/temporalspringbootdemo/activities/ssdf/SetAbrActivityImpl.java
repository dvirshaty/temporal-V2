package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import com.temporal.demos.temporalspringbootdemo.exception.NonRetryException;
import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "HsiaTaskQueue")
public class SetAbrActivityImpl implements SetAbrActivity {
    private static final Logger logger = Workflow.getLogger(SetAbrActivityImpl.class);

    @Override
    public void setAbr(HsiaDto input) {

        logger.info("set ABR to SSDF - {}", input);


        Random rd = new Random(); // creating Random boolean
        /*if (!rd.nextBoolean()) {
            //throw new RuntimeException("Failed setABR");
            throw new NonRetryException("Failed setABR");

        }*/


    }
}
