package com.temporal.demos.temporalspringbootdemo.activities.compensate;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CompensateActivity {

    public void compensate(HsiaDto hsiaDto);
    public void compensate2(HsiaDto hsiaDto);
}
