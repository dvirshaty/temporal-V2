package com.temporal.demos.temporalspringbootdemo.activities.hsia;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface HsiaActivity {
    public void submitHsia(HsiaDto hsiaDto);

    public void sspCallback(HsiaDto hsiaDto);

    public void cancel(String id);

}
