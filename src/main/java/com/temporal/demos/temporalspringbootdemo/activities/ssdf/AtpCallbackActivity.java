package com.temporal.demos.temporalspringbootdemo.activities.ssdf;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface AtpCallbackActivity {

    public void handleAtpCallback(boolean isATP);


}
