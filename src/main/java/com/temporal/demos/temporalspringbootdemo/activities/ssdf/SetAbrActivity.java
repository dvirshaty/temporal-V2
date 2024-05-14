package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SetAbrActivity {

    public void setAbr(HsiaDto input);

}

