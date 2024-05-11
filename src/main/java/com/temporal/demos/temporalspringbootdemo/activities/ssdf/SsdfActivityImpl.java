package com.temporal.demos.temporalspringbootdemo.activities.ssdf;


import com.temporal.demos.temporalspringbootdemo.repository.HisaRepository;
import com.temporal.demos.temporalspringbootdemo.repository.model.Hsia;
import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "HsiaTaskQueue")
public class SsdfActivityImpl implements SsdfAbrActivity {
    private static final Logger logger = Workflow.getLogger(SsdfActivityImpl.class);
    private final HisaRepository hisaRepository;

    @Override
    @Transactional
    public int getAbr222(Integer integer) {
        logger.info("set ABR to SSDF for index {}" +integer );
       if(integer == 18){
            throw  new RuntimeException("Suka!!!");
        }
        Hsia hsia = Hsia.builder().uuid(UUID.randomUUID().toString()).name("Dvir"+ integer).contact("Dvir" + integer).build();
        hisaRepository.save(hsia);
        return 1;
    }

}
