package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.activity.ActivityInterface;
import io.temporal.workflow.Workflow;

@ActivityInterface
public interface GetAbrActivity {


    void getAbr(HsiaDto abrId);

    boolean validate(String abrId);

}
